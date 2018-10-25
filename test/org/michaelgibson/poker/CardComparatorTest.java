package org.michaelgibson.poker;

import org.junit.Test;
import org.michaelgibson.cards.Card;
import org.michaelgibson.cards.FixedCard;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/* Test class to show how to compare cards using standard poker logic.
 *
 * For now, the point of this test is to ensure Card has sufficient operations to make it possible to do this
 * kind of operation; some day we might want to develop a better version that handles other card games too.
 *
 * The current implementation is a bit of a hack; it assumes that wild cards (e.g., Jokers) are always the highest
 * card you can have.  That's fine if you're only comparing single cards, but if you're comparing hands, you may
 * want your wildcard to be something else (e.g., if you have 6, 7, WildCard, 9, 10, you might want your WildCard to be 8).
 * At any rate, this isn't a full poker game with wild cards; it's testing that the Card implementation is rich enough
 * to let us do these kinds of calculations.
 */
class PokerCardComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        int value1 = getValue(card1);
        int value2 = getValue(card2);
        return value1 - value2;
    }

    private static int getValue(Card card) {
        FixedCard.CardValue[] values = FixedCard.CardValue.values();
        if (card instanceof FixedCard) {
            final FixedCard fixedCard = (FixedCard) card;
            return Arrays.binarySearch(values, fixedCard.getValue());
        } else {
            return values.length - 1;
        }
    }
}

public class CardComparatorTest {

    @Test
    public void compare() {
        final Card twoOfHearts = new FixedCard(FixedCard.CardValue.Two, FixedCard.Suit.Hearts);
        final Card twoOfClubs = new FixedCard(FixedCard.CardValue.Two, FixedCard.Suit.Clubs);
        final Card nineOfClubs = new FixedCard(FixedCard.CardValue.Nine, FixedCard.Suit.Clubs);
        final Card jackOfDiamonds = new FixedCard(FixedCard.CardValue.Jack, FixedCard.Suit.Diamonds);
        final Card aceOfSpades = new FixedCard(FixedCard.CardValue.Ace, FixedCard.Suit.Spades);

        PokerCardComparator pokerComparator = new PokerCardComparator();
        assertTrue(pokerComparator.compare(twoOfHearts, twoOfClubs) == 0);
        assertTrue(pokerComparator.compare(twoOfHearts, nineOfClubs) < 0);
        assertTrue(pokerComparator.compare(nineOfClubs, twoOfHearts) > 0);
        assertTrue(pokerComparator.compare(twoOfHearts, jackOfDiamonds) < 0);
        assertTrue(pokerComparator.compare(jackOfDiamonds, aceOfSpades) < 0);
    }
}