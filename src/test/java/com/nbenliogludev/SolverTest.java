package com.nbenliogludev;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nbenliogludev
 */
class SolverTest {

    @Test
    void findsSolutionAndItWorks() {
        int V = 2;
        State start = new State(List.of(
                new Tube(List.of("R","G"), V),
                new Tube(List.of("G","R"), V),
                new Tube(List.of(), V),
                new Tube(List.of(), V)
        ), V);

        Optional<List<Solver.Move>> plan = Solver.solve(start, 100);
        assertTrue(plan.isPresent(), "No solution found within depth");

        State cur = start;
        for (Solver.Move mv : plan.get()) {
            assertTrue(cur.canPour(mv.from(), mv.to()), "Illegal move in plan");
            cur = cur.pourMax(mv.from(), mv.to());
        }
        assertTrue(cur.isSolved(), "Final state is not solved");
    }

    @Test
    void trivialAlreadySolved() {
        int V = 3;
        State solved = new State(List.of(
                new Tube(List.of("B","B","B"), V),
                new Tube(List.of(), V)
        ), V);

        var res = Solver.solve(solved, 10);
        assertTrue(res.isPresent());
        assertEquals(0, res.get().size());
    }
}