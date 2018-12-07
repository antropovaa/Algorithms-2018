package lesson6;

import kotlin.NotImplementedError;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     *
     * Трудоемкость: T = O(height + width + height + width * height) = O(width * height) = O(N), где N - кол-во клеток поля
     * Ресурсоемкость: R = O(lines.size + field.size + metricField.size) = O(N)
     */
    public static int shortestPathOnField(String inputName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(inputName));
        List<String[]> lines = new ArrayList<>();
        int height = 0;
        int width = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.matches("^[0-9]( [0-9])*$")) {
                String[] numbers = line.split(" ");
                lines.add(numbers);
                width = numbers.length;
                height++;
            } else throw new IllegalArgumentException("Incorrect field format.");
        }
        input.close();

        String[][] field = new String[height][width];
        int[][] metricField = new int[height][width];
        for (int i = 0; i < height; i++) {
            field[i] = lines.get(i);
        }

        metricField[0][0] = Integer.parseInt(field[0][0]);
        for (int w = 1; w < width; w++) {
            metricField[0][w] = metricField[0][w - 1] + Integer.parseInt(field[0][w]);
        }
        for (int h = 1; h < height; h++) {
            metricField[h][0] = metricField[h - 1][0] + Integer.parseInt(field[h][0]);
        }

        for (int h = 1; h < height; h++) {
            for (int w = 1; w < width; w++) {
                int hSum = metricField[h][w - 1];
                int wSum = metricField[h - 1][w];
                int diagSum = metricField[h - 1][w - 1];
                metricField[h][w] = Math.min(diagSum, Math.min(hSum, wSum)) + Integer.parseInt(field[h][w]);
            }
        }

        return metricField[height - 1][width - 1];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
