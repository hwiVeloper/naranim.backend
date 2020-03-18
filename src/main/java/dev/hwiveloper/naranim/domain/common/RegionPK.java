package dev.hwiveloper.naranim.domain.common;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RegionPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="sg_id", length=8)
	private String sgId;
	
	@Column(name="sg_type_code", length=2)
	private String sgTypeCode;
	
	@Column(name="sd_name", length=100)
	private String sdName;
	
	@Column(name="gusigun_name", length=100)
	private String gusigunName;
	
	@Column(name="emd_name", length=100)
	private String emdName;
}
