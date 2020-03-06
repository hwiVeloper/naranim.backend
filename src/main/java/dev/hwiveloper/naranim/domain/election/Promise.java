package dev.hwiveloper.naranim.domain.election;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="promise")
@Getter
@Setter
public class Promise {
	@EmbeddedId
	private PromisePK key;
	
	@Column(length = 8)
	private String sgId;
	@Column(length = 2)
	private String sgTypeCode;
	@Column(length = 50)
	private String sggName;
	@Column(length = 50)
	private String sidoName;
	@Column(length = 50)
	private String wiwName;
	@Column(length = 50)
	private String partyName;
	@Column(length = 50)
	private String krName;
	@Column(length = 50)
	private String cnName;
	@Column(length = 100)
	private String prmsRealmName;
	@Column(length = 255)
	private String prmsTitle;
	@Column(columnDefinition="TEXT")
	private String prmsContent;
}
