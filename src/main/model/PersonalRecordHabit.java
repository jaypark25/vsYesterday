package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
//A class of a personal record recording havit

public class PersonalRecordHabit extends Habit implements Writable {
    private String habitName;
    private List<Integer> recordHistory;

    //Creates a Personal RecordHabit with name and empty record history.
    public PersonalRecordHabit(String name) {
        this.habitName = name;
        recordHistory = new ArrayList<>();
    }

    public void setHistory(List hist) {
        recordHistory = hist;
    }

    //EFFECTS : renames record with new name
    @Override
    public void renameHabit(String newName) {
        habitName = newName;
    }


    @Override
    public String getName() {
        return habitName;
    }

    //EFFECTS : adds a record of PR to the recordHistory.
    public void addPR(int personalRecord) {
        recordHistory.add(personalRecord);
        EventLog.getInstance().logEvent(new Event(habitName + "Day Recorded"));
    }

    @Override
    public List getHistory() {
        return recordHistory;
    }


    //CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.
    //EFFECT : returns the PRHabit as a JSON Object

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", habitName);
        json.put("category", "pr");
        json.put("history", historyToJson());
        return json;
    }

    //EFFECTS : returns the PRHabit History as a JSON Object

    private JSONArray historyToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Integer i : recordHistory) {
            jsonArray.put(i.toString());
        }
        return jsonArray;
    }
}
