package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void testBetRequest() throws Exception {

        JsonElement jsonElement = new JsonParser().parse("{\"key1\": \"value1\", \"key2\": \"value2\"}");

        assertEquals(0, Player.betRequest(jsonElement));

    }

    @Test
    public void testGetRainmanQuery() throws Exception {
        JsonElement cards = new JsonParser().parse("{cards=[{\"rank\":\"5\",\"suit\":\"diamonds\"},{\"rank\":\"6\",\"suit\":\"diamonds\"}," +
                "{\"rank\":\"7\",\"suit\":\"diamonds\"},{\"rank\":\"7\",\"suit\":\"spades\"},{\"rank\":\"8\",\"suit\":\"diamonds\"}," +
                "{\"rank\":\"9\",\"suit\":\"diamonds\"}]}");

        JsonArray cardsArray = cards.getAsJsonObject().get("cards").getAsJsonArray();
        JsonElement element = Rainman.callRainMan(cardsArray);

        assertTrue(element != null);

    }



}
