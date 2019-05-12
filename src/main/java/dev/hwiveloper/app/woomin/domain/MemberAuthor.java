package dev.hwiveloper.app.woomin.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_member_author")
@Getter
@Setter
public class MemberAuthor {
	@Id
	private String deptCd;
	private String autNm;
	private String cn;
	private String imageurl;
	private String publishName;
	private String regDate;
	private String sj;
}
