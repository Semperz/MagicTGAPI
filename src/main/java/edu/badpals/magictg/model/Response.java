package edu.badpals.magictg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Response{
	@JsonProperty("cards")
	private List<Cards> cards;

	public Response() {
	}

	public List<Cards> getCards(){
		return cards;
	}


	@Override
 	public String toString(){
		return 
			"Response{" + 
			"cards = '" + cards + '\'' + 
			"}";
		}
}