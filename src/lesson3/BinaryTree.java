package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     *
     * Трудоемкость: T = O(logN), где N - количество узлов в дереве
     * Ресурсоемкость: R = O(1)
     */
    @Override
    public boolean remove(Object o) {
        Node<T> current = root;
        Node<T> parent = root;

        boolean isLeftChild = true;
        int compare;

        while ((compare = current.getValue().compareTo((T) o)) != 0) {
            parent = current;

            if (compare > 0) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }

            if (current == null)
                return false;
        }

        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else
                parent.right = null;

        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else
                parent.right = current.left;

        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else
                parent.right = current.right;

        } else {
            Node replacement = getReplacementNode(current);

            if (current == root) {
                root = replacement;
            } else if (isLeftChild) {
                parent.left = replacement;
            } else
                parent.right = replacement;

            replacement.left = current.left;
        }
        size--;
        return true;
    }

    public Node<T> getReplacementNode(Node<T> replaceNode) {
        Node<T> replacementParent = replaceNode;
        Node<T> replacement = replaceNode;
        Node<T> focusNode = replaceNode.right;

        while (focusNode != null) {
            replacementParent = replacement;
            replacement = focusNode;
            focusNode = focusNode.left;
        }

        if (replacement != replaceNode.right) {
            replacementParent.left = replacement.right;
            replacement.right = replaceNode.right;
        }

        return replacement;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private LinkedList<Node<T>> list = new LinkedList<>();
        private boolean nextIteration = false;

        private BinaryTreeIterator() { }

        /**
         * Поиск следующего элемента
         * Средняя
         *
         * Трудоемкость: T = O(logN)
         * Ресурсоемкость: R = O(N)
         */
        private Node<T> findNext() {
            if (root == null)
                return null;

            if (current != null && current.getValue() == last())
                return null;

            if (current == null) {
                current = root;
                while (current.left != null) {
                    list.addLast(current);
                    current = current.left;
                }
                return current;
            }

            if (current.right == null) {
                current = list.getLast();
                list.removeLast();
                return current;
            }

            current = current.right;
            while (current.left != null) {
                list.addLast(current);
                current = current.left;
            }
            return current;
        }

        @Override
        public boolean hasNext() {
            nextIteration = true;
            return findNext() != null;
        }

        @Override
        public T next() {
            if (nextIteration) {
                nextIteration = false;
                return current.value;
            }
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.getValue();
        }

        /**
         * Удаление следующего элемента
         * Сложная
         *
         * Трудоемкость: T = O(logN), т.к. в методе next() используется метод findNext()
         * Ресурсоемкость: R = O(N)
         */
        @Override
        public void remove() {
            T last = current.getValue();

            if (!hasNext()) {
                BinaryTree.this.remove(last);
                current = find(last());
            } else {
                BinaryTree.this.remove(last);
                next();
            }
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
