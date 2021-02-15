package com.techelevator.services;

import com.techelevator.models.HandOdds;

public class TestService {
	
	HandOdds testPreFlop = new HandOdds();
	HandOdds testPostFlop = new HandOdds();
	

	public HandOdds getHoleValue(String hole) {
		testPreFlop.setExpectedValue((Double.toString((Math.random() * 3) - 0.15)));
		return testPreFlop;
	}
	
	public HandOdds getFlopWinProb(String board, String hole) {
		testPostFlop.setWinOdds(Double.toString((Math.random() * 10)));
		testPostFlop.setWinProbability(Double.toString((Math.random())));
		return testPostFlop;
	}
	
	public HandOdds getTurnWinProb(String board, String hole) {
		testPostFlop.setWinOdds(Double.toString((Math.random() * 10)));
		testPostFlop.setWinProbability(Double.toString((Math.random())));
		return testPostFlop;
	}
	
	public HandOdds getRiverWinProb(String board, String hole) {
		testPostFlop.setWinOdds(Double.toString((Math.random() * 10)));
		testPostFlop.setWinProbability(Double.toString((Math.random())));
		return testPostFlop;
	}
}
