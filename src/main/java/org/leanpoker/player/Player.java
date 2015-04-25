package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class Player {

    static final String VERSION = "defaults suck";
    static Queue<ShowdownData> latestData = new ArrayDeque<>(10);

    public static int betRequest(JsonElement request) {
        int buyIn = request.getAsJsonObject().get("current_buy_in").getAsInt();
        buyIn = buyIn - getMe(request).getAsJsonObject().get("bet").getAsInt();
        int round = request.getAsJsonObject().get("round").getAsInt();
        Hand hand = new Hand(getHoleCards(request));

        if (round == 0) {
            if (hand.isPocketPair()) {
                buyIn = (buyIn + request.getAsJsonObject().get("minimum_raise").getAsInt()) * 2;
            }

            if (hand.isCrap()) {
                buyIn = 0;
            }
        }
        return buyIn;
    }

    public static void showdown(JsonElement game) {

//        latestData.remove();
//        latestData.add();
    }


    private static JsonArray getHoleCards(JsonElement state) {
        JsonElement me = getMe(state);
        return me.getAsJsonObject().get("hole_cards").getAsJsonArray();
    }

    private static JsonElement getMe(JsonElement state) {
        int myIndex = state.getAsJsonObject().get("in_action").getAsInt();
        return state.getAsJsonObject().get("players").getAsJsonArray().get(myIndex);
    }

    private static JsonElement callRainMan(JsonElement state) {
        JsonArray communityCards = state.getAsJsonObject().get("community_cards").getAsJsonArray();
        JsonArray holeCards = getHoleCards(state);

        communityCards.addAll(holeCards);
        //TODO call rain man
        return null;
    }
}
