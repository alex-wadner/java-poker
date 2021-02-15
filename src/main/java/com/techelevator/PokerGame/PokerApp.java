package com.techelevator.PokerGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.techelevator.models.Card;
import com.techelevator.models.ComputerPlayer;
import com.techelevator.models.Deck;
import com.techelevator.models.Hand;
import com.techelevator.models.HumanPlayer;
import com.techelevator.models.Player;
import com.techelevator.view.InputService;

public class PokerApp {
	private Deck deck;
	private Hand hand;
	private HumanPlayer human;
	private List<Player> players;
	private List<Card> publicCards;
	private int wager = 0;
	private int pot;
	private int smallBlind = 25;
	private int bigBlind = 50;
	private static int turn = 1;
	private static int blindsCounter = 0;
	private InputService input = new InputService(System.in, System.out);
	
	
	public void setup() {
		players = new ArrayList<Player>();
		human = new HumanPlayer();
		players.add(human);
		String name = input.getUserInput("\n\n\nWhat is your name? >>> ");
		if (!name.isBlank()) human.setName(name);
		
		while (true) {
		int opponents = input.getUserInputInteger("\nHow many opponents would you like? (7 maximum) >>> ");
			if (opponents == 0 || opponents > 7) {
				System.out.println(">>> Try again <<<");			
			} else {
				for (int i = 0; i < opponents; i++) {
					List<String> names = new ArrayList<String>();
					names.add("Igor");
					names.add("Ludwig");
					names.add("Johannes");
					names.add("Franz");
					names.add("Antonin");
					names.add("Gustav");
					names.add("Felix");
					ComputerPlayer computer = new ComputerPlayer();
					players.add(computer);
					int index = (int)(Math.random() * names.size());
					computer.setName(names.get(index));
					names.remove(index); //not working
				}
				break;
			}
		}
		System.out.println("\nGood luck, " + human.getName() + "! Let's play.");
		play();
	}
	
	public void play() {
		hand = new Hand();
		deck = new Deck();
		deck.shuffle();
		publicCards = new ArrayList<>();
		blinds();
		deal();
		nextDeal(3, "Flop");
		nextDeal(1, "Turn");
		nextDeal(1, "River");
		determineWinner();
	}
	
	public void blinds() { //simply assigns big and small blinds in a rotating fashion, automatically takes the blinds, and if needed puts that player all in if their blind is >= their money.
		System.out.println("\n*********************************************************************");
		if (turn % 5 == 0) {
			smallBlind *= 2;
			bigBlind *= 2;
			System.out.println("\nThe blinds increased!");
		}
		if (blindsCounter == players.size()) {
			blindsCounter = 0;
		}
		
		players.get(blindsCounter).setBigBlind(true);
		if (blindsCounter + 1 == players.size()) {
			players.get(0).setSmallBlind(true);
		} else {
			players.get(blindsCounter + 1).setSmallBlind(true);
		}
		
		for (Player player : players) {
			if (player.isBigBlind()) {
				if (!player.bet(bigBlind)) {
					pot += player.getMoney();
					player.setMoney(0);
					player.setAllIn(true);
				} else {
					pot += bigBlind;
				}
			}
			if (player.isSmallBlind() ) {
				if (!player.bet(smallBlind)) {
					pot += player.getMoney();
					player.setMoney(0);
					player.setAllIn(true);
				} else {
					pot += smallBlind;
				}
			}
		}
		
//		if (!players.get(blindsCounter).bet(bigBlind)) {
//			players.get(blindsCounter).bet(players.get(blindsCounter).getMoney());
//			players.get(blindsCounter).setAllIn(true);
//		}
//		players.get(blindsCounter).setBigBlind(true);
//		pot += bigBlind; // wrong = should bet whatever they have if they're all in
//		if (blindsCounter == players.size() - 1) {
//			if (!players.get(0).bet(smallBlind)) {
//				players.get(0).bet(players.get(0).getMoney());
//				players.get(0).setAllIn(true);
//			}
//			players.get(0).setSmallBlind(true);
//		} else {
//			if (!players.get(blindsCounter + 1).bet(smallBlind)) {
//				players.get(blindsCounter + 1).bet(players.get(blindsCounter + 1).getMoney());
//				players.get(blindsCounter + 1).setAllIn(true);
//			}
//			players.get(blindsCounter + 1).setSmallBlind(true);
//		}
//		pot += smallBlind; // wrong = should bet whatever they have if they're all in
//		blindsCounter++;
	}
	
