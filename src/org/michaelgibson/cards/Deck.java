package org.michaelgibson.cards;

import java.util.*;

/**
 * Class representing a deck of cards.  Cards can be added to the deck and dealt from the deck.
 */
public class Deck {
    // Array of cards to deal.  Cards are dealt from the end of the array, for efficiency reasons
    private ArrayList<Card> cards;
    private Random random;

    /** Creates a deck of cards with the given cards, in the given order
     *
     * @param cards The array of cards to use
     */
    public Deck(Card[] cards) {
        this(new ArrayList<>(Arrays.asList(cards)));
    }

    /** Creates a deck of cards with the given cards, in the given order
     *
     * @param cards The array of cards to use
     */
    public Deck(ArrayList<Card> cards) {
        this.cards = new ArrayList<>();
        this.cards.addAll(cards);
        Collections.reverse(this.cards);
    }

    /** Creates a standard deck of 52 cards (no wildcards).
     *
     * @return the deck
     */
    static public Deck standardDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        for (FixedCard.Suit s : FixedCard.Suit.values()) {
            for (FixedCard.CardValue v : FixedCard.CardValue.values()) {
                cards.add(new FixedCard(v, s));
            }
        }

        return new Deck(cards);
    }

    /** Function used for dependency injection when testing; it's okay not to call this and just use the default.
     *
     * @param random Random number generator
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    private Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    /** The dealOneCard method will throw if there are no cards left, so this method lets you check whether there are
     *  any more cards, before calling it.
     *
     * @return Number of cards remaining to be dealt.
     */
    public int numCardsRemaining() {
        return cards.size();
    }

    /** Shuffles the deck in place.  All permutations have equal likelihood of occurring.
     */
    public void shuffle() {
        final Random random = getRandom();
        // At each step, pick a random card and put it into the last place (cards[position])
        // Note that this is O(N) - we go through the array once and gets and sets are O(1)
        for (int position = cards.size() - 1; position > 0; position--) {
            // Invariant: cards[position + 1 .. end] are locked in
            // cards[] contains all the items that it originally did

            int r = random.nextInt(position + 1); // nextInt excludes the bound
            Card c1 = cards.get(position);
            Card c2 = cards.get(r);
            cards.set(position, c2);
            cards.set(r, c1);
        }
    }

    /** Deals one card from the deck, removing it.
     *
     * When you run out of cards, throws NoSuchElementException; to avoid this, check numCardsRemaining &gt; 0 first.
     *
     * @return The top card.
     * @throws NoSuchElementException if no more cards are available.
     */
    public Card dealOneCard() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException();
        }

        final int index = cards.size() - 1;
        Card toReturn = cards.get(index);
        cards.remove(index);
        return toReturn;
    }
}
