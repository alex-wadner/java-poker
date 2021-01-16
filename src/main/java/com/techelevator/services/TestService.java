package com.techelevator.services;

import com.techelevator.models.HandOdds;

public class TestService {
	
	HandOdds testPreFlop = new HandOdds();
	HandOdds testPostFlop = new HandOdds();
	

	public HandOdds getHoleValue(String hole) {
		testPreFlop.setExpectedValue("0.51");
		return testPreFlop;
	}
	
	public HandOdds getFlopWinProb(String board, String hole) {
		testPostFlop.setWinOdds("3.79");
		testPostFlop.setWinProbability("0.536");
		return testPostFlop;
	}
	
	public HandOdds getTurnWinProb(String board, String hole) {
		testPostFlop.setWinOdds("13.79");
		testPostFlop.setWinProbability("0.536");
		return testPostFlop;
	}
	
	public HandOdds getRiverWinProb(String board, String hole) {
		testPostFlop.setWinOdds("13.79");
		testPostFlop.setWinProbability("0.536");
		return testPostFlop;
	}
}
