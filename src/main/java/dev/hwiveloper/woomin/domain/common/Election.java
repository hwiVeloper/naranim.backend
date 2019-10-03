package dev.hwiveloper.woomin.domain.common;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_election")
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
