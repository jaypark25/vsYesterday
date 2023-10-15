package persistence;

import model.CheckHabit;
import model.Habit;
import model.HabitList;
import model.PersonalRecordHabit;
import org.json.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads habitList from JSON data stored in file
//CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads habitList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HabitList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHabitList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses habitlist from JSON object and returns it
    private HabitList parseHabitList(JSONObject jsonObject) {
        HabitList hl = new HabitList();
        addHabits(hl, jsonObject);
        return hl;
    }

    // MODIFIES: hl
    // EFFECTS: parses Habits from JSON object and adds them to workroom
    private void addHabits(HabitList hl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("habitList");
        int i = 0;
        for (Object json : jsonArray) {
            JSONObject nextHabit = (JSONObject) json;
            addHabit(hl, nextHabit);
            i++;
        }
    }

    // MODIFIES: hl
    // EFFECTS: parses habits from JSON object and adds it to habitlist
    private void addHabit(HabitList hl, JSONObject jsonObject) {
        String cat = jsonObject.getString("category");
        String name = jsonObject.getString("name");
        JSONArray jsonArray = jsonObject.getJSONArray("history");


        if (cat.equals("check")) {
            CheckHabit ch = new CheckHabit(name);
            ch.setHistory(getCheckHistory(jsonArray));
            hl.addHabit(ch);
        } else {
            PersonalRecordHabit prh = new PersonalRecordHabit(name);
            prh.setHistory(getPRHistory(jsonArray));
            hl.addHabit(prh);
        }
    }

    //EFFECTS : takes a JSONArray representing the history of a checklist habit, and returns it as a List.
    private List<Boolean> getCheckHistory(JSONArray his) {
        List<Boolean> addBoolean = new ArrayList<>();
        for (int i = 0; i < his.length(); i++) {
            if (his.get(i).equals("true")) {
                addBoolean.add(true);
            } else {
                addBoolean.add(false);
            }
        }
        return addBoolean;
    }

    //EFFECTS : takes a JSONArray representing the history of a PR habit, and returns it as a List.
    private List<Integer> getPRHistory(JSONArray his) {
        List<Integer> addPR = new ArrayList<>();
        for (int i = 0; i < his.length(); i++) {

            String atEach = his.get(i).toString();
            addPR.add(Integer.parseInt(atEach));
        }
        return addPR;
    }
}
