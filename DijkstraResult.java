import java.util.*;

public class DijkstraResult {
    private final Map<Vertex, Integer> distances;
    private final Map<Vertex, Vertex> previous;

    public DijkstraResult(Map<Vertex, Integer> distances,
                          Map<Vertex, Vertex> previous) {
        this.distances = new HashMap<>(distances);
        this.previous = new HashMap<>(previous);
    }

    public Map<Vertex, Integer> getDistances() {
        return Collections.unmodifiableMap(distances);
    }

    public int getDistance(Vertex vertex) {
        return distances.getOrDefault(
            vertex, Integer.MAX_VALUE);
    }

    public List<Vertex> buildPath(Vertex start, Vertex finish) {
        LinkedList<Vertex> path = new LinkedList<>();

        if (getDistance(finish) == Integer.MAX_VALUE) {
            return path;
        }

        Vertex current = finish;

        while (current != null) {
            path.addFirst(current);

            if (current.equals(start)) {
                return path;
            }

            current = previous.get(current);
        }

        return Collections.emptyList();
    }
}
