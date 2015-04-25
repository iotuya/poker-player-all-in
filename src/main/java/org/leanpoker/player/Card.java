package org.leanpoker.player;

import com.google.gson.JsonObject;

/**
 * Created by Tomislav on 25.4.2015..
 */
public class Card {
    private static final String SUIT = "suit";
    private static final String RANK = "rank";

    private int rank;
    private String suite;

    public Card(JsonObject card) {
        rank = parseRank(card.get(RANK).getAsString());
        suite = card.get(SUIT).getAsString();
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

    public int getRank() {
        return rank;
    }

    public String getSuite() {
        return suite;
    }

    public boolean isHigh() {
        return rank >= 11;
    }

    public boolean isHighInSuite() {
        return rank >= 5;
    }
}
