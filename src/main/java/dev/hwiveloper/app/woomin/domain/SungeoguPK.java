package dev.hwiveloper.app.woomin.domain;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SungeoguPK implements Serializable {
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="sg_type_code")
	private String sgTypeCode;
	
	@Column(name="sgg_name")
	private String sggName;
}
