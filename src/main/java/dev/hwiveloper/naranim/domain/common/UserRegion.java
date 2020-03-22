package dev.hwiveloper.naranim.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="user_region")
@Getter
@Setter
public class UserRegion {
	@Id
	private String email;
	
	@Column(name="sd_name", length=100)
	private String sdName;
	
	@Column(name="gusigun_name", length=100)
	private String gusigunName;
	
	@Column(name="emd_name", length=100)
	private String emdName;
	
	@Column(name="sggName", length=100)
	private String sggName;
}
