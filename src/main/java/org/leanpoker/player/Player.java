package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.Map;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        int buyIn = request.getAsJsonObject().get("current_buy_in").getAsInt();
        int round = request.getAsJsonObject().get("round").getAsInt();
        return round == 0 ? buyIn : buyIn * 2;
    }

    public static void showdown(JsonElement game) {

    }
}
