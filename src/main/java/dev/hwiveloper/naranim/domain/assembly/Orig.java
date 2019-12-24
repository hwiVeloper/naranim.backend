package dev.hwiveloper.naranim.domain.assembly;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="orig")
@Getter
@Setter
public class Orig {
	@Id
	private String origCd;
	private String origNm;
	private String upOrigCd;
}
