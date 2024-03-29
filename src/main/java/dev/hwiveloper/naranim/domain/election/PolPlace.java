package dev.hwiveloper.naranim.domain.election;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="poll_place")
@Getter
@Setter
public class PolPlace {
	@EmbeddedId
	private PolPlacePK key;
	
	private String psName;
	private String evPsName;
	private String wiwName;
	private String emdName;
	private int evOrder;
	private String placeName;
	private String addr;
	private String floor;
}
