package dev.hwiveloper.app.woomin.domain;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GusigunPK implements Serializable {
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="w_order")
	private int wOrder;
}
