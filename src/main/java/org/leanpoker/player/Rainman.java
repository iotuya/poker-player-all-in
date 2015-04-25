package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ssuciu on 4/25/2015.
 */
public class Rainman {
    public static JsonElement callRainMan(JsonElement state) {
        JsonArray allCards = state.getAsJsonObject().get("community_cards").getAsJsonArray();
        JsonArray holeCards = Player.getHoleCards(state);

        allCards.addAll(holeCards);

        return callRainMan(allCards);
    }

    public static JsonElement callRainMan(JsonArray allCards) {
        JsonElement rainman = null;
        try {
            String RAINMAN_URL = "http://rainman.leanpoker.org/rank";
            String CHARSET = "UTF-8";
            String query = "cards="  + URLEncoder.encode(allCards.toString(), CHARSET);

            URLConnection connection = new URL(RAINMAN_URL + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", CHARSET);

            String responseString = convertStreamToString(connection.getInputStream());

            rainman = new JsonParser().parse(responseString);
        } catch (IOException e) {

        }

        return rainman;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
