package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.Map;

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
        } else {
            if (hand.isPocketPair()) {
                buyIn = (buyIn + request.getAsJsonObject().get("minimum_raise").getAsInt()) * 2;
            }
            //TODO: consider cards on the table
//            JsonArray communityCards = request.getAsJsonObject().get("community_cards").getAsJsonArray();
        }
        return buyIn;
    }

    public static void showdown(JsonElement game) {

//        latestData.remove();
//        latestData.add();
    }


    public static JsonArray getHoleCards(JsonElement state) {
        JsonElement me = getMe(state);
        return me.getAsJsonObject().get("hole_cards").getAsJsonArray();
    }

    private static JsonElement getMe(JsonElement state) {
        int myIndex = state.getAsJsonObject().get("in_action").getAsInt();
        return state.getAsJsonObject().get("players").getAsJsonArray().get(myIndex);
    }
}
