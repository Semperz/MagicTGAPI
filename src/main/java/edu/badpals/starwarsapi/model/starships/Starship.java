package edu.badpals.starwarsapi.model.starships;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Starship {

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private Object previous;

	@JsonProperty("count")
	private int count;

	@JsonProperty("results")
	private List<Object> results;

	public String getNext(){
		return next;
	}

	public Object getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<Object> getResults(){
		return results;
	}
}