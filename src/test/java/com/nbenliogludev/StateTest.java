package com.nbenliogludev;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nbenliogludev
 */
class StateTest {

    @Test
    void canPourAndPourMax_basic() {
        int V = 4;
        State s = new State(List.of(
                new Tube(List.of("R","R"), V),
                new Tube(List.of(), V),
                new Tube(List.of("G"), V)
        ), V);

        assertTrue(s.canPour(0, 1));
        State s2 = s.pourMax(0, 1);

        assertEquals(0, s2.tube(0).size());
        assertEquals(List.of("R","R"), s2.tube(1).view());

        assertFalse(s2.canPour(2, 1));
    }

    @Test
    void isSolved_whenAllMonoOrEmpty() {
        int V = 2;
        State solved = new State(List.of(
                new Tube(List.of("R","R"), V),
                new Tube(List.of("B","B"), V),
                new Tube(List.of(), V)
        ), V);
        assertTrue(solved.isSolved());

        State notSolved = new State(List.of(
                new Tube(List.of("R","B"), V),
                new Tube(List.of(), V)
        ), V);
        assertFalse(notSolved.isSolved());
    }

    @Test
    void pourRespectsCapacityAndTopBlock() {
        int V = 3;
        State s = new State(List.of(
                new Tube(List.of("R","R","R"), V),
                new Tube(List.of("R"), V)
        ), V);

        assertTrue(s.canPour(0, 1));
        State s2 = s.pourMax(0, 1);

        assertEquals(List.of("R"), s2.tube(0).view());
        assertEquals(List.of("R","R","R"), s2.tube(1).view());
        assertTrue(s2.tube(1).isFull());
    }
}