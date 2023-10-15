package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalRecordHabitTest {
    private PersonalRecordHabit test;
    @BeforeEach
    void runBefore() {
        test = new PersonalRecordHabit("test");
    }

    @Test
    void testRename() {
        test.renameHabit("hello");
        assertEquals("hello", test.getName());
    }

    @Test
    void testRecordPR() {
        test.addPR(12);
        test.addPR(15);
        assertEquals(test.getHistory().size(), 2);
        assertEquals(test.getHistory().get(0), 12);
        assertEquals(test.getHistory().get(1), 15);
    }

    @Test
    void testHistoryToJson() {
        test.addPR(12);

    }
}
