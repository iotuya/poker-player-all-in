package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by Tomislav on 25.4.2015..
 */
public class Hand {
    private int firstRank;
    private int secondRank;

    private String firstSuite;
    private String secondSuite;

    public Hand(JsonArray holeCards) {
        JsonObject firstCard = holeCards.get(0).getAsJsonObject();
        firstRank = parseRank(firstCard.get("rank").getAsString());
        firstSuite = firstCard.get("suit").getAsString();
        JsonObject secondCard = holeCards.get(1).getAsJsonObject();
        secondRank = parseRank(secondCard.get("rank").getAsString());
        secondSuite = secondCard.get("suit").getAsString();
    }

    private int parseRank(String rank) {
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException e) {
            switch (rank) {
                case "J":
                    return 11;
                case "Q":
                    return 12;
                case "K":
                    return 13;
                case "A":
                    return 14;
                default:
                    return 0;
            }
        }
    }

    public boolean isPocketPair() {
        return firstRank == secondRank;
    }

    public boolean isCrap() {
        return (!isPocketPair() && !firstSuite.equals(secondSuite)) || (bigDiff() && !highCard());
    }

    private boolean highCard() {
        return firstRank >= 10 || secondRank >= 10;
    }

    private boolean bigDiff() {
        return Math.abs(firstRank - secondRank) > 2;
    }
}
