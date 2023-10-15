package model;

import org.json.JSONObject;

import java.util.List;

//public abstract class for a habit- has most of the behaviour that every habit needs
public abstract class Habit {
    //EFFECTS : renames Habit with new name.
    public abstract void renameHabit(String newName);


    // EFFECTS : returns name of habit
    public abstract String getName();

    //EFFECTS : returns habit history
    public abstract List getHistory();

    //EFFECTS : returns current object as JSON Object.

    public abstract JSONObject toJson();
}
