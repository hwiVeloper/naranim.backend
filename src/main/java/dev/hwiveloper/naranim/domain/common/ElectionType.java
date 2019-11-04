package dev.hwiveloper.naranim.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Entity(name="woomin_election_type")
@Getter
public class ElectionType {
	@Id
	@NotNull
	@Column(name = "sg_type_code", length = 2)
	private String sgTypeCode;
	
	@NotNull
	@Column(name = "sg_type_name", length = 50)
	private String sgTypeName;
}
