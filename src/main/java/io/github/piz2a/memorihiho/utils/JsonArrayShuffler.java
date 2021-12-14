package io.github.piz2a.memorihiho.utils;

import org.json.simple.JSONArray;

import java.util.Random;

public class JsonArrayShuffler {

    public static JSONArray shuffle(JSONArray array) {
        // Fisher–Yates shuffle
        JSONArray newArray = (JSONArray) array.clone();
        Random random = new Random();
        for (int i = array.size() - 1; i >= 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap
            Object object = newArray.get(j);
            newArray.set(j, newArray.get(i));
            newArray.set(i, object);
        }
        return newArray;
    }
}
