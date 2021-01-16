package com.techelevator.PokerGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.techelevator.models.Card;
import com.techelevator.models.Deck;

public class PokerAppOld {
	Scanner input = new Scanner(System.in);
	int pot = 0;
	int wager = 0;
	int playerMoney = 1000;
	int computerMoney = 1000;
	int smallBlind = 250;
	int bigBlind = 500;
	private static int turn = 1;
	List<Card> publicCards;
	List<Card> player1total;
	List<Card> player1hand;
	List<Card> player2total;
	List<Card> player2hand;
	Deck deck;
	
	public void start() {
		System.out.println("");
		System.out.println("   $$$$$\\  $$$$$$\\  $$\\    $$\\  $$$$$$\\        $$$$$$$$\\ $$$$$$$$\\ $$\\   $$\\  $$$$$$\\   $$$$$$\\        $$\\   $$\\  $$$$$$\\  $$\\       $$$$$$$\\  $$\\ $$$$$$$$\\ $$\\      $$\\ ");
		System.out.println("   \\__$$ |$$  __$$\\ $$ |   $$ |$$  __$$\\       \\__$$  __|$$  _____|$$ |  $$ |$$  __$$\\ $$  __$$\\       $$ |  $$ |$$  __$$\\ $$ |      $$  __$$\\ $  |$$  _____|$$$\\    $$$ |");
		System.out.println("      $$ |$$ /  $$ |$$ |   $$ |$$ /  $$ |         $$ |   $$ |      \\$$\\ $$  |$$ /  $$ |$$ /  \\__|      $$ |  $$ |$$ /  $$ |$$ |      $$ |  $$ |\\_/ $$ |      $$$$\\  $$$$ |");
		System.out.println("      $$ |$$$$$$$$ |\\$$\\  $$  |$$$$$$$$ |         $$ |   $$$$$\\     \\$$$$  / $$$$$$$$ |\\$$$$$$\\        $$$$$$$$ |$$ |  $$ |$$ |      $$ |  $$ |    $$$$$\\    $$\\$$\\$$ $$ |");
		System.out.println("$$\\   $$ |$$  __$$ | \\$$\\$$  / $$  __$$ |         $$ |   $$  __|    $$  $$<  $$  __$$ | \\____$$\\       $$  __$$ |$$ |  $$ |$$ |      $$ |  $$ |    $$  __|   $$ \\$$$  $$ |");
		System.out.println("$$ |  $$ |$$ |  $$ |  \\$$$  /  $$ |  $$ |         $$ |   $$ |      $$  /\\$$\\ $$ |  $$ |$$\\   $$ |      $$ |  $$ |$$ |  $$ |$$ |      $$ |  $$ |    $$ |      $$ |\\$  /$$ |");
		System.out.println("\\$$$$$$  |$$ |  $$ |   \\$  /   $$ |  $$ |         $$ |   $$$$$$$$\\ $$ /  $$ |$$ |  $$ |\\$$$$$$  |      $$ |  $$ | $$$$$$  |$$$$$$$$\\ $$$$$$$  |    $$$$$$$$\\ $$ | \\_/ $$ |");
		System.out.println(" \\______/ \\__|  \\__|    \\_/    \\__|  \\__|         \\__|   \\________|\\__|  \\__|\\__|  \\__| \\______/       \\__|  \\__| \\______/ \\________|\\_______/     \\________|\\__|     \\__|");
                                                                                         
		System.out.print("\n\n\n                                                                        Press enter to deal\n");
		String userInput = input.nextLine();
		if (userInput == "" || userInput != "") {
			play();
		}
	}
	
