package dev.hwiveloper.app.woomin.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_election")
@Getter
@Setter
public class Election {
	@Id
	private String sgId;
	private String sgName;
	private String sgTypeCode;
	private String sgVoteDate;
}
