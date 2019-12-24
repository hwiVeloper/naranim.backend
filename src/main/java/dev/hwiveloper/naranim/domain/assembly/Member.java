package dev.hwiveloper.naranim.domain.assembly;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * deptCd		부서코드
 * num			식별코드
 * empNm		한글이름
 * hjNm		한자이름
 * engNm		영문이름
 * reeleGbnNm	당선횟수
 * origNm		선거구
 * jpgLink		의원사진 파일경로
 */
@Entity(name="congress_member")
@Getter
@Setter
@ToString
public class Member {
	@Id
	private String deptCd;
	
	// 기본정보
	private String empNm;
	private String engNm;
	private String hjNm;
	private String jpgLink;
	private int num;
	private String origCd;
	private String origNm;
	private String reeleGbnCd;
	private String reeleGbnNm;
	
	// 상세정보
	private String bthDate;
	private String polyCd;
	private String polyNm;
	private String shrtNm;
	private String electionNum;
	private String assemTel;
	private String assemHomep;
	private String assemEmail;
	private String staff;
	private String secretary;
	private String secretary2;
	private String hbbyCd;
	private String examCd;
	private String memTitle;
}
