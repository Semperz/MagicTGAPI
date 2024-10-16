package edu.badpals.starwarsapi.model.people;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class People {

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