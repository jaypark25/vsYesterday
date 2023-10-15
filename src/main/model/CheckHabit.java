package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
//class of a checklist habit.

public class CheckHabit extends Habit implements Writable {
    private String habitName;

    private List<Boolean> recordHistory;
    //Creates a CheckHabit with habitName and empty record history.

    public CheckHabit(String habitName) {
        this.habitName = habitName;
        recordHistory = new ArrayList<>();

    }

    //EFFECTS : rename this check habit with given name
    @Override
    public void renameHabit(String newName) {
        this.habitName = newName;
    }

    // EFFECTS : adds true to record history if given boolean is true, add false otherwise.
    public void recordDay(boolean completedHabit) {
        recordHistory.add(completedHabit);
        EventLog.getInstance().logEvent(new Event(habitName + "Day Recorded"));
    }

    public String getName() {
        return habitName;
    }

    public void setHistory(List hist) {
        recordHistory = hist;
    }

    @Override
    public List getHistory() {
        return recordHistory;
    }

    //effects : returns check habit as a json.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", habitName);
        json.put("category", "check");
        json.put("history", historyToJson());
        return json;
    }

    //CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.
    //effects : returns record history in jsonArray form.
    private JSONArray historyToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Boolean b : recordHistory) {
            if (b) {
                jsonArray.put("true");
            } else {
                jsonArray.put("false");
            }
        }
        return jsonArray;
    }

}
