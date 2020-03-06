package dev.hwiveloper.naranim.domain.election;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PromisePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="hubo_id")
	private String huboId;
	
	@Column(name="prms_ord", length = 2)
	private Integer prmsOrd;
	
}
