package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name="woomin_election_type")
@Getter
public class ElectionType {
	@Id
	private String sgTypeCode;
	private String sgTypeName;
}
