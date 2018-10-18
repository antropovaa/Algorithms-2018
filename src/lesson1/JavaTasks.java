package lesson1;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     *
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * Трудоемкость: T = O(n * log(n)), где n - кол-во моментов времени
     * Ресурсоемкость: R = O(n)
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        Scanner input = new Scanner(new File(inputName));
        List<String> sortedTimes = new ArrayList<>();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.matches("^(([0-1]\\d)|(2[0-3])):[0-5]\\d:[0-5]\\d\n?$")) {
                sortedTimes.add(line);
            } else throw new IllegalArgumentException("Incorrect time format.");
        }
        input.close();

        Collections.sort(sortedTimes);

        FileWriter output = new FileWriter(new File(outputName));
        for (String time : sortedTimes) {
            output.write(time + "\n");
        }
        output.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * Трудоемкость: T = O(n * log(n)), где n - кол-во строчек в файле inputName
     * Ресурсоемкость: R = O(n + n) = O(n)
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        Scanner input = new Scanner(new File(inputName));
        List<List<String>> oldLines = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.matches("^[А-я]+ [А-я]+ - [А-я]+ \\d+$")) {
                String[] addresses = line.split(" - ");
                oldLines.add(Arrays.asList(addresses[0], addresses[1]));
            } else throw new IllegalArgumentException("Illegal line format.");
        }
        input.close();

        Map<String, Set<String>> newLines = new TreeMap<>();
        for (List<String> oldLine : oldLines) {
            String name = oldLine.get(0);
            String address = oldLine.get(1);

            if (!newLines.containsKey(address)) {
                Set<String> names = new TreeSet<>();
                names.add(name);
                newLines.put(address, names);
            } else
                newLines.get(address).add(name);
        }

        FileWriter output = new FileWriter(new File(outputName));
        for (String key : newLines.keySet()) {
            StringBuilder namesLine = new StringBuilder();
            Set<String> namesSet = newLines.get(key);

            if (namesSet.size() > 1) {
                for (int i = 0; i < namesSet.size() - 1; i++) {
                    namesLine.append(namesSet.toArray()[i]).append(", ");
                }
            }
            namesLine.append(namesSet.toArray()[namesSet.size() - 1]);
            output.write(key + " - " + namesLine + "\n");
        }
        output.close();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     *
     * Трудоекость: T = O(n), где n  - кол-во строчек в inputName (кол-во температур)
     * Ресурсоемкость: R = O(n)
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        Scanner input = new Scanner(new File(inputName));
        List<Integer> temperatures = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.matches("^-?\\d+.\\d$")) {
                double temperature = Double.parseDouble(line) * 10;
                if (temperature >= -2730 && temperature <= 5000) {
                    temperatures.add((int) temperature);
                } else throw new IllegalArgumentException("Illegal temperature.");
            } else throw new IllegalArgumentException("Illegal temperature.");
        }
        input.close();

        countingSort(temperatures);

        FileWriter output = new FileWriter(new File(outputName));
        for (int temp : temperatures) {
            output.write(Double.parseDouble(String.valueOf(temp)) / 10 + "\n");
        }
        output.close();
    }

    static private void countingSort(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int element : list) {
            if (element < min) {
                min = element * 10; }
            if (element > max) {
                max = element * 10;
            }
        }

        int[] buckets = new int[max - min + 1];
        for (int element : list) {
            buckets[element - min]++;
        }

        int arrayIndex = 0;
        for (int i = 0; i < buckets.length; i++) {
            for (int j = buckets[i]; j > 0; j--) {
                list.set(arrayIndex++, i + min);
            }
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     *
     * Трудоемкость: T = O(n)
     * Ресурсоемкость: R = O(n + m + l), где n - кол-во чисел, m = statistic.size(), l = mostFrequentNums.size()
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        Scanner input = new Scanner(new File(inputName));
        List<Integer> nums = new ArrayList<>();

        while (input.hasNextInt()) {
            int num = input.nextInt();
            if (num > 0) {
                nums.add(num);
            } else throw new IllegalArgumentException("Illegal number format.");
        }

        Map<Integer, Integer> statistic = new HashMap<>();
        List<Integer> mostFrequentNums = new ArrayList<>();
        int maxRepeats = 0;
        for (Integer num : nums) {
            int repeats = statistic.get(num) == null ? 1 : (statistic.get(num) + 1);
            if (repeats == maxRepeats) {
                mostFrequentNums.add(num);
            }
            if (repeats > maxRepeats) {
                maxRepeats = repeats;
                mostFrequentNums.clear();
                mostFrequentNums.add(num);
            }
            statistic.put(num, repeats);
        }

        int minFrequentNum = mostFrequentNums.get(0);
        for (int i = 1; i < mostFrequentNums.size(); i++) {
            int num = nums.get(i);
            if (mostFrequentNums.get(i) < minFrequentNum) {
                minFrequentNum = mostFrequentNums.get(i);
            }
        }

        FileWriter output = new FileWriter(new File(outputName));
        for (int num : nums) {
            if (num != minFrequentNum) {
                output.write(num + "\n");
            }
        }
        for (int i = 0; i < maxRepeats; i++) {
            output.write(minFrequentNum + "\n");
        }
        output.close();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     *
     * Трудоемкость: T = O(n), где n  = second.length
     * Ресурсоемкость: R = O(1)
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int li = 0, ri = first.length;
        int begin = 0; int end = second.length;
        for (int i = begin; i < end; i++) {
            if (li < first.length && (ri == second.length || first[li].compareTo(second[ri]) <= 0)) {
                second[i] = first[li++];
            }
            else {
                second[i] = second[ri++];
            }
        }
    }
}