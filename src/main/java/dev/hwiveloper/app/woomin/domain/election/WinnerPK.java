package dev.hwiveloper.app.woomin.domain.election;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class WinnerPK implements Serializable {
	@Column(name="num")
	private int num;
	
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="sg_type_code")
	private String sgTypeCode;
	
	@Column(name="hubo_id")
	private String huboId;
}