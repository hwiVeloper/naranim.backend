package dev.hwiveloper.naranim.domain.election;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="promise")
@Getter
@Setter
public class Promise {
	@Id
	private String huboId;
	
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
	@Column(length = 2)
	private Integer prmsOrd;
	@Column(length = 100)
	private String prmsRealmName;
	@Column(length = 255)
	private String prmsTitle;
	@Column(columnDefinition="TEXT")
	private String prmsContent;
//	<sgId>20170509</sgId>
//	<sgTypecode>1</sgTypecode>
//	<cnddtId>100120965</cnddtId>
//	<sggName>대한민국</sggName>
//	<sidoName>전국</sidoName>
//	<wiwName/>
//	<partyName>더불어민주당</partyName>
//	<krName>문재인</krName>
//	<cnName>文在寅</cnName>
//	<prmsCnt>10</prmsCnt>
//	<prmsOrd1>1</prmsOrd1>
//	<prmsRealmName1>노동</prmsRealmName1>
//	<prmsTitle1>일자리를 책임지는 대한민국</prmsTitle1>
//	<prmmCont1>
}
