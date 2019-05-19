package dev.hwiveloper.app.woomin.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_gusigun")
@Getter
@Setter
public class Gusigun {
	@EmbeddedId
	private GusigunPK key;
	
	private String wiwName;
	private String sdName;
}
