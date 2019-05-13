package dev.hwiveloper.app.woomin.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_election")
@Getter
@Setter
public class Election {
	@EmbeddedId
	private ElectionPK key;
	
	private String sgName;
	private String sgVoteDate;
}
