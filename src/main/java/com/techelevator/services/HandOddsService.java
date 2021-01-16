package com.techelevator.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.models.HandOdds;

@Component
public class HandOddsService {
	
	private String apiUrl = "https://sf-api-on-demand-poker-odds-v1.p.rapidapi.com/";
	private String key = "d33981d468msh80166873d0e6cc2p1fb3d3jsn73381b0af722";
	private String host = "sf-api-on-demand-poker-odds-v1.p.rapidapi.com";
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonNode jsonNode;
	
	public HandOdds getHoleValue(String hole) {
		String url = apiUrl + "pre-flop?hole=" + hole; 
		HandOdds odds = new HandOdds();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), String.class);
		
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			String expectedValue = jsonNode.path("data").path(0).path("hole_cards").path("indicative_expected_value").toString();	
			odds.setExpectedValue(expectedValue);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return odds;
	}

	public HandOdds getFlopWinProb(String board, String hole) {
		String url = apiUrl + "flop?hole=" + hole + "&board=" + board; 
		HandOdds odds = new HandOdds();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), String.class);
		
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			
			String winOdds = jsonNode.path("data").path("winning").path("average").path("odds").toString();
			String winProbability = jsonNode.path("data").path("winning").path("average").path("probability").toString();
			
			odds.setWinOdds(winOdds);
			odds.setWinProbability(winProbability);
			odds.setExpectedValue(null);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return odds;
	}
	
	public HandOdds getTurnWinProb(String board, String hole) {
		String url = apiUrl + "turn?hole=" + hole + "&board=" + board; 
		HandOdds odds = new HandOdds();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), String.class);
		
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			
			String winOdds = jsonNode.path("data").path("winning").path("average").path("odds").toString();
			String winProbability = jsonNode.path("data").path("winning").path("average").path("probability").toString();
			
			odds.setWinOdds(winOdds);
			odds.setWinProbability(winProbability);
			odds.setExpectedValue(null);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return odds;
	}
	
	public HandOdds getRiverWinProb(String board, String hole) {
		String url = apiUrl + "river?hole=" + hole + "&board=" + board;
		HandOdds odds = new HandOdds();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), String.class);
		
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			
			String winOdds = jsonNode.path("data").path("winning").path("odds").toString();
			String winProbability = jsonNode.path("data").path("winning").path("probability").toString();
			
			odds.setWinOdds(winOdds);
			odds.setWinProbability(winProbability);
			odds.setExpectedValue(null);

			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return odds;
	}
	
	private HttpEntity<String> getHttpEntity() {
	    HttpHeaders headers = new HttpHeaders();
		headers.set("x-rapidapi-key", key);
		headers.set("x-rapidapi-host", host);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    return entity;
	}


}
