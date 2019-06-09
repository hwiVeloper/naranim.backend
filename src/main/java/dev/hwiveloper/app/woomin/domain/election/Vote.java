package dev.hwiveloper.app.woomin.domain.election;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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
	private BigDecimal totSunsu;
	private BigDecimal psSunsu;
	private BigDecimal psEtcSunsu;
	private BigDecimal totTusu;
	private BigDecimal psTusu;
	private BigDecimal psEtcTusu;
	private BigDecimal turnout;
	private int vrOrder;
}