	public void play() {
		deck = new Deck();
		deck.shuffle();
		publicCards = new ArrayList<>();
		player1hand = new ArrayList<Card>();
		player2hand = new ArrayList<Card>();
		player1total = new ArrayList<Card>();
		player2total = new ArrayList<Card>();
		deal();
		nextDeal(3, "Flop");
		nextDeal(1, "Turn");
		nextDeal(1, "River");
		determineWinner(player1total, player2total);
		finish();
	}
	
//	public void blinds() {
//		if (turn % 5 == 0) {
//			smallBlind *= 2;
//			bigBlind *= 2;
//			System.out.println("\nThe blinds increased!");
//		}
////		if (playerMoney <= bigBlind) {
////			wager = playerMoney;
////			wager -= computerMoney;
////			playerMoney = 0;
////			allIn("player", player1hand);
////		}
////		if (computerMoney <= bigBlind) {
////			pot = computerMoney;
////			computerMoney = 0;
////			wager -= playerMoney;
////			allIn("computer", player2hand);
////		}
//		if (turn % 2 == 0) {
//			playerMoney -= smallBlind;
//			computerMoney -= bigBlind;
//			
//			System.out.print("You're Small Blind.\nWould you like play the hand? ($" + smallBlind + ")\nCall (Any Key) or (F)old >>> \n");
//			String response = input.nextLine();
//			if (response.toLowerCase().equals("f")) {
//				pot = smallBlind + bigBlind;
//				computerMoney += pot;
//				System.out.println("You folded.");
//				finish();
//				play();
//			}
//			
//			pot += smallBlind;
//			playerMoney -=smallBlind;
//		} else {
//			computerMoney -= smallBlind;
//			playerMoney -= bigBlind;
//			pot += smallBlind; // enter computer logic here to call small blind
//			computerMoney -=smallBlind;
//			System.out.println("You're Big Blind. $" + bigBlind + "/$" + smallBlind + "\n");
//		}
//		
//		pot = pot + smallBlind + bigBlind;
//	}
	
	public void deal() {
		player1hand.add(deck.draw());
		player1hand.add(deck.draw());
		player2hand.add(deck.draw());
		player2hand.add(deck.draw());
		player1total.addAll(player1hand);
		player2total.addAll(player2hand);
		System.out.println("\n************************************\n\nYour hand: " + player1hand + "\n");
//		blinds();
		determineBettingOrder();
	}
	
	public void nextDeal(int num, String handName) {
		wager = 0;
		draw(num);
		System.out.println("\n************************************\n\nThe " + handName + ": " + publicCards);
		System.out.println("Your Hand: " + player1hand + "\n");
		returnHighHand(player1total);
		determineBettingOrder();
	}

	public void draw(int num) {
		for (int i = 0; i < num; i++) {
			Card draw = deck.draw();
			publicCards.add(draw);	
			player1total.add(draw);
			player2total.add(draw);
		}
	}
	
	public void determineBettingOrder() {
		System.out.println("POT $" + pot + ", BLINDS $" + smallBlind + "/$" + bigBlind + ", YOU $" + playerMoney + ", OPPONENT $" + computerMoney);
		if (turn % 2 == 1) {
			computerPlaceBet();
		} else {
			menu();
		}
	}
	
	public void menu() {
		System.out.print("CHECK (Enter), (B)ET, (F)OLD, E(X)IT >>> ");
		String choice = input.nextLine();
		if (choice.toLowerCase().equals("b")) {
			placeBet();
		}
		if (choice.toLowerCase().equals("f")) {
			System.out.println("\nYou folded.");
			play();
		}
		if (choice.toLowerCase().equals("x")) {
			System.out.println("\nThanks for playing!");
			System.exit(1);
		}
	}
	
//	old, better? version
//	public void placeBet() {
//		while (true) {
//			System.out.print("What's your wager? >>> $");
//			String response = input.nextLine();
//			wager = Integer.parseInt(response);		
//				if (wager <= playerMoney) {
//					playerMoney -= wager;
//					pot += wager;
//					computerRespondBet();
//					break;
//				} else {
//					System.out.println("You don't have enough to make that wager, try again\n");
//				}
//		}
//	}
	
	public void placeBet() {
		while (true) {
			System.out.print("What's your wager? >>> $");
			String response = input.nextLine();
			wager = Integer.parseInt(response);
				if (wager == playerMoney) {
					playerMoney -= wager;
					pot += wager;
					computerRespondBet();
					allIn("human", player1hand);
					break;
				}
				else if (wager < playerMoney) {
					playerMoney -= wager;
					pot += wager;
					computerRespondBet();
					break;
				} else {
					System.out.println("You don't have enough to make that wager, try again\n");
				}
		}
	}
	
