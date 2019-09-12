package dev.hwiveloper.app.woomin.domain.election;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_winner")
@Getter
@Setter
public class Winner {
	@EmbeddedId
	private WinnerPK key;
	
	private String sggName;
	private String sdName;
	private String wiwName;
	private String gihoSangse;
	private String jdName;
	private String name;
	private String hanjaName;
	private String gender;
	private String birthday;
	private int age;
	private String addr;
	private String jobId;
	private String job;
	private String eduId;
	private String edu;
	private String career1;
	private String career2;
	@Digits(integer=10, fraction=0)
	private BigDecimal dugsu;
	@Digits(integer=10, fraction=2)
	private BigDecimal dugyul;
}
