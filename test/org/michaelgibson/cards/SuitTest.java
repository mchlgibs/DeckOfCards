package org.michaelgibson.cards;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class SuitTest {

    @Test
    public void names() {
        assertEquals("Hearts", FixedCard.Suit.Hearts.toString());
        assertEquals("Clubs", FixedCard.Suit.Clubs.toString());
        assertEquals("Diamonds", FixedCard.Suit.Diamonds.toString());
        assertEquals("Spades", FixedCard.Suit.Spades.toString());
    }

    @Test
    public void getColor() {
        assertEquals(Color.RED, FixedCard.Suit.Hearts.getColor());
        assertEquals(Color.BLACK, FixedCard.Suit.Clubs.getColor());
        assertEquals(Color.RED, FixedCard.Suit.Diamonds.getColor());
        assertEquals(Color.BLACK, FixedCard.Suit.Spades.getColor());
    }
}