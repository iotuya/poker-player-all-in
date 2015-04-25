package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        int buyIn = request.getAsJsonObject().get("current_buy_in").getAsInt();
        JsonArray players = request.getAsJsonObject().get("playeres").getAsJsonArray();
        int round = request.getAsJsonObject().get("round").getAsInt();
        int playerBet = 0;
        for (JsonElement player : players) {
            JsonObject playerObject = player.getAsJsonObject();
            if ("active".equals(playerObject.get("status"))) {
                playerBet = playerObject.get("bet").getAsInt();
                break;
            }
        }
        buyIn = buyIn - playerBet;
        return round == 0 ? buyIn : buyIn * 2;
    }

    public static void showdown(JsonElement game) {

    }
}
