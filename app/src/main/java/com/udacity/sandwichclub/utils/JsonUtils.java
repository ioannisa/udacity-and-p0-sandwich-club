package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            // retrieve the root
            JSONObject sandwitchJson = new JSONObject(json);

            // For every direct descendant item, store its value to a String variable.
            String placeOfOrigin = sandwitchJson.getString("placeOfOrigin");
            String description = sandwitchJson.getString("description");
            String image = sandwitchJson.getString("image");

            // The ingredients are an array, so we retrieve this array, and store each value in a List<String>
            JSONArray ingredientsJsonArray = sandwitchJson.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<String>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredientsList.add(ingredientsJsonArray.getString(i));
            }

            // The name object contains both direct items and array, so we fetch it as an individual json document
            JSONObject nameJSON = new JSONObject(sandwitchJson.getString("name"));
            // Save the "Main Name" to a String variable
            String mainName = nameJSON.getString("mainName");
            // The "Also Known As" section can contain multiple names, so we create an array
            // and put all its items in List<String>
            JSONArray knownAsArray = nameJSON.getJSONArray("alsoKnownAs");
            List<String> knownAsList = new ArrayList<String>();
            for (int i = 0; i < knownAsArray.length(); i++) {
                knownAsList.add(knownAsArray.getString(i));
            }

            // return a new Sandwitch object by initializing its constructor with the required values
            return new Sandwich(mainName, knownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
            // if some error occurred, return null
            return null;
        }
    }
}
