package persistence;


import model.CheckHabit;
import model.Habit;
import model.HabitList;
import model.PersonalRecordHabit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.

    @Test
    void testWriterInvalidFile() {
        try {
            HabitList hl = new HabitList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            HabitList hl = new HabitList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHabitList.json");
            writer.open();
            writer.write(hl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHabitList.json");
            hl = reader.read();
            assertEquals(0, hl.getHabitList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            HabitList hl = new HabitList();
            CheckHabit ch = new CheckHabit("hi");
            PersonalRecordHabit pr = new PersonalRecordHabit("pr");
            hl.addHabit(ch);
            hl.addHabit(pr);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHabitList.json");
            writer.open();
            writer.write(hl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHabitList.json");
            hl = reader.read();
            List<Habit> listOfHabit = hl.getHabitList();
            assertEquals(2, listOfHabit.size());
            checkHabit("hi", listOfHabit.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterHistory() {
        try {


            HabitList hl = new HabitList();
            CheckHabit ch = new CheckHabit("ch");
            PersonalRecordHabit pr = new PersonalRecordHabit("pr");

            hl.addHabit(ch);
            hl.addHabit(pr);
            ch.recordDay(true);
            ch.recordDay(false);
            pr.addPR(12);
            JsonWriter writer = new JsonWriter("./data/testWriterHistory.json");
            writer.open();
            writer.write(hl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterHistory.json");
            hl = reader.read();
            List<Habit> listOfHabit = hl.getHabitList();
            assertEquals(2, listOfHabit.size());
            assertEquals(true, listOfHabit.get(0).getHistory().get(0));
            assertEquals(false, listOfHabit.get(0).getHistory().get(2));
            assertEquals(12, listOfHabit.get(1).getHistory().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");

        }
    }
}