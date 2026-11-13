import java.util.List;

public class Main {
    public static Graph createGraph() {
        Graph graph = new Graph();

        Vertex A = graph.addVertex("A");
        Vertex B = graph.addVertex("B");
        Vertex C = graph.addVertex("C");
        Vertex D = graph.addVertex("D");
        Vertex E = graph.addVertex("E");
        Vertex F = graph.addVertex("F");

        // Вариант C11: отрицательные веса заменены модулями.
        graph.addEdge(A, B, 7);
        graph.addEdge(A, C, 9);
        graph.addEdge(C, B, 3);
        graph.addEdge(B, D, 1);
        graph.addEdge(B, E, 2);
        graph.addEdge(C, E, 2);
        graph.addEdge(D, E, 1);
        graph.addEdge(D, F, 3);
        graph.addEdge(E, F, 3);

        return graph;
    }

    public static void main(String[] args) {
        Graph graph = createGraph();

        Vertex start = graph.getVertices().get(0);
        Vertex finish = graph.getVertices().get(5);

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, start);

        System.out.println(
            "Вариант 3 (C11). Алгоритм Дейкстры");
        System.out.println("Кратчайшие расстояния от A:");

        for (Vertex vertex : graph.getVertices()) {
            int d = result.getDistance(vertex);

            System.out.println(vertex.getName() + " = "
                + (d == Integer.MAX_VALUE ? "INF" : d));
        }

        List<Vertex> path = result.buildPath(start, finish);

        if (path.isEmpty()) {
            System.out.println("Путь A -> F отсутствует.");
        } else {
            System.out.println(
                "Кратчайший путь A -> F: " + formatPath(path));
            System.out.println(
                "Вес пути: " + result.getDistance(finish));
        }
    }

    private static String formatPath(List<Vertex> path) {
        StringBuilder result = new StringBuilder();

        for (Vertex vertex : path) {
            if (result.length() > 0) {
                result.append(" -> ");
            }
            result.append(vertex.getName());
        }

        return result.toString();
    }
}
