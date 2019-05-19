package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPK {
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="j_order")
	private String jOrder;
}
