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
        buyIn = buyIn - getMe(request).get("bet").getAsInt();
        int round = request.getAsJsonObject().get("round").getAsInt();
        int betIndex = request.getAsJsonObject().get("bet_index").getAsInt();
        int raise = 0;
        Hand hand = new Hand(getHoleCards(request));
        Random random = new Random();

        int minimumRaise = request.getAsJsonObject().get("minimum_raise").getAsInt();
        if (round == 0) {
            raise = blindStrategy(request, buyIn, betIndex, raise, hand, random, minimumRaise);
        } else {
            List<Card> cards = getCards(request.getAsJsonObject().get("community_cards").getAsJsonArray());

            if (!cards.isEmpty()) {
                JsonElement rainmanData = Rainman.callRainMan(request);
                int rank = rainmanData.getAsJsonObject().get("rank").getAsInt();
                int value = rainmanData.getAsJsonObject().get("value").getAsInt();

                Card highest = getHighestCard(cards);
                if (betIndex > 0) {
                    raise = continuedBettingStrategy(request, buyIn, hand, highest, minimumRaise, round, rank, value);
                } else {
                    if (hand.containsHighest(highest)) {
                        raise = minimumRaise;
                    }

                    if (hand.isGood(rank, value)) {
                        raise = minimumRaise * randomMultiplier(random);
                    }

                    if (request.getAsJsonObject().get("players").getAsJsonArray().size() > 2 && rank < 1 || (rank >= 1 && rank < 4 && value < 5)) {
                        return 0;
                    }
                }
            }
        }
        return buyIn + raise;
    }

    private static int continuedBettingStrategy(JsonElement request, int buyIn, Hand hand, Card highest, int minimumRaise, int round, int rank, int value) {
        if (buyIn > getMe(request).get("stack").getAsInt() / 4 || (round == 3 && rank < 1)) {
            return 0 - buyIn;
        }
        if (hand.isGood(rank, value)) {
            return minimumRaise;
        }

        return 0;

    }

    private static int blindStrategy(JsonElement request, int buyIn, int betIndex, int raise, Hand hand, Random random, int minimumRaise) {
        if (hand.isPocketPair() && betIndex == 0) {
            raise = minimumRaise * randomMultiplier(random);
        } else if (hand.isCrap() && !isBlind(request, betIndex)) {
            raise = 0 - buyIn;
        } else if (betIndex > 0) {
            if (hand.isCrap() || buyIn > 500) {
                raise = 0 - buyIn;
            }
        }
        return raise;
    }

    private static boolean highPair(Hand hand, Card card) {
        return hand.isPocketPair() || hand.containsHighest(card);
    }

    private static Card getHighestCard(List<Card> cards) {
        Card highest = cards.get(0);
        for (Card card : cards) {
            if (card.getRank() > highest.getRank()) {
                highest = card;
            }
        }
        return highest;
    }

    private static int randomMultiplier(Random random) {
        return random.nextInt(4) + 1;
    }

    private static List<Card> getCards(JsonArray community_cards) {
        List<Card> cards = new ArrayList<>();
        for (JsonElement card : community_cards) {
            cards.add(new Card(card.getAsJsonObject()));
        }

        return cards;
    }

    private static boolean isBlind(JsonElement request, int betIndex) {
        return getMe(request).get("bet").getAsInt() > 0 && betIndex == 0;
    }

    public static void showdown(JsonElement game) {

//        latestData.remove();
//        latestData.add();
    }


    public static JsonArray getHoleCards(JsonElement state) {
        JsonElement me = getMe(state);
        return me.getAsJsonObject().get("hole_cards").getAsJsonArray();
    }

    private static JsonObject getMe(JsonElement state) {
        int myIndex = state.getAsJsonObject().get("in_action").getAsInt();
        return state.getAsJsonObject().get("players").getAsJsonArray().get(myIndex).getAsJsonObject();
    }
}
