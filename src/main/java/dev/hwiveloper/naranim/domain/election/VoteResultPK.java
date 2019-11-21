package dev.hwiveloper.naranim.domain.election;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class VoteResultPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="sg_id", length=8)
	private String sgId;
	
	@Column(name="sg_type_code", length=2)
	private String sgTypeCode;
	
	@Column(name="sgg_name", length=100)
	private String sggName;
	
	@Column(name="hubo_order", length=3)
	private int huboOrder;
	
	@Column(name="sd_name", length=100)
	private String sdName;
	
	@Column(name="wiw_name", length=100)
	private String wiwName;
}
