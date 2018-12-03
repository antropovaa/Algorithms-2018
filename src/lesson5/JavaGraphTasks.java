package lesson5;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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
     * Трудоемкость: T = O(n^2), где n - количество вершин в графе
     * Ресурсоемкость: R = O(n)
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        Set<Graph.Vertex> V = graph.getVertices();
        Set<Graph.Edge> E = graph.getEdges();
        List<Graph.Vertex> result = new ArrayList<>();
        List<Graph.Edge> eulerLoop = new ArrayList<>();

        if (itIsEulerGraph(graph)) {
            Stack<Graph.Vertex> s = new Stack<>();
            Graph.Vertex v = V.iterator().next();
            s.push(v);
            while (!s.empty()) {
                Graph.Vertex w = s.peek();
                for (Graph.Vertex u : V) {
                    Graph.Edge e = graph.getConnection(w, u);
                    if (E.contains(e)) { // нашли ребро, по которому еще не проходили
                        s.push(u);
                        E.remove(e); // удалили это ребро
                        break;
                    }
                }
                if (w == s.peek()) { // не нашли инцидентных ребер для вершины w, по которым еще не прошли
                    s.pop();
                    result.add(w);
                }
            }

            for (int i = 0; i < result.size() - 1; i++) {
                eulerLoop.add(graph.getConnection(result.get(i), result.get(i + 1)));
            }
        } else
            throw new IllegalArgumentException("This graph is not Euler graph, it is impossible to find Euler loop.");

        return eulerLoop;
    }

    private static boolean itIsEulerGraph(Graph graph) {
        int oddVertex = 0;
        for (Graph.Vertex vertex : graph.getVertices()) {
            if (graph.getEdges().size() % 2 == 1) {
                oddVertex++;
            }
        }
        return oddVertex != 2;
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
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
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
