package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_poll_place")
@Getter
@Setter
public class PolPlace {
	@EmbeddedId
	private PolPlacePK key;
	
	private String evPsName;
	private String wiwName;
	private String emdName;
	private int evOrder;
	private String placeName;
	private String addr;
	private String floor;
}
