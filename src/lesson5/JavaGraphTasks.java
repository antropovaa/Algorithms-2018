package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    public JavaGraphTasks() {
    }

    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     *
     * Трудоемкость: T = O(V * E), где n - количество вершин в графе
     * Ресурсоемкость: R = O(V + E)
     */

    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Vertex> result = new ArrayList<>();
        Set<Graph.Vertex> V = graph.getVertices();
        Set<Graph.Edge> E = graph.getEdges();
        List<Graph.Edge> eulerLoop = new ArrayList<>();

        if (isThereEulerLoop(graph)) {
            findLoop(V.iterator().next(), graph, V, E, result);

            for (int i = 0; i < result.size() - 1; i++) {
                eulerLoop.add(graph.getConnection(result.get(i), result.get(i + 1)));
            }
        }

        return eulerLoop;
    }

    private static void findLoop(Graph.Vertex v, Graph graph, Set<Graph.Vertex> V,
                                 Set<Graph.Edge> E, List<Graph.Vertex> result) {
        for (Graph.Vertex u : V) {
            Graph.Edge e = graph.getConnection(v, u);
            if (E.contains(e)) {
                E.remove(e);
                findLoop(u, graph, V, E, result);
            }
        }
        result.add(v);
    }

    public static boolean isThereEulerLoop(Graph graph) {
        int oddVertex = 0;
        for (Graph.Vertex vertex : graph.getVertices()) {
            if (graph.getConnections(vertex).size() % 2 == 1) {
                oddVertex++;
            }
        }
        return oddVertex == 0;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     *
     * Трудоемкость: T = O(?)
     * Ресурсоемкость: R = O(memory.size + (children.size + grandchildren.size) * V) = O(V + V^2) = O(V^2),
     * где V - общее количество вершин в графе
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Map<Graph.Vertex, Set<Graph.Vertex>> memory = new HashMap<>();
        Graph.Vertex vertex = graph.getVertices().iterator().next();
        return largestIndependentVertexSet(memory, vertex, vertex, graph);
    }

    private static Set<Graph.Vertex> largestIndependentVertexSet(Map<Graph.Vertex, Set<Graph.Vertex>> memory,
                                                                 Graph.Vertex vertex, Graph.Vertex parent,
                                                                 Graph graph) {
        if (!memory.containsKey(vertex)) {
            Set<Graph.Vertex> children = new HashSet<>();
            Set<Graph.Vertex> grandchildren = new HashSet<>();

            for (Graph.Vertex child : graph.getNeighbors(vertex)) {
                if (child != parent) {
                    children.addAll(largestIndependentVertexSet(memory, child, vertex, graph));

                    for (Graph.Vertex grandchild : graph.getNeighbors(child)) {
                        if (grandchild != vertex) {
                            grandchildren.addAll(largestIndependentVertexSet(memory, grandchild, child, graph));
                        }
                    }
                }
                grandchildren.add(vertex);

                if (children.size() > grandchildren.size())
                    memory.put(vertex, children);
                else {
                    memory.put(vertex, grandchildren);
                }
            }
        }

        return memory.get(vertex);
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }
}
