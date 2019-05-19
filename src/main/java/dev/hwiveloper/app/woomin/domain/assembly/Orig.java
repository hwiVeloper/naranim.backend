package dev.hwiveloper.app.woomin.domain.assembly;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_orig")
@Getter
@Setter
public class Orig {
	@Id
	private String origCd;
	private String origNm;
	private String upOrigCd;
}
