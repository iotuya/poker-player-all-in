package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class Player {

    static final String VERSION = "defaults suck";

    public static int betRequest(JsonElement request) {
        int buyIn = request.getAsJsonObject().get("current_buy_in").getAsInt();
        int round = request.getAsJsonObject().get("round").getAsInt();
        Hand hand = new Hand(getHoleCards(request));

        if (hand.isPocketPair()) {
            buyIn = buyIn * 2;
        }
        return round == 0 ? buyIn : buyIn * 2;
    }

    public static void showdown(JsonElement game) {
        //todo x
    }


    private static JsonArray getHoleCards(JsonElement state) {
        int myIndex = state.getAsJsonObject().get("in_action").getAsInt();
        return state.getAsJsonObject().get("players").getAsJsonArray().get(myIndex).getAsJsonObject().get("hole_cards").getAsJsonArray();
    }

    private static JsonElement callRainMan(JsonElement state) {
        JsonArray communityCards = state.getAsJsonObject().get("community_cards").getAsJsonArray();
        JsonArray holeCards = getHoleCards(state);

        communityCards.addAll(holeCards);
        //TODO call rain man
        return null;
    }
}
