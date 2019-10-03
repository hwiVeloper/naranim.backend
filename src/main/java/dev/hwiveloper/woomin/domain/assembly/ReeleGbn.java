package dev.hwiveloper.woomin.domain.assembly;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_reele_gbn")
@Getter
@Setter
public class ReeleGbn {
	@Id
	private String reeleGbnCd;
	private String reeleGbnNm;
}
