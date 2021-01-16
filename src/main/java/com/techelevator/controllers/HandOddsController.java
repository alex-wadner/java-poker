package com.techelevator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.models.HandOdds;
import com.techelevator.services.HandOddsService;

@RestController
public class HandOddsController {

	@Autowired
	HandOddsService odds;
	
	@RequestMapping(path = "pre-flop", method = RequestMethod.GET)
	public HandOdds getHoleValue(@RequestParam String hole) {
		return odds.getHoleValue(hole);
	}
	
	@RequestMapping(path = "flop", method = RequestMethod.GET)
	public HandOdds getFlopWinProb(@RequestParam String board, @RequestParam String hole) {
		return odds.getFlopWinProb(board, hole);
	}
	
	@RequestMapping(path = "turn", method = RequestMethod.GET)
	public HandOdds getTurnWinProb(@RequestParam String board, @RequestParam String hole) {
		return odds.getTurnWinProb(board, hole);
	}
	
	@RequestMapping(path = "river", method = RequestMethod.GET)
	public HandOdds getRiverWinProb(@RequestParam String board, @RequestParam String hole) {
		return odds.getRiverWinProb(board, hole);
	}
}
