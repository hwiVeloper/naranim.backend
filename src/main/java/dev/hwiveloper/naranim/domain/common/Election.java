package dev.hwiveloper.naranim.domain.common;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="election")
@Getter
@Setter
public class Election {
	@EmbeddedId
	private ElectionPK key;
	
	@Column(name="sg_name")
	private String sgName;
	
	@Column(name="sg_vote_date")
	private String sgVoteDate;
}
