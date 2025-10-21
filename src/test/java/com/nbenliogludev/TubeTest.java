package com.nbenliogludev;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nbenliogludev
 */
class TubeTest {

    @Test
    void topColorAndRunLen() {
        Tube t1 = new Tube(List.of("R", "R", "G"), 4);
        assertEquals("G", t1.topColor());
        assertEquals(1, t1.topRunLen());

        Tube t2 = new Tube(List.of("B", "B", "B"), 4);
        assertEquals("B", t2.topColor());
        assertEquals(3, t2.topRunLen());
    }

    @Test
    void pushAndPopMany() {
        Tube t = new Tube(List.of("R"), 3);
        Tube t2 = t.pushMany("R", 2);
        assertEquals(3, t2.size());
        assertTrue(t2.isFull());
        assertThrows(IllegalArgumentException.class, () -> t2.popMany(4));

        Tube t3 = t2.popMany(2);
        assertEquals(1, t3.size());
        assertEquals("R", t3.topColor());
    }

    @Test
    void isPerfectMono() {
        Tube monoFull = new Tube(List.of("G","G","G","G"), 4);
        assertTrue(monoFull.isPerfectMono());

        Tube notFull = new Tube(List.of("G","G","G"), 4);
        assertFalse(notFull.isPerfectMono());

        Tube mixed = new Tube(List.of("G","R","G","G"), 4);
        assertFalse(mixed.isPerfectMono());
    }
}