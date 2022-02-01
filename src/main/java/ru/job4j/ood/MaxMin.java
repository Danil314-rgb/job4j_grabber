package ru.job4j.ood;

import java.util.Comparator;
import java.util.List;

public class MaxMin {

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return search(value, comparator);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return max(value, comparator.reversed());
    }

    public <T> T search(List<T> value, Comparator<T> comparator) {
        T result = value.get(0);
        for (T t : value) {
            if (comparator.compare(t, result) > 0) {
                result = t;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 1);
        Comparator<Integer> comparator= Integer::compareTo;
        System.out.println(maxMin.max(list, comparator));
        System.out.println(maxMin.min(list, comparator));
    }
}