	public void respondBet() {
		System.out.println("The computer has wagered $" + wager);
		System.out.println("(C)all, (R)aise, (F)old, E(X)IT >>> ");
		String choice = input.nextLine();
		if (choice.toLowerCase().equals("c")) {
			// automatically take wager amount from your purse
		}
		if (choice.toLowerCase().equals("r")) {
			placeBet();
		}
		if (choice.toLowerCase().equals("f")) {
			computerMoney += wager;// take wager and give it to computer purse
			finish();
		}
		if (choice.toLowerCase().equals("x")) {
			System.out.println("\nThanks for playing!");
			System.exit(1);
		} else {
			System.out.println("Invalid response, try again!");
		}
	}
	
	public void computerPlaceBet() {
		/*
		 * if (bet) {
		 * respondBet()
		 * }
		 * if (check) {
		 * menu()
		 * }
		 * if (fold) {
		 * sout("Computer folded. You win!")
		 * finish()
		 * }
		 */
		computerMoney -= wager;
		pot += wager;
		menu();
	}

	public void computerRespondBet() {
		/*
		 * if (call) {
		 * continue game
		 * }
		 * if (raise) {
		 * respondBet();
		 * }
		 * if (fold) {
		 * sout("Computer folded. You win!")
		 * finish()
		 * }
		 */
		if (wager >= computerMoney) {
			computerMoney -= wager;
			pot += wager;
			allIn("computer", player2hand);
		}
		computerMoney -= wager;
		pot += wager;
	}
	
	public void determineWinner(List<Card> playerOneHand, List<Card> playerTwoHand) {
		System.out.println("\n************************************\n");
		System.out.print("YOU: ");
		List<Integer> playerOne = returnHighHand(playerOneHand);
		System.out.print("OPPONENT: ");
		List<Integer> playerTwo = returnHighHand(playerTwoHand);
	
		if (playerOne.get(0) > playerTwo.get(0)) {
			banker("win");
		}
		else if (playerOne.get(0) == playerTwo.get(0)) {
			if (playerOne.get(1) > playerTwo.get(1)) {
				banker("win");
			} 
			else if (playerOne.get(1) == playerTwo.get(1)) {
				if (playerOne.get(2) > playerTwo.get(2)) {
					banker("win");
				}
				else if (playerOne.get(2) == playerTwo.get(2)) {
					banker("draw");
				}
				else {
					banker("lose");
				}
			}
			else {
				banker("lose");
			}
		} else {
			banker("lose");
		}
	}
	
	public void finish() {
		if (playerMoney <= 0) {
			System.out.println("\nThe computer beat you.\nThanks for playing!");
			System.exit(1);
		}
		if (computerMoney <= 0) {
			System.out.println("You beat the computer!\nThanks for playing!");
			System.exit(1);
		}
		
		pot = 0;
		wager = 0;
		turn++;
		System.out.println("YOU $" + playerMoney + ", OPPONENT $" + computerMoney);
		System.out.print("\nPress any key for the next hand... \n('X' to Quit) ");
		String response = input.nextLine();
		
		if (response.toLowerCase().equals("x")) {
			System.out.println("\nThanks for playing!");
			System.out.println("Total winnings: $" + (playerMoney - 1000));
			System.exit(1);
		}
		play();
	}
	
	public void banker(String outcome) {
		int difference = pot / 2;
		if (outcome.equals("win")) {
			playerMoney += pot;
			System.out.println(">> YOU WON $" + difference + "\n");
		} 
		else if (outcome.equals("draw")) {
			playerMoney += (pot / 2);
			computerMoney += (pot / 2);
			System.out.println("DRAW!");
		}
		else if (outcome.equals("lose")){
			computerMoney += pot;
			System.out.println(">> YOU LOST $" + difference + "\n");
		}
	}
	
