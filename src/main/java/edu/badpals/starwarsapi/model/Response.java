package edu.badpals.starwarsapi.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("count")
	private int count;

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private Object previous;

	@JsonProperty("results")
	private List<Object> results;

	public int getCount() {
		return count;
	}

	public String getNext(){
		return next;
	}

	public Object getPrevious(){
		return previous;
	}

	public List<Object> getResults(){
		return results;
	}



}