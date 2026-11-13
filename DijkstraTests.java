
import java.util.List;

public class DijkstraTests {

    public static void main(String[] args) {
        testMainGraph();
        testSingleVertex();
        testUnreachableVertex();
        testGraphWithCycle();
        testGraphWithoutCycle();
        testAlternativePaths();
        testRepeatedRun();

        System.out.println("Все 7 тестов пройдены.");
    }

    private static void testMainGraph() {
        Graph graph = Main.createGraph();
        Vertex a = find(graph, "A");
        Vertex f = find(graph, "F");

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, a);

        check(result.getDistance(f) == 11,
            "Основной граф: расстояние до F должно быть 11");

        check(format(result.buildPath(a, f))
                .equals("A -> B -> D -> F"),
            "Основной граф: неверный путь");
    }

    private static void testSingleVertex() {
        Graph graph = new Graph();
        Vertex a = graph.addVertex("A");

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, a);

        check(result.getDistance(a) == 0,
            "Одна вершина: расстояние должно быть 0");

        check(format(result.buildPath(a, a)).equals("A"),
            "Одна вершина: путь должен состоять из A");
    }

    private static void testUnreachableVertex() {
        Graph graph = new Graph();
        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        graph.addVertex("F");

        graph.addEdge(a, b, 2);

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, a);

        check(result.getDistance(find(graph, "F"))
                == Integer.MAX_VALUE,
            "Недостижимая F должна иметь расстояние INF");

        check(result.buildPath(a, find(graph, "F")).isEmpty(),
            "Путь до недостижимой F должен отсутствовать");
    }

    private static void testGraphWithCycle() {
        Graph graph = new Graph();
        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex f = graph.addVertex("F");

        graph.addEdge(a, b, 1);
        graph.addEdge(b, a, 1);
        graph.addEdge(b, f, 2);

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, a);

        check(result.getDistance(f) == 3,
            "Граф с циклом: расстояние до F должно быть 3");
    }

    private static void testGraphWithoutCycle() {
        Graph graph = new Graph();
        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex f = graph.addVertex("F");

        graph.addEdge(a, b, 2);
        graph.addEdge(b, f, 3);

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, a);

        check(result.getDistance(f) == 5,
            "Граф без цикла: расстояние до F должно быть 5");
    }

    private static void testAlternativePaths() {
        Graph graph = new Graph();
        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");
        Vertex f = graph.addVertex("F");

        graph.addEdge(a, b, 8);
        graph.addEdge(a, c, 2);
        graph.addEdge(c, b, 1);
        graph.addEdge(b, f, 2);

        DijkstraResult result =
            Dijkstra.shortestPaths(graph, a);

        check(result.getDistance(f) == 5,
            "Альтернативные пути: кратчайший вес должен быть 5");

        check(format(result.buildPath(a, f))
                .equals("A -> C -> B -> F"),
            "Альтернативные пути: неверный маршрут");
    }

    private static void testRepeatedRun() {
        Graph graph = Main.createGraph();
        Vertex a = find(graph, "A");
        Vertex f = find(graph, "F");

        int first = Dijkstra.shortestPaths(graph, a)
            .getDistance(f);

        int second = Dijkstra.shortestPaths(graph, a)
            .getDistance(f);

        check(first == 11 && second == 11,
            "Повторный запуск должен давать тот же результат");
    }

    private static Vertex find(Graph graph, String name) {
        for (Vertex vertex : graph.getVertices()) {
            if (vertex.getName().equals(name)) {
                return vertex;
            }
        }
        throw new IllegalArgumentException(
            "Вершина не найдена: " + name);
    }

    private static String format(List<Vertex> path) {
        StringBuilder text = new StringBuilder();

        for (Vertex vertex : path) {
            if (text.length() > 0) {
                text.append(" -> ");
            }
            text.append(vertex.getName());
        }

        return text.toString();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
        System.out.println("[OK] " + message);
    }
}
