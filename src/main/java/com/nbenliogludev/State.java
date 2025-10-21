package com.nbenliogludev;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author nbenliogludev
 */
public class State {
    private final List<Tube> tubes;
    private final int volume;

    State(List<Tube> tubes, int volume) {
        this.tubes = List.copyOf(tubes);
        this.volume = volume;
    }

    int nTubes() { return tubes.size(); }

    int capacity() { return volume; }

    Tube tube(int i) { return tubes.get(i); }

    boolean isSolved() {
        for (var t : tubes) {
            if (t.isEmpty()) continue;
            if (!t.isPerfectMono()) return false;
        }
        return true;
    }

    boolean canPour(int from, int to) {
        if (from == to) return false;
        var A = tubes.get(from);
        var B = tubes.get(to);
        if (A.isEmpty() || B.isFull()) return false;
        var topA = A.topColor();
        var topB = B.topColor();
        return topB == null || topB.equals(topA);
    }

    State pourMax(int from, int to) {
        if (!canPour(from, to)) return this;
        var A = tubes.get(from);
        var B = tubes.get(to);

        int run  = A.topRunLen();
        int free = B.capacity() - B.size();
        int move = Math.min(run, free);

        var newA = A.popMany(move);
        var newB = B.pushMany(A.topColor(), move);

        var next = new ArrayList<Tube>(tubes);
        next.set(from, newA);
        next.set(to, newB);
        return new State(next, volume);
    }

    String encode() {
        return tubes.stream()
                .map(t -> String.join("\u0001", t.view()))
                .collect(Collectors.joining("\u0002"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State other)) return false;
        if (this.volume != other.volume) return false;
        if (this.tubes.size() != other.tubes.size()) return false;

        for (int i = 0; i < tubes.size(); i++) {
            var a = this.tubes.get(i).view();
            var b = other.tubes.get(i).view();
            if (a.size() != b.size()) return false;
            for (int j = 0; j < a.size(); j++) {
                if (!Objects.equals(a.get(j), b.get(j))) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h = 31 * h + volume;
        for (var tube : tubes) {
            int th = 1;
            var cells = tube.view();
            for (var c : cells) {
                th = 31 * th + (c == null ? 0 : c.hashCode());
            }
            th = 31 * th + cells.size();
            h = 31 * h + th;
        }
        return h;
    }

}