	public void allIn(String player, List<Card> hand) {
		// accepts a parameter saying how many more cards need to be dealt and automatically deals the rest of the hand.
		// adds however much money a player has left to the pot, not allowing that players money go negative.
		int cardsLeft = 5 - publicCards.size();
		draw(cardsLeft);
		System.out.println("\n************************************\n\nThe " + player + " is all in: " + hand + " & " + publicCards + "\n");
		determineWinner(player1total, player2total);
		finish();
	}
	
	
	
	public List<Integer> returnHighHand(List<Card> handDealt) { //analyzes a hand and tells you what the best hand is
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
//							fourInARow++;
							i+=2;
						}
					}
					else {
//						threeInARow++;
						i++;
					}
				}
				else {
//					twoInARow++;
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
			System.out.println("Royal Flush: " + royalFlush[0].getFace() + ", " + royalFlush[1].getFace() + ", " + royalFlush[2].getFace() + ", " + royalFlush[3].getFace() + ", " + royalFlush[4].getFace() + " " + handDealt + "\n");
			hand.add(10);
			hand.add(14);
			hand.add(highCard.getValue());
		}
		else if (straightFlush[0] != null) {
			System.out.println("Straight Flush: " + straightFlush[0].getFace() + ", " + straightFlush[1].getFace() + ", " + straightFlush[2].getFace() + ", " + straightFlush[3].getFace() + ", " + straightFlush[4].getFace() + " " + handDealt + "\n");
			hand.add(9);
			hand.add(straightFlush[4].getValue());
			hand.add(highCard.getValue());
		}
		else if (quad.getValue() > 0) {
			System.out.println("Four of a Kind: " + quad.getFace() + "'s" + " " + handDealt + "\n");
			hand.add(8);
			hand.add(quad.getValue());
			hand.add(highCard.getValue());
		}
		else if (fullHouse[0] != null) {
			System.out.println("Full House: " + fullHouse[0].getFace() + "'s full of " + fullHouse[1].getFace() + "'s" + " " + handDealt + "\n");
			hand.add(7);
			hand.add(fullHouse[0].getValue());
			hand.add(fullHouse[1].getValue());
		}
		else if (flush.getValue() > 0) {
			System.out.println("Flush: " + flush.getFace() + " high, " + flushSuit + " " + handDealt + "\n");
			hand.add(6);
			hand.add(flush.getValue());
			hand.add(highCard.getValue());
		}
		else if (straight[0] != null) {
			System.out.println("Straight: " + straight[0].getFace() + ", " + straight[1].getFace() + ", " + straight[2].getFace() + ", " + straight[3].getFace() + ", " + straight[4].getFace() + " " + handDealt + "\n");
			hand.add(5);
			hand.add(straight[4].getValue());
			hand.add(highCard.getValue()); // add high card for hand in case there is a tie
		}
		else if (triple.getValue() > 0) {
			System.out.println("Three of a Kind: " + triple.getFace() + "'s" + " " + handDealt + "\n");
			hand.add(4);
			hand.add(triple.getValue());
			hand.add(highCard.getValue());
		}
		else if (twoPair[0] != null) {
			System.out.println("Two Pairs: " + twoPair[1].getFace() + "'s & " + twoPair[0].getFace() + "'s" + " " + handDealt + "\n");
			hand.add(3);
			hand.add(twoPair[1].getValue());
			hand.add(twoPair[0].getValue());
		}
		else if (pair.getValue() > 0) {
			System.out.println("Pair: " + pair.getFace() + "'s" + " " + handDealt + "\n");
			hand.add(2);
			hand.add(pair.getValue());
			hand.add(highCard.getValue());
		}
		else if (highCard.getValue() > 0) {
			System.out.println("High Card: " + highCard.getFace() + " " + handDealt + "\n");
			hand.add(1);
			hand.add(highCard.getValue());
			hand.add(highCard.getValue());
		}

		return hand;
	}
	
	
	public static void main(String[] args) {
		PokerAppOld newGame = new PokerAppOld();
		newGame.start();
	}

}
