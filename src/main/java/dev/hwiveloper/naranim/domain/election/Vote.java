package dev.hwiveloper.naranim.domain.election;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_vote")
@Getter
@Setter
public class Vote {
	@EmbeddedId
	private VotePK key;
	
	private String sdName;
	private String wiwName;
	@Digits(integer=10, fraction=0)
	private BigDecimal totSunsu;
	@Digits(integer=10, fraction=0)
	private BigDecimal psSunsu;
	@Digits(integer=10, fraction=0)
	private BigDecimal psEtcSunsu;
	@Digits(integer=10, fraction=0)
	private BigDecimal totTusu;
	@Digits(integer=10, fraction=0)
	private BigDecimal psTusu;
	@Digits(integer=10, fraction=0)
	private BigDecimal psEtcTusu;
	@Digits(integer=10, fraction=2)
	private BigDecimal turnout;
	private int vrOrder;
}
