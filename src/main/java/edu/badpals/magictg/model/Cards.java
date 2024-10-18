package edu.badpals.magictg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cards {
	//Importante
	@JsonProperty("name")
	private String name;
	@JsonProperty("manaCost")
	private String manaCost;
	@JsonProperty("cmc")
	private Object cmc;
	@JsonProperty("colors")
	private List<String> colors;
	@JsonProperty("type")
	private String type;
	@JsonProperty("subtypes")
	private List<String> subtypes;
	@JsonProperty("rarity")
	private String rarity;
	@JsonProperty("setName")
	private String setName;
	@JsonProperty("text")
	private String text;
	@JsonProperty("power")
	private String power;
	@JsonProperty("toughness")
	private String toughness;
	@JsonProperty("imageUrl")
	private String imageUrl;

	//No importante
	@JsonProperty("colorIdentity")
	private List<String> colorIdentity;
	@JsonProperty("multiverseid")
	private String multiverseid;
	@JsonProperty("types")
	private List<String> types;
	@JsonProperty("set")
	private String set;
	@JsonProperty("artist")
	private String artist;
	@JsonProperty("layout")
	private String layout;
	@JsonProperty("number")
	private String number;

	public Cards() {
	}

	public List<String> getColorIdentity(){
		return colorIdentity;
	}

	public String getSetName(){
		return setName;
	}

	public String getMultiverseid(){
		return multiverseid;
	}

	public List<String> getTypes(){
		return types;
	}

	public String getSet(){
		return set;
	}

	public String getArtist(){
		return artist;
	}

	public String getType(){
		return type;
	}

	public List<String> getColors(){
		return colors;
	}

	public List<String> getSubtypes(){
		return subtypes;
	}

	public String getLayout(){
		return layout;
	}

	public String getNumber(){
		return number;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getName(){
		return name;
	}

	public Object getCmc(){
		return cmc;
	}

	public String getText(){
		return text;
	}

	public String getPower(){
		return power;
	}

	public String getManaCost(){
		return manaCost;
	}

	public String getToughness(){
		return toughness;
	}

	public String getRarity(){
		return rarity;
	}

	@Override
 	public String toString(){
		return 
			"CardsItem{" + 
			"colorIdentity = '" + colorIdentity + '\'' + 
			",setName = '" + setName + '\'' + 
			",multiverseid = '" + multiverseid + '\'' + 
			",types = '" + types + '\'' + 
			",set = '" + set + '\'' + 
			",artist = '" + artist + '\'' + 
			",type = '" + type + '\'' + 
			",colors = '" + colors + '\'' + 
			",subtypes = '" + subtypes + '\'' + 
			",layout = '" + layout + '\'' + 
			",number = '" + number + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",name = '" + name + '\'' + 
			",cmc = '" + cmc + '\'' + 
			",text = '" + text + '\'' + 
			",power = '" + power + '\'' + 
			",manaCost = '" + manaCost + '\'' + 
			",toughness = '" + toughness + '\'' + 
			",rarity = '" + rarity + '\'' + 
			"}";
		}
}