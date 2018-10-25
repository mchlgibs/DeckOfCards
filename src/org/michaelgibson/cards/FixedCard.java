package org.michaelgibson.cards;

import java.awt.*;
import java.util.Objects;

/**
 * Normal Cards.  A standard deck consists of 52 (= 4 suits x 13 cards per suit).
 * Other card types (e.g., Jokers, wild cards) are handled separately.
 *
 * Fixed cards are immutable.
 */
public class FixedCard implements Card {
    private final Suit suit;
    private final CardValue value;

    /* Right now, you can make as many of the same card as you want; if we wound up using a lot of memory, we could
     * implement a flyweight pattern here, and just always return the same ones.
     */
    public FixedCard(CardValue value, Suit suit) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedCard fixedCard = (FixedCard) o;
        return suit == fixedCard.suit &&
                value == fixedCard.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }

    @Override
    public String toString() {
        return "FixedCard{" + value + " of " + suit + '}';
    }

    /**
     * A standard deck of cards consists of 52 cards divided in 13 each of four Suits (hearts, clubs, spades, diamonds).
     * This enum contains the suit.
     */
    public enum Suit {
        Hearts,
        Clubs,
        Spades,
        Diamonds;

        /**
         * Gets the color (red or black) of the suit.
         *
         * @return the color
         */
        public Color getColor() {
            switch (this) {
                case Hearts:
                case Diamonds:
                    return Color.RED;
                case Clubs:
                case Spades:
                    return Color.BLACK;
                default:
                    // You should really only be calling this with one of the enum values (e.g., Suit.Hearts),
                    // so you shouldn't ever get here.
                    throw new IllegalArgumentException("Invalid FixedCard: " + this.toString());
            }
        }
    }

    /**
     * Values of cards in a standard deck:
     *   Number cards (2-9) + Face cards (Jack, Queen, King, Ace)
     */
    public enum CardValue {
    // Not doing anything really sophisticated; could make separate classes for numbers and face values
    // but we don't need those yet.
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King,
        Ace
    }
}