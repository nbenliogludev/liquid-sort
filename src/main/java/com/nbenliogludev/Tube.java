package com.nbenliogludev;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author nbenliogludev
 */
public class Tube {
    private final List<String> cells;
    private final int volume;

    Tube(List<String> cells, int volume) {
        this.cells = cells;
        this.volume = volume;
    }

    public int capacity() { return volume; }

    public int size() { return cells.size();}

    boolean isEmpty() { return cells.isEmpty(); }

    boolean isFull() { return cells.size() == volume;}

    String topColor() { return isEmpty() ? null : cells.getLast(); }

    List<String> view() { return cells; }

    boolean isPerfectMono() {
        if (size() != volume || isEmpty()) return false;
        String c = cells.getFirst();
        for (String x : cells) if (!Objects.equals(x, c)) return false;
        return true;
    }

    int topRunLen() {
        if (isEmpty()) return 0;
        String c = topColor();
        int k = 0;
        for (int i = cells.size() - 1; i >= 0; --i) {
            if (!Objects.equals(cells.get(i), c)) break;
            k++;
        }
        return k;
    }

    Tube pushMany(String color, int count) {
        var arr = new ArrayList<>(cells);
        for (int i = 0; i < count; i++) arr.add(color);
        return new Tube(arr, volume);
    }

    Tube popMany(int count) {
        if (count <= 0 || count > cells.size())
            throw new IllegalArgumentException("bad count");
        var arr = new ArrayList<>(cells);
        for (int i = 0; i < count; i++) arr.remove(arr.size()-1);
        return new Tube(arr, volume);
    }

}
