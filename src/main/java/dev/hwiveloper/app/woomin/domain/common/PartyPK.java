package dev.hwiveloper.app.woomin.domain.common;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PartyPK implements Serializable{
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="p_order")
	private int pOrder;
}
