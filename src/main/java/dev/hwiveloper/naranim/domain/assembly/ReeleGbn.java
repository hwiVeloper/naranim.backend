package dev.hwiveloper.naranim.domain.assembly;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="reele_gbn")
@Getter
@Setter
public class ReeleGbn {
	@Id
	private String reeleGbnCd;
	private String reeleGbnNm;
}
