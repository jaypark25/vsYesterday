package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckHabitTest {
    private CheckHabit test;
    @BeforeEach
    void runBefore() {
        test = new CheckHabit("test");
    }

    @Test
    void testRename() {
        test.renameHabit("hello");
        assertEquals("hello", test.getName());
    }

    @Test
    void testRecordDay() {
        test.recordDay(true);
        test.recordDay(false);
        assertEquals(test.getHistory().size(), 2);
        assertEquals(test.getHistory().get(0), true);
        assertEquals(test.getHistory().get(1), false);
    }


}