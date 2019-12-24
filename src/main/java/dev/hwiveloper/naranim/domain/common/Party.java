package dev.hwiveloper.naranim.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="party")
@Getter
@Setter
public class Party {
	@EmbeddedId
	private PartyPK key;
	private String jdName;
}
