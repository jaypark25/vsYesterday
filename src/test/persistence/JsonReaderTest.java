package persistence;


import model.Habit;
import model.HabitList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.
class JsonReaderTest extends JsonTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            HabitList wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            HabitList hl = reader.read();
            assertEquals(0, hl.getHabitList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHabitList.json");
        try {
            HabitList hl = reader.read();
            List<Habit> listOfHabit = hl.getHabitList();
            assertEquals(2, listOfHabit.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}