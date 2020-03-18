package dev.hwiveloper.naranim.domain.common;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="region")
@Getter
@Setter
public class Region {
	
	@EmbeddedId
	private RegionPK key;
	
	@Column(name="etc_name", nullable=true, length=50)
	private String etcName;
	
	@Column(name="jungsu", length=1)
	private Integer jungsu;
	
	@Column(name="sgg_name", length=100)
	private String sggName;
	
}
