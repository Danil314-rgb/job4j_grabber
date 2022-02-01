package ru.job4j.ood;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MaxMinTest {

    @Test
    public void max() {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 1);
        Comparator<Integer> comparator = Integer::compareTo;
        int result = maxMin.max(list, comparator);
        assertThat(result, is(6));
    }

    @Test
    public void min() {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 1);
        Comparator<Integer> comparator = Integer::compareTo;
        int result = maxMin.min(list, comparator);
        assertThat(result, is(1));
    }
}