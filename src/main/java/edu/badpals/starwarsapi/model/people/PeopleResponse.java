package edu.badpals.starwarsapi.model.people;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleResponse {

	@JsonProperty("name")
	private String name;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("mass")
	private String mass;

	@JsonProperty("height")
	private String height;

	@JsonProperty("hair_color")
	private String hairColor;

	@JsonProperty("eye_color")
	private String eyeColor;



	public String getGender(){
		return gender;
	}

	public String getMass(){
		return mass;
	}


	public String getHairColor(){
		return hairColor;
	}


	public String getEyeColor(){
		return eyeColor;
	}


	public String getName(){
		return name;
	}

	public String getHeight(){
		return height;
	}

	@Override
	public String toString() {
		return "PeopleResponse{" +
				", gender='" + gender + '\'' +
				", mass='" + mass + '\'' +
				", hairColor='" + hairColor + '\'' +
				", eyeColor='" + eyeColor + '\'' +
				", name='" + name + '\'' +
				", height='" + height + '\'' +
				'}';
	}
}