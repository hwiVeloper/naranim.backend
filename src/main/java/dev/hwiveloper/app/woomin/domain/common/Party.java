package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_party")
@Getter
@Setter
public class Party {
	@EmbeddedId
	private PartyPK key;
	private String jdName;
}
