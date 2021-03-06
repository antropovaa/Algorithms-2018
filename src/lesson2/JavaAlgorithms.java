package lesson2;

import kotlin.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * Трудоемкость: T = O(n), где n - количество строк в inputName (кол-во цен)
     * Реусурсоемкость: R = O(n)
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws FileNotFoundException {
        Scanner input = new Scanner(new FileReader(inputName));
        List<Integer> prices = new ArrayList<>();
        while (input.hasNextInt()) {
            int price = input.nextInt();
            if (price > 0) {
                prices.add(price);
            } else throw new IllegalArgumentException("Prices have to be more than 0.");
        }

        List<Integer> delta = new ArrayList<>();
        List<Pair<Integer, Integer>> indexes = new ArrayList<>();
        for (int i = 0; i < prices.size() - 1; i++) {
            delta.add(prices.get(i + 1) - prices.get(i));
            indexes.add(new Pair<>(i + 1, i + 2));
        }

        int startIndex = 0;
        int lastIndex = 0;
        int maxSum = delta.get(0);

        int lastStartIndex = 0;
        int lastSum = 0;

        for (int i = 0; i < delta.size(); i++) {
            int currentDelta = delta.get(i);
            lastSum += currentDelta;
            if (lastSum < currentDelta) {
                lastSum = currentDelta;
                lastStartIndex = i;
            }
            if (lastSum > maxSum) {
                startIndex = lastStartIndex;
                lastIndex = i;
                maxSum = lastSum;
            }
        }
        return new Pair<>(indexes.get(startIndex).getFirst(), indexes.get(lastIndex).getSecond());
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Трудоемкость: T = O(n), где n = menNumber
     * Ресурсоемкость: R = О(1)
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        if (menNumber == 0) throw new IllegalArgumentException("Number of men has to be more then 1.");
        int result = 0;
        for (int i = 1; i <= menNumber; ++i) {
            result = (result + choiceInterval) % i;
        }
        return result + 1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     *
     * Трудоемкость: Т = О(n * m), где n = first.length(), m = second.length()
     * Ресурсоемкость: R = O(n * m)
     */
    static public String longestCommonSubstring(String first, String second) {
        int[][] matches = new int[first.length()][second.length()];
        int maxLength = 0;
        int maxIndex = -1;

        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (i > 0 && j > 0) {
                    if (first.charAt(i - 1) == second.charAt(j - 1)) {
                        int currentLength = matches[i - 1][j - 1] + 1;
                        matches[i][j] = currentLength;
                        if (currentLength > maxLength) {
                            maxLength = currentLength;
                            maxIndex = i;
                        }
                    }
                }
            }
        }

        return (maxIndex == -1) ? "" : first.substring(maxIndex - maxLength, maxIndex);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     *
     * Трудоемкость: T = O(n * log(log(n))), где n = limit
     * Ресурсоемкоть: R = O(n)
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;

        int[]primes = new int[limit + 1];
        primes[0] = 1;
        primes[1] = 1;

        for (int num = 2; num <= (int) Math.sqrt(limit); num++) {
            if (num * num > limit) break;
            if (primes[num] == 0) {
                for (int i = num * num; i <= limit; i += num) {
                    primes[i] = 1;
                }
            }
        }

        int result = 0;
        for (int num : primes) {
            if (num == 0)
                result++;
        }
        return result;
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     *
     * Трудоемкость (с учетом рекурсивной функции): T = O(m^2 * n^2), где m и n - ширина и высота матрицы
     * Ресурсоемкость: R = O(m * n), где m * n - общее кол-во букв в заданной матрицы
     */
    static class Balda {
        private static List<String[]> strings;
        private static int width;
        private static int height;
        private static String[][] matrix;
        private static String currentWord;
        private static Set<String> result;

        static public Set<String> baldaSearcher(String inputName, Set<String> words) throws FileNotFoundException {
            width = 0;
            height = 0;
            strings = new ArrayList<>();
            Scanner input = new Scanner(new FileReader(inputName));
            while (input.hasNextLine()) {
                String[] string = input.nextLine().split(" ");
                width = string.length;
                strings.add(string);
                height++;
            }

            matrix = new String[height][width];
            for (int i = 0; i < height; i++) {
                matrix[i] = strings.get(i);
            }

            result = new HashSet<>();
            for (String word : words) {
                String first = String.valueOf(word.charAt(0));
                currentWord = word;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (first.equals(matrix[i][j])) {
                            int letter = 0;
                            search(i, j, letter);
                        }
                    }
                }
            }

            return result;
        }

        /**
         * Трудоемкость: T(O) = O(m * n), где m - ширина матрицы, n - высота, поскольку в самом худшем случае слово
         * "соберется" из каждого элемента матрицы, то есть функция вызовется столько раз, сколько элементов
         */
        static private void search(int row, int column, int letter) {
            if (letter != currentWord.length() - 1) {
                String nextLetter = String.valueOf(currentWord.charAt(letter + 1));
                if (column + 1 < width && matrix[row][column + 1].equals(nextLetter)) {
                    search(row, column + 1, letter + 1);
                }
                if (column - 1 >= 0 && matrix[row][column - 1].equals(nextLetter)) {
                    search(row, column - 1, letter + 1);
                }
                if (row - 1 >= 0 && matrix[row - 1][column].equals(nextLetter)) {
                    search(row - 1, column, letter + 1);
                }
                if (row + 1 < height && matrix[row + 1][column].equals(nextLetter)) {
                    search(row + 1, column, letter + 1);
                }
            } else
                result.add(currentWord);
        }
    }
}
