package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
	
	private String name;
	private int money = 1000;
	private int wager = 0;
	private int totalWager = 0;
	private List<Card> hand = new ArrayList<Card>();
	private List<Card> totalCards = new ArrayList<Card>();
	private List<Integer> handStrength = new ArrayList<Integer>();
	private boolean isAllIn = false;
	private boolean isFolded = false;
	private boolean isBigBlind = false;
	private boolean isSmallBlind = false;
	
	public boolean isAllIn() {
		return isAllIn;
	}
	public void setAllIn(boolean isAllIn) {
		this.isAllIn = isAllIn;
	}
	public boolean isFolded() {
		return isFolded;
	}
	public void setFolded(boolean isFolded) {
		this.isFolded = isFolded;
	}
	public boolean isBigBlind() {
		return isBigBlind;
	}
	public void setBigBlind(boolean isBigblind) {
		this.isBigBlind = isBigblind;
	}
	public boolean isSmallBlind() {
		return isSmallBlind;
	}
	public void setSmallBlind(boolean isSmallBlind) {
		this.isSmallBlind = isSmallBlind;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void winMoney(int amount) {
		money += amount;
	}
	public void loseMoney(int amount) {
		money -= amount;
	}
	public int getWager() {
		return wager;
	}
	public void setWager(int wager) {
		this.wager = wager;
	}
	public int getTotalWager() {
		return totalWager;
	}
	public void setTotalWager(int wager) {
		this.totalWager += wager;
	}
	public List<Card> getHand() {
		return hand;
	}
	public void setHand(Card card) {
		hand.add(card);
	}
	public List<Card> getTotalCards() {
		return totalCards;
	}
	public void setTotalCards(Card card) {
		totalCards.add(card);
	}
	public List<Integer> getHandStrength() {
		return handStrength;
	}
	public void setHandStrength(List<Integer> handStrength) {
		this.handStrength = handStrength;
	}
	
	
	public int beginBet(int bigBlind) {
		return 0;
	}
	
	public int respondBet(int wager) {
		return 0;
	}
	
	public int respondCall(String prompt, int smallBlind) {
		return 0;
	}
	
	public boolean bet(int amount) {
		return false;
	}
	
	public void resetHands() {
	}
}
