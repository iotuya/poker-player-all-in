package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by Tomislav on 25.4.2015..
 */
public class Hand {
    private String firstRank;
    private String secondRank;

    private String firstSuite;
    private String secondSuite;

    public Hand(JsonArray holeCards) {
        JsonObject firstCard = holeCards.get(0).getAsJsonObject();
        firstRank = firstCard.get("rank").getAsString();
        firstSuite = firstCard.get("suite").getAsString();
        JsonObject secondCard = holeCards.get(1).getAsJsonObject();
        secondRank = secondCard.get("rank").getAsString();
        secondSuite = secondCard.get("suite").getAsString();
    }

    public boolean isPocketPair() {
        return firstRank.equals(secondRank);
    }
}
