package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by Tomislav on 25.4.2015..
 */
public class Hand {

    private Card firstCard;
    private Card secondCard;

    public Hand(JsonArray holeCards) {
        this.firstCard = new Card(holeCards.get(0).getAsJsonObject());
        this.secondCard = new Card(holeCards.get(1).getAsJsonObject());
    }



    public boolean isPocketPair() {
        return firstCard.getRank() == secondCard.getRank();
    }

    public boolean isCrap() {
        return !isPocketPair()
                && !firstCard.getSuite().equalsIgnoreCase(secondCard.getSuite())
                && bigDiff()
                && !highCard();
    }

    private boolean highCard() {
        return firstCard.isHigh() || secondCard.isHigh();
    }

    private boolean bigDiff() {
        return Math.abs(firstCard.getRank() - secondCard.getRank()) > 2;
    }
}
