import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Vertex> vertices = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    public Vertex addVertex(String name) {
        for (Vertex vertex : vertices) {
            if (vertex.getName().equals(name)) {
                throw new IllegalArgumentException(
                    "Вершина уже существует: " + name);
            }
        }

        Vertex vertex = new Vertex(name);
        vertices.add(vertex);
        return vertex;
    }

    public void addEdge(Vertex from, Vertex to, int weight) {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new IllegalArgumentException(
                "Обе вершины должны принадлежать графу");
        }

        if (weight < 0) {
            throw new IllegalArgumentException(
                "Алгоритм Дейкстры требует неотрицательных весов");
        }

        edges.add(new Edge(from, to, weight));
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
