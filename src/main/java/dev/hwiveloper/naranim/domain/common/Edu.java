package dev.hwiveloper.naranim.domain.common;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="woomin_edu")
@Getter
@Setter
@NoArgsConstructor
public class Edu implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EduPK key;

	private String eduName;
	private int eOrder;
}
