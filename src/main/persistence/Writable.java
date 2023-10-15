package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

//WRITABLE
public interface Writable {
    // EFFECTS: returns this as JSON object
    //CREDITS : code attributed to CPSC 210 JSON Steriliazation Demo.
    JSONObject toJson();

}
