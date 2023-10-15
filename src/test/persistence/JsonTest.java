package persistence;


import model.Habit;

import static org.junit.jupiter.api.Assertions.assertEquals;

//CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.

public class JsonTest {


    protected void checkHabit(String name, Habit habit) {
        assertEquals(name, habit.getName());

    }
}
