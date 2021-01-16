package com.techelevator.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deckOfCards = new ArrayList<>(); // ArrayList to hold a deck of cards 

	public Deck() {  // default constructor -- no params
		//  52 cards	
		Integer id = 1;
		Integer [] value = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		String[] face = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		String[] suit = {"Spades", "Hearts", "Diamonds", "Clubs"};
		
		for (int j = 0; j < suit.length; j++) {
			for (int i = 0; i < 13; i++) {
				Card currentCard = new Card(id, value[i], face[i], suit[j]);
				deckOfCards.add(currentCard);
			}
		}
	}
	
	public List<Card> getDeckOfCards() {
		return deckOfCards;
	}
	
	public void shuffle() {
		Collections.shuffle(deckOfCards); // in place shuffling of the cards
	}
	
	public Card draw() {
		int randomId = ((int)(Math.random() * deckOfCards.size()));
		Card card = deckOfCards.get(randomId);
		deckOfCards.remove(card);
		return card;
	}
	
	public String toString() {
		return "Deck is: " + deckOfCards;
	}

}
