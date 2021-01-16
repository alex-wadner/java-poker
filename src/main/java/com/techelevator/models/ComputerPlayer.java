package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

import com.techelevator.services.TestService;

public class ComputerPlayer extends Player{
	
	//HandOddsService odds = new HandOddsService();
	TestService odds = new TestService();
	
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

	
	public int respondCall(String prompt, int call) {
		System.out.println("\n" + name + " is thinking...");
		HandOdds bettingOdds = getOdds(hand, totalCards);
		double holeValue = 0.0;
		double winProb = 0.0;
		int bet = 0;
	
		if (bettingOdds.getExpectedValue() == null) {
			winProb = Double.parseDouble(bettingOdds.getWinProbability());
			
			if (winProb > 0.5) {
				if (bet(call)) {
					bet = call;	
				} else {
					wager = money;
					money = 0;
					isAllIn = true;
					return wager;
				}
			} else {
				bet = -1;
			}
			
		} else {
			holeValue = Double.parseDouble(bettingOdds.getExpectedValue());
			
			if (holeValue > -0.13) {
				if (bet(call)) {
					bet = call;	
				} else {
					wager = money;
					money = 0;
					isAllIn = true;
					System.out.println(name + " is all in!");
					return wager;
				}
			} else {
				bet = -1;
			}
		}
		return bet;
		
	}

	public int beginBet(int bigBlind) {
		System.out.println("\n" + name + " is thinking...");
		int bet = calculateBet(bigBlind);
		return makeBet(bet);
	}
	
	public int respondBet(int wager) {
		System.out.println("\n" + name + " is thinking...");
		int bet = calculateResponse(wager);
		return makeBet(bet);
	}
	
	private int makeBet(int bet) {
		if (wager > bet) { // when computer wants to bet less than the wager, it folds
			return -1;
		}
		else if (bet(bet - this.wager)) { // when the computer wants to bet something more than the minimum bet but less than what it has.
			return bet;
		} else { // when the computer wants to bet more than it has, it just goes all in with what it's got. 
			wager = money;
			money = 0;
			setAllIn(true);
			setTotalWager(wager);
			return wager; // conflict in returning wager?? assign to new variable?
		}
	}
	
	public boolean bet(int amount) {
		if (amount < money) { // allows a bet of less than it has and deducts money from it's purse
			money -= amount;
			setTotalWager(amount);
			return true;
		} 
		else if (amount == money) { // allows a bet equaling what it has and deducts money from it's purse.
			money = 0;
			setTotalWager(amount);
			setAllIn(true);
			return true;
		}
		else { // rejects a bet more than it has. this is here so that if blinds are more than comp has, existing blinds code takes what it has and puts it all in.
			return false;
		}
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
	
	private int calculateBet(int minimumBet) {
		HandOdds bettingOdds = getOdds(hand, totalCards);
		double holeValue = 0.0;
		double winOdds = 0.0;
		double winProb = 0.0;
		int bet = 0;
		
		if (bettingOdds.getExpectedValue() != null) {
			holeValue = Double.parseDouble(bettingOdds.getExpectedValue());
			if (holeValue < 0) {
				bet = 0;
			}
			else if (holeValue > 0 && holeValue < 1) {
				bet = minimumBet;
			}
			else if (holeValue > 1) {
				bet = minimumBet + (minimumBet/2);
			}
			
		} else {
			winOdds = Double.parseDouble(bettingOdds.getWinOdds());
			winProb = Double.parseDouble(bettingOdds.getWinProbability());
			if (winProb < 0.05) {
				bet = -1;
			}
			else if (winProb < 0.5) {
				bet = 0;
			}
			else if (winProb < 0.7) {
				bet = minimumBet;
			}
			else if (winProb < 0.9) { // fix hole
				bet = minimumBet + (int)(winOdds * 3);
			}
			else if (winProb > 0.9 && winProb < 0.95) {
				bet = minimumBet + (int)(winOdds * 6);
			}
			else if (winProb > 0.95) {
				bet = money;
			}
		}
		return bet;
	}
	
	private int calculateResponse(int wager) {
		HandOdds bettingOdds = getOdds(hand, totalCards);
		double holeValue = 0.0;
		double winOdds = 0.0;
		double winProb = 0.0;
		int bet = 0;
		
		if (bettingOdds.getExpectedValue() != null) {
			holeValue = Double.parseDouble(bettingOdds.getExpectedValue());
			if (holeValue < -0.12) {
				bet = -1;
			}
			else if (holeValue >= -0.12 && holeValue < 1) {
				if (wager > (money/3)) {
					bet = -1;
				} else {
					bet = wager;
				}
			}
			else if (holeValue >= 1) {
				bet = wager + (wager/2);
			}
			else if (holeValue >= 2) {
				bet = money;
			}
			
		} else {
			winOdds = Double.parseDouble(bettingOdds.getWinOdds());
			winProb = Double.parseDouble(bettingOdds.getWinProbability());
			if (winProb < 0.05) {
				bet = -1;
			}
			else if (winProb < 0.80) {
				if (wager > (money/3)) {
					bet = -1;
				} else {
					bet = wager;
				}
			}
			else if (winProb >= 0.80 && winProb < 95) {
				bet = wager + (int)((winOdds * wager)/3);
			}
			else if (winProb >= 0.95) {
				bet = money;
			}
		}
		return bet;
	}
	
	private HandOdds getOdds(List<Card> hand, List<Card> totalCards) {
		HandOdds bettingOdds = new HandOdds();
		String hole = cardConvert(hand);
		if (totalCards.size() == 2) {
			bettingOdds = odds.getHoleValue(hole);
		} 
		else if (totalCards.size() == 5) {
			bettingOdds = odds.getFlopWinProb(cardConvert(totalCards).substring(6), hole);
		}
		else if (totalCards.size() == 6) {
			bettingOdds = odds.getTurnWinProb(cardConvert(totalCards).substring(6), hole);
		}
		else if (totalCards.size() == 7) {
			bettingOdds = odds.getRiverWinProb(cardConvert(totalCards).substring(6), hole);
		}
		
		return bettingOdds;
	}
	
	private String cardConvert(List<Card> hand) {
		String handStr = "";
		for (Card card : hand) {
			String face = card.getFace() == "10" ? "T" : card.getFace().substring(0, 1);
			handStr += face + card.getSuit().substring(0, 1).toLowerCase() + ",";
		}
		return handStr.substring(0, handStr.length() - 1);
	}
}
