import java.util.*;

public class Dijkstra {
    public static DijkstraResult shortestPaths(
            Graph graph, Vertex start) {

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException(
                "Стартовая вершина отсутствует в графе");
        }

        Map<Vertex, Integer> distance = new HashMap<>();
        Map<Vertex, Vertex> previous = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();

        for (Vertex vertex : graph.getVertices()) {
            distance.put(vertex, Integer.MAX_VALUE);
        }

        distance.put(start, 0);

        while (visited.size() < graph.getVertices().size()) {
            Vertex current = null;
            int minDistance = Integer.MAX_VALUE;

            // Вершины рассматриваются по алфавиту.
            for (Vertex vertex : graph.getVertices()) {
                int d = distance.get(vertex);

                if (!visited.contains(vertex)
                        && d < minDistance) {
                    current = vertex;
                    minDistance = d;
                }
            }

            if (current == null) {
                break;
            }

            visited.add(current);

            for (Edge edge : graph.getEdges()) {
                if (!edge.getFrom().equals(current)) {
                    continue;
                }

                Vertex next = edge.getTo();

                if (visited.contains(next)) {
                    continue;
                }

                int newDistance =
                    distance.get(current) + edge.getWeight();

                // При равенстве расстояний предшественник
                // не меняется.
                if (newDistance < distance.get(next)) {
                    distance.put(next, newDistance);
                    previous.put(next, current);
                }
            }
        }

        return new DijkstraResult(distance, previous);
    }
}
