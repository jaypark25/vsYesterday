package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HabitListTest {
    private HabitList test;

    private final CheckHabit testCheck = new CheckHabit("tester");
    private final PersonalRecordHabit testPR = new PersonalRecordHabit("tester for PR");


    @BeforeEach
    void runBefore() {
        test = new HabitList();
    }

    @Test
    void testAddHabit() {
        assertTrue(test.addHabit(testCheck));
        assertTrue(test.addHabit(testPR));
        assertEquals(test.getHabitList().size(), 2);
        assertFalse(test.addHabit(testCheck));
        assertFalse(test.addHabit(testPR));
        assertEquals(test.getHabitList().size(), 2);
    }

    @Test
    void testRemoveHabit() {
        test.addHabit(testCheck);
        test.addHabit(testPR);
        assertTrue(test.removeHabit("tester"));
        assertEquals(test.getHabitList().size(), 1);
        assertFalse(test.removeHabit("tester"));
        assertTrue(test.removeHabit("tester for PR"));
        assertEquals(test.getHabitList().size(), 0);
    }

    @Test
    void testGetHabit() {
        test.addHabit(testCheck);
        test.addHabit(testPR);
        assertEquals(testCheck, test.getHabit(0));
        assertEquals(testPR, test.getHabit(1));
    }


}
