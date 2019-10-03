package dev.hwiveloper.woomin.domain.assembly;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_poly")
@Getter
@Setter
public class Poly {
	@Id
	private String polyCd;
	private String polyNm;
}
