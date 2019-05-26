package dev.hwiveloper.app.woomin.domain.common;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PolPlacePK implements Serializable {
	private boolean preYn; // pk 사전투표소 여부
	private String sgId; // pk
	private int num; // pk
	private String sdName; // pk
}
