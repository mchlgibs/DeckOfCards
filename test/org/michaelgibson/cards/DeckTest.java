package org.michaelgibson.cards;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DeckTest {
    @Test
    public void dealOneCard() {
        final Card aceOfDiamonds = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Diamonds);
        final Card aceOfSpades = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Spades);

        final Card[] cards = {aceOfDiamonds, aceOfSpades};
        Deck d = new Deck(cards);

        assertEquals(2, d.numCardsRemaining());
        assertEquals(aceOfDiamonds, d.dealOneCard());

        assertEquals(1, d.numCardsRemaining());
        assertEquals(aceOfSpades, d.dealOneCard());

        assertEquals(0, d.numCardsRemaining());

        try {
            d.dealOneCard();
            fail("This was expected to throw an exception.");
        } catch(NoSuchElementException e) {
        }
    }

    // This test and shuffle2 ensure that we're shuffling randomly (one gets results in one order, the other in the other)
    @Test
    public void shuffle1() {
        final Card aceOfDiamonds = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Diamonds);
        final Card aceOfSpades = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Spades);

        final Card[] cards = {aceOfDiamonds, aceOfSpades};
        Deck d = new Deck(cards);
        final Random random = new Random();
        random.setSeed(0);
        d.setRandom(random);
        d.shuffle();

        assertEquals(aceOfDiamonds, d.dealOneCard());
        assertEquals(aceOfSpades, d.dealOneCard());
    }

    @Test
    public void shuffle2() {
        final Card aceOfDiamonds = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Diamonds);
        final Card aceOfSpades = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Spades);

        final Card[] cards = {aceOfDiamonds, aceOfSpades};

        Deck d = new Deck(cards);
        final Random random = new Random();
        random.setSeed(4096);
        d.setRandom(random);
        d.shuffle();

        assertEquals(aceOfSpades, d.dealOneCard());
        assertEquals(aceOfDiamonds, d.dealOneCard());
    }

    @Test
    // Test to confirm that if you don't explicitly set the random number generator, one will be added for you
    public void shuffleDefaultRandom() {
        final Card aceOfDiamonds = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Diamonds);
        final Card aceOfSpades = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Spades);

        final Card[] cards = {aceOfDiamonds, aceOfSpades};

        Deck d = new Deck(cards);
        d.shuffle();
        // Nothing to check here, except that we don't crash
    }

    @Test
    public void shuffleEmpty() {
        final Card[] cards = {};
        Deck d = new Deck(cards);
        d.shuffle();
        // Nothing to check here, except that we don't crash
    }

    @Test
    public void shuffleMany() {
        Deck d = Deck.standardDeck();
        final Random random = new Random();
        random.setSeed(4096);
        d.setRandom(random);
        d.shuffle();

        // We'll check that we deal 52 cards and don't get any duplicates
        Set<Card> dealtCards = new HashSet();

        // We'll check the beginning and ending cards explicitly
        final Card tenOfHearts = new FixedCard(FixedCard.CardValue.Ten, FixedCard.Suit.Hearts);
        final Card card1 = d.dealOneCard();
        assertEquals(tenOfHearts, card1);
        assertFalse(dealtCards.contains(card1));
        dealtCards.add(card1);

        // Skip the middle cards
        for (int i = 0; i < 50; i++) {
            final Card card = d.dealOneCard();
            assertFalse(dealtCards.contains(card));
            dealtCards.add(card);
        }

        final Card fourOfHearts = new FixedCard(FixedCard.CardValue.Four, FixedCard.Suit.Hearts);
        final Card card52 = d.dealOneCard();
        assertEquals(fourOfHearts, card52);
        assertFalse(dealtCards.contains(card52));
        dealtCards.add(card52);

        assertEquals(52, dealtCards.size());
        assertEquals(0, d.numCardsRemaining());
    }
}