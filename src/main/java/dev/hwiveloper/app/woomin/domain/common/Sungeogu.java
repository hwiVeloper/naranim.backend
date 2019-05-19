package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_sungeogu")
@Getter
@Setter
public class Sungeogu {
	@EmbeddedId
	private SungeoguPK key;
	
	private String sdName;
	private String wiwName;
	private int sggJungsu;
	private int sOrder;
}
