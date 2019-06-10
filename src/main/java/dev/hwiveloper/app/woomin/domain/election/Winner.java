package dev.hwiveloper.app.woomin.domain.election;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_winner")
@Getter
@Setter
public class Winner {
	private int num;
	private String sgId;
	private String sgTypeCode;
	private String huboId;
	private String sggName;
	private String sdName;
	private String wiwName;
	private int giho;
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
	private BigDecimal dugsu;
	private BigDecimal dugyul;
}