	public void deal() {
		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				Card card = deck.draw();
				player.setHand(card);
				player.setTotalCards(card);	
			}
		}
		
		System.out.println("\nYour hand: " + human.getHand());
		if (human.isSmallBlind()) System.out.println("\n>>> You are small blind.\n");
		if (human.isBigBlind()) System.out.println("\n>>> You are big blind.\n");
		
		for (Player player : players) {
			int call = 0;
			if (player.isSmallBlind() && !player.isAllIn()) {
				call = player.respondCall("The blind is $" + smallBlind + ". ", smallBlind);
			}
			if (!player.isSmallBlind() && !player.isBigBlind()) {
				call = player.respondCall("The blind is $" + bigBlind + ". ", bigBlind);
			}
			
			if (-1 == call) { 
				player.setFolded(true);
				System.out.println(player.getName() + " folded.");
			} else {
				pot += call;
				if (player.isAllIn()) {
					System.out.println(player.getName() + " is all in!");
				} else {
					System.out.println(player.getName() + " calls.");						
				}
			}
//			player.resetTotalWager();
		}
		allIn();
		checkForWinner();
		betControl();
	}
	
	public void nextDeal(int num, String handName) {
		wager = 0;
		getNext(num);
		System.out.println("\n************************************\n\nThe " + handName + ": " + publicCards);
		System.out.println("Your Hand: " + human.getHand() + "\n");
		hand.getHandStrength(human.getTotalCards());
		checkForWinner();
		betControl();
	}
	
	public void getNext(int num) {
		for (int i = 0; i < num; i++) {
			Card card = deck.draw();
			publicCards.add(card);	
			
			for (Player player : players) {
				player.setTotalCards(card);
			}
		}
	}
	
	public void allIn() { // checks for if all or all but one player is "all in", thus ending betting and plays out the rest of the missing public cards.
		List<Player> allInPlayers = getAllInPlayers();

		if (allInPlayers.size() >= getActivePlayers().size() - 1) {
			int cardsLeft = 5 - publicCards.size();
			getNext(cardsLeft);
			System.out.println("\n************************************\n");
			for (Player player : allInPlayers) {
				System.out.println(player.getName() + " is all in!");
			}
			System.out.println("\n************************************\n");
			determineWinner();
		} else {
			allInPlayers.clear();
		}
	}
	
	public void checkForWinner() { // checks for if all but one player has folded, and makes them winner by default.
		List<Player> activePlayers = getActivePlayers();
		if (activePlayers.size() == 1) {
			banker(activePlayers);
		}
	}
	
	public List<Player> setBettingOrder() { //determines who bets first according to blinds. Small blind bets first, big blind bets last. 
		List<Player> bettingOrder = new ArrayList<Player>();
		
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isSmallBlind()) {
				bettingOrder.add(players.get(i));
				if (i != players.size() - 1) {
					for (int j = i + 1; j < players.size(); j++) {
						bettingOrder.add(players.get(j));
					}
				}
			}
		}
		
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isSmallBlind()) {
				bettingOrder.add(players.get(i));
			} else {
				break;
			}
		}
		
		return bettingOrder;
	}
	
	
	public void betControl() { // facilitates and organizes betting.
		System.out.print("\nPOT $" + pot + "   BLINDS $" + smallBlind + "/$" + bigBlind + "   "); 
		getStatus();
		List<Player> orderedPlayers = setBettingOrder();
		for (Player player : orderedPlayers) { // goes through the players once for their initial bet.
			if (!player.isFolded() && !player.isAllIn()) {
				if (wager == 0) {
					int response = player.beginBet(bigBlind);
					handleBet(response, player);
				} else {
					int response = player.respondBet(wager);
					handleBet(response, player);
				}
			}
		}
		checkForWinner(); // make sure everyone didn't fold before moving on...
		while (true) {
			System.out.print("\n");
			getPotStatus();
			System.out.print("\n");
			for (Player player : orderedPlayers) { //checks to see if there are any bets or raises.
				if (!player.isFolded() && !player.isAllIn()) {
					if (wager != player.getWager()) {
						if (getAllInPlayers().size() == getActivePlayers().size() - 1) { // if everyone but one is all-in, force them to either call or fold - do not allow a raise option.
							int response = player.respondCall("\n", (wager - player.getTotalWager())); //player.getMoney() is the wrong value. what if you have more money than everyone? why not (wager - player.getTotalWager()) instead?
							handleBet(response, player);
						} else {
							int response = player.respondBet(wager);
							handleBet(response, player);							
						}
					}
				}
			}
			int[] settledUp = getBettingPlayers();
			if (settledUp[0] == settledUp[1]) break; //if all players are paid up, the loop is broken and the hand continues. if any player made a bet that hasn't been called yet, the loop continues.
		}
		
		for (Player player : players) { // only clear wagers for players who aren't all in
			if (!player.isAllIn()) {
				player.setWager(0);				
			}
		}
		checkForWinner();
		allIn();
	}
	
	public void handleBet(int response, Player player) { // adds each bet to the pot and takes care of console messaging.
		if (response == -1) {
			System.out.println(player.getName() + " folded.");
			player.setFolded(true);
		} else if (response == -2) {
			System.out.println("\nThanks for playing!");
			System.exit(1);
		} else if (player.isAllIn()) {
			System.out.println(player.getName() + " is all in!");
			wager = player.getTotalWager();
			pot += response;
			player.setWager(wager);
		} else {
			if (response > wager) {
				System.out.println(player.getName() + " raises $" +  (response - wager) + "! The total wager is now $" + response + ".");
			}
			else if (response == 0) {
				System.out.println(player.getName() + " checks.");
			}
			else if (response == wager) {
				System.out.println(player.getName() + " calls.");
			}
			wager = response;
			pot += wager - player.getWager(); // is this bit is flawed when there are multiple raises per hand? no, it's fine. but "wager - player.getWager()" has proved to be an issue. if there are future betting problems, don't forget this.
			player.setWager(wager);
		}
	}
	
	
	public void determineWinner() {
		System.out.println("\nRESULTS\n");
		List<Player> activePlayers = getActivePlayers(); //instead of referencing players and having to filter out players who are folded, do the same thing by iterating through only active players (players who aren't folded)
		for (Player player : activePlayers) {
			System.out.print(player.getName() + ": ");
			player.setHandStrength(hand.getHandStrength(player.getTotalCards()));
		}
		
		Player winner = activePlayers.get(0);
		List<Player> winners = new ArrayList<Player>();
		for (int i = 1; i < activePlayers.size(); i++) { // start the loop so it starts comparing with the second player (index 1)
				if (activePlayers.get(i).getHandStrength().get(0) > winner.getHandStrength().get(0)) {
					winner = activePlayers.get(i);
					winners.clear();
				}
				else if (activePlayers.get(i).getHandStrength().get(0) == winner.getHandStrength().get(0)) {
					if (activePlayers.get(i).getHandStrength().get(1) > winner.getHandStrength().get(1)) {
						winner = activePlayers.get(i);
						winners.clear();
					} 
					else if (activePlayers.get(i).getHandStrength().get(1) == winner.getHandStrength().get(1)) {
						if (activePlayers.get(i).getHandStrength().get(2) > winner.getHandStrength().get(2)) {
							winner = activePlayers.get(i);
							winners.clear();
						}
						else if (activePlayers.get(i).getHandStrength().get(2) == winner.getHandStrength().get(2)) {
							winners.add(activePlayers.get(i));
						} 
					}
				}
			}
		
		winners.add(winner);
		banker(winners);
	}
	
	public void banker(List<Player> winners) {
		boolean evenSplit = false;
		for (int i = 0; i < winners.size(); i++) { // checks to see if all players wagered the same amount
			if (winners.get(0).getTotalWager() != winners.get(i).getTotalWager()) {
				evenSplit = false; // delete?
				break;
			} else {
				evenSplit = true;
			}
		}
		// Check to see if the winner put in as much as everyone else? if not, maybe just give them what they put in?
		if (winners.size() == 1) { // if there's only one winner, they get the entire pot. 
			winners.get(0).winMoney(pot);
			System.out.println("\n" + winners.get(0).getName() + " wins the $" + pot + " pot!\n");
		} 
		else if (evenSplit) { // if all winners wagered the same amount, pot is split evenly.
			System.out.print("\nDRAW! $" + pot + " split between " + winners.get(0).getName());
			winners.get(0).winMoney(pot / winners.size());
			for (int i = 1; i < winners.size(); i++) {
				System.out.print(" & " + winners.get(i).getName());
				winners.get(i).winMoney(pot / winners.size());
			}
		}
		else { // if players wagered different amounts but ended up all in, they only receive back what they put in.
			System.out.print("\nDRAW! $" + pot + " split between " + winners.get(0).getName()); // shouldn't it be that every player wins what they contributed to the pot???
			winners.get(0).winMoney(winners.get(0).getTotalWager());
			for (int i = 1; i < winners.size(); i++) {
				System.out.print(" & " + winners.get(i).getName());
				winners.get(i).winMoney(winners.get(i).getTotalWager());
			}
		}
		System.out.println("\n");
		finish();
	}
	
	public void finish() { // clean up. checks to see if you won or lost. removes players with no money, resets hands, values, gives option to play again, etc.
		List<Player> returningPlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (human.getMoney() == 0) {
				System.out.println("You lost. Thanks for playing!");
				System.exit(1);
			}
			else if (player.getMoney() == 0) {
				System.out.println(player.getName() + " has bowed out.\n");
			} else {
				returningPlayers.add(player);
			}
		}
		
		if (returningPlayers.size() == 1) {
			System.out.println("YOU BEAT EVERYONE! Nice card playing. Come back and play again soon!");
			System.exit(1);
		}		
		players = returningPlayers;

		resetPlayers();
		getStatus();
		String response = input.getUserInput("\n\nPress any key for the next hand... \n('X' to Quit) ");
		if (response.toLowerCase().equals("x")) {
			System.out.println("\nThanks for playing!");
			System.out.println("Total winnings: $" + (human.getMoney() - 1000));
			System.exit(1);
		}
		
		play();
	}
	
	public void resetPlayers() {
		pot = 0;
		wager = 0;
		turn++;
		blindsCounter++;
		
		for (Player player : players) {
			player.resetHands();
		}
	}

	public void getStatus() {
		for (Player player : players) {
			if (player.isAllIn() && !player.isFolded()) {
				System.out.print(player.getName().toUpperCase() + " (all-in)   ");
			}
			if (player.isFolded() && !player.isAllIn()) {
				System.out.print(player.getName().toUpperCase() + " (folded)   ");
			} 
			else if (!player.isAllIn() && !player.isFolded()) {
				System.out.print(player.getName().toUpperCase() + " $" + player.getMoney() + "   ");
			}
		}
	}
	
	public void getPotStatus() {
		System.out.print("INDIVIDUAL WAGERS:   ");
		for (Player player : players) {
			if (player.isAllIn() && !player.isFolded()) {
				System.out.print(player.getName().toUpperCase() + " $" + player.getTotalWager() +" (all-in)   ");
			}
			if (player.isFolded() && !player.isAllIn()) {
				System.out.print(player.getName().toUpperCase() + " (folded)   ");
			} 
			else if (!player.isAllIn() && !player.isFolded()) {
				System.out.print(player.getName().toUpperCase() + " $" + player.getTotalWager() + "   ");
			}
		}
	}
	
	public List<Player> getActivePlayers() {
		List<Player> activePlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (!player.isFolded()) {
				activePlayers.add(player);
			}
		}
		return activePlayers;
	}
	
	public List<Player> getAllInPlayers() {
		List<Player> allInPlayers = new ArrayList<Player>();
		
		for (Player player : players) {
			if (player.isAllIn() && !player.isFolded()) {
				allInPlayers.add(player);
			}
		} 
		return allInPlayers;
	}
	
	public int[] getBettingPlayers() {
		int bettingPlayers = 0;
		int paidUp = 0;
		for (Player player : players) { // checks if all players are paid up. 
			if (!player.isFolded() && !player.isAllIn()) {
				bettingPlayers++;
				if (wager == player.getWager()) {
					paidUp++;
				}
			}
		}
		int[] players = {bettingPlayers, paidUp};
		return players;
	}
	
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
                                                                                         
		String userInput = input.getUserInput("\n\n\n                                                                       (Press enter to deal)\n");
		if (userInput == "" || userInput != "") {
			setup();
		}
	}
	
	public static void main(String[] args) {
		PokerApp newGame = new PokerApp();
		newGame.start();
	}

}
