package dev.hwiveloper.naranim.domain.election;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_vote_result")
@Getter
@Setter
public class VoteResult {
	@EmbeddedId
	private VoteResultPK key;
	
	@Digits(integer=10, fraction=0)
	private BigDecimal sunsu;
	@Digits(integer=10, fraction=0)
	private BigDecimal tusu;
	@Digits(integer=10, fraction=0)
	private BigDecimal yutusu;
	@Digits(integer=10, fraction=0)
	private BigDecimal mutusu;
	@Digits(integer=10, fraction=0)
	private BigDecimal gigwonsu;
	private String jd;
	private String hbj;
	@Digits(integer=10, fraction=0)
	private BigDecimal dugsu;
	private int crOrder;
}
