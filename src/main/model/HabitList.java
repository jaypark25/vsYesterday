package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

//public class of a Habit list.
public class HabitList implements Writable {
    private final List<Habit> habitList;

    //Creates an empty habit list.

    public HabitList() {
        habitList = new ArrayList<>();
    }

    //REQUIRES : habit name must not be already used in Habit List
    //EFFECTS: adds habit to habitList if same habit not present and returns true, return false otherwise.
    public boolean addHabit(Habit addedHabit) {
        String habitName = addedHabit.getName();
        for (Habit h : habitList) {
            if (h.getName().equals(habitName)) {
                return false;
            }
        }
        habitList.add(addedHabit);
        EventLog.getInstance().logEvent(new Event(addedHabit.getName() + " Habit Added"));
        return true;
    }

    //REQUIRES : habit name must be already used in Habit List
    //EFFECTS: removes habit from habitList if same habit not present and returns true, return false otherwise.
    public boolean removeHabit(String removeName) {
        int i = 0;
        for (Habit h : habitList) {
            if (h.getName().equals(removeName)) {
                habitList.remove(i);
                EventLog.getInstance().logEvent(new Event(removeName + " Habit Removed."));
                return true;
            }
            i++;
        }
        EventLog.getInstance().logEvent(new Event("Failed to Remove Habit"));
        return false;
    }

    public List getHabitList() {
        return habitList;
    }

    // REQUIRES: HabitList must containt at least one habitt
    // effects : returns Habit of the given index.
    public Habit getHabit(int num) {
        return habitList.get(num);
    }

    //EFFECTS: returns the habitlist as a JSON Object,
    //CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("habitList", habitsToJson());
        return json;
    }

    // EFFECTS: returns habits in the Habitlist is a JSON array.
    private JSONArray habitsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Habit h : habitList) {
            jsonArray.put(h.toJson());
        }
        EventLog.getInstance().logEvent(new Event("File Saved."));
        return jsonArray;

    }


}
