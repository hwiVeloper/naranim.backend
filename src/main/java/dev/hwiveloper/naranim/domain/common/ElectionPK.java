package dev.hwiveloper.naranim.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ElectionPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="sg_type_code")
	private String sgTypeCode;
}
