package com.techelevator.models;

public class Card implements Comparable<Card>{
	// data members, properties, instance variables
	private Integer id;
	private Integer value;
	private String face;
	private String suit;
	
	public Card() {
		this.setId(0);
		this.value = 0;
		this.face = "";
		this.suit = "";
	}
	
	public Card(Integer id, Integer value, String face, String suit) {
		this.setId(id);
		this.value = value;
		this.face = face;
		this.suit = suit;
	}
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public String getFace() {
		return face;
	}
	
	public void setFace(String face) {
		this.face = face;
	}
	
	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}


	public String toString() { // return a string representation of a card
		return this.face + " of " + this.suit;
	}

	@Override
	public int compareTo(Card c) {
		return this.getValue().compareTo(((Card) c).getValue());	
	}

	

}
