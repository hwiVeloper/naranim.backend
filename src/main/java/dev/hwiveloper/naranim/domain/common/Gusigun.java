package dev.hwiveloper.naranim.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="gusigun")
@Getter
@Setter
public class Gusigun {
	@EmbeddedId
	private GusigunPK key;
	
	private String wiwName;
	private String sdName;
}
