package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.view.InputService;

public class HumanPlayer extends Player {
	private InputService input = new InputService(System.in, System.out);

	private String name = "Wolfgang";
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
	public void setMoney(int money) {
		this.money = money;
	}
	public void winMoney(int amount) {
		money += amount;
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

	
	public int respondCall(String prompt, int call) {
		String choice = input.getUserInput("\nCall $" + call + " (Enter) or (F)old >>> ");
		System.out.println("");
		if (choice.toLowerCase().equals("f")) {
			return -1;
		} else {
			if (!bet(call)) { // if you don't have enough, you go all in.
				call = money;
				bet(call);
				money = 0;
				isAllIn = true;
			}
			wager = 0; // any bad effect of setting wager to 0 here? I don't think so. When this is called, it either doesn't matter or the wagers are about to be set back to zero anyways. 
								//I put this here in the event you match an all-in but don't go all in yourself. without this, it subtracts your all in bet with your wager, which can delete money.
			return call;
		}
	}
	
	public int beginBet(int bigBlind) {		
		String choice = input.getUserInput("\nCHECK (Enter), (B)ET, (F)OLD, E(X)IT >>> ");
		System.out.println("");
		if (choice.toLowerCase().equals("b")) {
			return makeBet(bigBlind);
		}
		else if (choice.toLowerCase().equals("f")) {
			return -1;
		}
		else if (choice.toLowerCase().equals("x")) {
			System.out.println("\nThanks for playing!");
			return -2;
		} 
			return 0;
	}
	
	public int respondBet(int wager) { //make a separate section for if the wager is more than you have that only allows for allin or fold?
		System.out.println("\n>>> You've been raised!");

		if (wager > money) {
			String choice = input.getUserInput("\nAll in $" + money + " (Enter), (F)old >>> ");
			System.out.println("");

			if (choice.toLowerCase().equals("f")) {
				return -1;
			} else {
				wager = money;
				bet(money);
				isAllIn = true;
				return wager;
			}
		}
		
		while (true) {
			String choice = input.getUserInput("\nCall $" + wager + " (Enter), (R)aise, (F)old, E(X)IT >>> "); //having the amount in there doesn't work if the wager is more than you have.
			System.out.println("");

			if (choice.toLowerCase().equals("r")) { // make it so you can't raise a lower amount than what the current wager is
				return makeBet(wager);
			}
			else if (choice.toLowerCase().equals("f")) {
				return -1;
			}
			else if (choice.toLowerCase().equals("x")) {
				return -2;
			} 
			else {
				if (!bet(wager - this.wager)) {
					money = wager;
					bet(wager);
					money = 0;
					isAllIn = true;
				}
				return wager;
			}
		}
	}
	
	
	private int makeBet(int min) {
		while (true) {
			int wager = input.getUserInputInteger("What's your wager? >>> $");
			if (wager >= min) {
				if (bet(wager - this.wager)) {
					return wager;
				}
			} else {
				System.out.println("You can't make that wager, try again.\n");
			}
		}
	}
	
	public boolean bet(int amount) {
		if (amount < 0) {
			return false;
		}
		else if (amount < money) {
			money -= amount;
			setTotalWager(amount);
			return true;
		} 
		else if (amount == money) {
			money = 0;
			setTotalWager(amount);
			isAllIn = true;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void resetTotalWager() {
		totalWager = 0;
	}

	public void resetHands() {
		hand.clear();
		totalCards.clear();
		isAllIn = false;
		isFolded = false;
		isBigBlind = false;
		isSmallBlind = false;
		wager = 0;
		totalWager = 0;
	}
}
