package com.techelevator.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

	public List<Integer> getHandStrength(List<Card> handDealt) { 
		Card[] royalFlush = new Card[5];
		Card[] straightFlush = new Card[5];
		Card quad = new Card();
		Card[] fullHouse = new Card[2];
		Card flush = new Card();
		Card[] straight = new Card[5];
		Card triple = new Card();
		Card[] twoPair = new Card[2];
		Card pair = new Card();
		Card highCard = new Card();
		String flushSuit = "";
		List<Card> clubs = new ArrayList<Card>();
		List<Card> hearts = new ArrayList<Card>();
		List<Card> diamonds = new ArrayList<Card>();
		List<Card> spades = new ArrayList<Card>();
		List<Integer> hand = new ArrayList<Integer>();
			
					
			
		List<Card> handList = new ArrayList<Card>(handDealt); // copy so handDealt remains unmodified (handList removes duplicates so process can find straights)
		Collections.sort(handList);
		Collections.sort(handDealt);
		// takes stock of the presence of each suit
		for (int i = 0; i < handList.size(); i++) {
			if (handList.get(i).getSuit().equals("Hearts")) {
				hearts.add(handList.get(i));
			}
			if (handList.get(i).getSuit().equals("Clubs")) {
				clubs.add(handList.get(i));
			}
			if (handList.get(i).getSuit().equals("Diamonds")) {
				diamonds.add(handList.get(i));
			}
			if (handList.get(i).getSuit().equals("Spades")) {
				spades.add(handList.get(i));
			}
		}
		
		// searches for groups (2 o.a.k, 3 o.a.k, 4 o.a.k) and places matches in it's own array. deletes duplicate values for the straight-finder below it
		for (int i = 0; i < handList.size(); i++) {
			if ((i < handList.size() - 1) && (handList.get(i).getValue() == (handList.get(i + 1).getValue()))) {
				if ((i < handList.size() - 2) && (handList.get(i + 1).getValue() == (handList.get(i + 2).getValue()))) {
					if ((i < handList.size() - 3) && (handList.get(i + 2).getValue() == (handList.get(i + 3).getValue()))) {
						quad = handList.get(i);
						handList.remove(i);
						handList.remove(i);
						handList.remove(i);
					} 
					else {
						triple = handList.get(i);
						handList.remove(i);
						handList.remove(i);
					}
				} 
				else {
					if (pair.getValue() > 0) {
						twoPair[0] = pair;
						twoPair[1] = handList.get(i);
						pair = handList.get(i);
						handList.remove(i);
					} else {
					pair = handList.get(i);
					handList.remove(i);
					}
				}
			}
			else if ((i == handList.size() - 1) && (handList.get(i).getValue() == (handList.get(i - 1).getValue()))) {
				if (pair.getValue() > 0) {
					twoPair[0] = pair;
					twoPair[1] = handList.get(i);
					pair = handList.get(i);
					handList.remove(i);
				} else {
				pair = handList.get(i);
				handList.remove(i);
				}
			}
			else {
				highCard = handList.get(i);
			}
		}
		
		// searches for a straight, straightFlush, and royalFlush. if so, places the cards in it's own array.
		for (int i = 0; i < handList.size(); i++) {
			if ((i != handList.size() - 1) && (handList.get(i).getValue() - handList.get(i + 1).getValue() == -1)) {
					String suit1 = handList.get(i).getSuit();
					String suit2 = handList.get(i + 1).getSuit();
				if ((i != handList.size() - 2) && (handList.get(i + 1).getValue() - handList.get(i + 2).getValue() == -1)) {
					String suit3 = handList.get(i + 2).getSuit();
					if ((i != handList.size() - 3) && (handList.get(i + 2).getValue() - handList.get(i + 3).getValue() == -1)) {
						String suit4 = handList.get(i + 3).getSuit();
						if ((i != handList.size() - 4) && (handList.get(i + 3).getValue() - handList.get(i + 4).getValue() == -1)) {
							String suit5 = handList.get(i + 4).getSuit();
							if (suit1.equals(suit2) && suit2.equals(suit3) && suit3.equals(suit4) && suit4.equals(suit5)) {
								if (handList.get(i).getValue() == 10) {
									for (int j = 0; j < 5; j++) {
										royalFlush[j] = handList.get(i + j);
									}
								} else {
									for (int j = 0; j < 5; j++) {
										straightFlush[j] = handList.get(i + j);
									}
								}
							}
							else {
								for (int j = 0; j < 5; j++) {
									straight[j] = handList.get(i + j);
								}
							}
						}
						else {
							i+=2;
						}
					}
					else {
						i++;
					}
				}
			}
		}

		// determines if there is a full house
		if (pair.getValue() >= 1 && triple.getValue() >= 1 || pair.getValue() >= 1 && quad.getValue() >= 1 || triple.getValue() >= 1 && quad.getValue() >= 1) {
			fullHouse[0] = triple;
			fullHouse[1] = pair;		
		}
		
		// searches for a flush, and if so outputs the flush suit.
		if (hearts.size() >= 5) {
			flush = hearts.get(hearts.size() - 1);
			flushSuit = "hearts";
		} 
		if (clubs.size() >= 5) {
			flush = clubs.get(clubs.size() - 1);
			flushSuit = "clubs";
		}
		if (diamonds.size() >= 5) {
			flush = diamonds.get(diamonds.size() - 1);
			flushSuit = "diamonds";
		}
		if (spades.size() >= 5) {
			flush = spades.get(spades.size() - 1);
			flushSuit = "spades";
		}
		
		highCard = handList.get(handList.size() - 1);

		// calculates and outputs the best hand in a List = [handStrength, high card/grouping, second high card/grouping (if applicable)]
		if (royalFlush[0] != null) {
			System.out.println("Royal Flush: " + royalFlush[0].getFace() + ", " + royalFlush[1].getFace() + ", " + royalFlush[2].getFace() + ", " + royalFlush[3].getFace() + ", " + royalFlush[4].getFace() + " " + handDealt);
			hand.add(10);
			hand.add(14);
			hand.add(highCard.getValue());
		}
		else if (straightFlush[0] != null) {
			System.out.println("Straight Flush: " + straightFlush[0].getFace() + ", " + straightFlush[1].getFace() + ", " + straightFlush[2].getFace() + ", " + straightFlush[3].getFace() + ", " + straightFlush[4].getFace() + " " + handDealt);
			hand.add(9);
			hand.add(straightFlush[4].getValue());
			hand.add(highCard.getValue());
		}
		else if (quad.getValue() > 0) {
			System.out.println("Four of a Kind: " + quad.getFace() + "'s" + " " + handDealt);
			hand.add(8);
			hand.add(quad.getValue());
			hand.add(highCard.getValue());
		}
		else if (fullHouse[0] != null) {
			System.out.println("Full House: " + fullHouse[0].getFace() + "'s full of " + fullHouse[1].getFace() + "'s" + " " + handDealt);
			hand.add(7);
			hand.add(fullHouse[0].getValue());
			hand.add(fullHouse[1].getValue());
		}
		else if (flush.getValue() > 0) {
			System.out.println("Flush: " + flush.getFace() + " high, " + flushSuit + " " + handDealt);
			hand.add(6);
			hand.add(flush.getValue());
			hand.add(highCard.getValue());
		}
		else if (straight[0] != null) {
			System.out.println("Straight: " + straight[0].getFace() + ", " + straight[1].getFace() + ", " + straight[2].getFace() + ", " + straight[3].getFace() + ", " + straight[4].getFace() + " " + handDealt);
			hand.add(5);
			hand.add(straight[4].getValue());
			hand.add(highCard.getValue()); // add high card for hand in case there is a tie
		}
		else if (triple.getValue() > 0) {
			System.out.println("Three of a Kind: " + triple.getFace() + "'s" + " " + handDealt);
			hand.add(4);
			hand.add(triple.getValue());
			hand.add(highCard.getValue());
		}
		else if (twoPair[0] != null) {
			System.out.println("Two Pairs: " + twoPair[1].getFace() + "'s & " + twoPair[0].getFace() + "'s" + " " + handDealt);
			hand.add(3);
			hand.add(twoPair[1].getValue());
			hand.add(twoPair[0].getValue());
		}
		else if (pair.getValue() > 0) {
			System.out.println("Pair: " + pair.getFace() + "'s" + " " + handDealt);
			hand.add(2);
			hand.add(pair.getValue());
			hand.add(highCard.getValue());
		}
		else if (highCard.getValue() > 0) {
			System.out.println("High Card: " + highCard.getFace() + " " + handDealt);
			hand.add(1);
			hand.add(highCard.getValue());
			hand.add(highCard.getValue());
		}

		return hand;
	}
}
