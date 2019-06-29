package dev.hwiveloper.app.woomin.domain.election;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PolPlacePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="pre_yn")
	private boolean preYn;
	
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="num")
	private int num;
	
	@Column(name="sd_name")
	private String sdName;
}
