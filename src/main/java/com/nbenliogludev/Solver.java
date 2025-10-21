package com.nbenliogludev;

import java.util.*;

/**
 * @author nbenliogludev
 */
public final class Solver {

    public record Move(int from, int to) {}

    public static Optional<List<Move>> solve(State start, int maxDepth) {
        if (start.isSolved()) return Optional.of(List.of());

        Set<State> visited = new HashSet<>();
        List<Move> path = new ArrayList<>();

        boolean ok = dfs(start, maxDepth, visited, path);
        return ok ? Optional.of(List.copyOf(path)) : Optional.empty();
    }

    // Рекурсивный DFS
    private static boolean dfs(State state, int depthLeft,
                               Set<State> visited, List<Move> path) {
        if (state.isSolved()) return true;
        if (depthLeft == 0) return false;
        if (!visited.add(state)) return false;

        int n = state.nTubes();
        for (int from = 0; from < n; from++) {
            for (int to = 0; to < n; to++) {
                if (from == to) continue;
                if (!state.canPour(from, to)) continue;

                State next = state.pourMax(from, to);
                if (next.equals(state)) continue;

                path.add(new Move(from, to));
                if (dfs(next, depthLeft - 1, visited, path)) return true;
                path.remove(path.size() - 1);
            }
        }
        return false;
    }

    private Solver() {}
}
