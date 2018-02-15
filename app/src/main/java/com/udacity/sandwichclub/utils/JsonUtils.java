package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            String name = "";
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            if (jsonObject.has("name")) {
                JSONObject nameObj = jsonObject.getJSONObject("name");

                if (nameObj.has("mainName"))
                    name = nameObj.getString("mainName");

                if (nameObj.has("alsoKnownAs")) {
                    JSONArray alternativeNames = nameObj.getJSONArray("alsoKnownAs");
                    for (int i=0; i<alternativeNames.length(); i++)
                        alsoKnownAs.add(alternativeNames.getString(i));
                }
            }

            String placeOfOrigin = "";
            if (jsonObject.has("placeOfOrigin"))
                placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String description = "";
            if (jsonObject.has("description"))
                description = jsonObject.getString("description");

            String image = "";
            if (jsonObject.has("image"))
                image = jsonObject.getString("image");

            ArrayList<String> ingredients = new ArrayList<>();
            if (jsonObject.has("ingredients")) {
                JSONArray els = jsonObject.getJSONArray("ingredients");
                for (int i=0; i<els.length(); i++)
                    ingredients.add(els.getString(i));
            }

            return new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            return null;
        }
    }
}
