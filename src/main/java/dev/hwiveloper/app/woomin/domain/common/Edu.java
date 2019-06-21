package dev.hwiveloper.app.woomin.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.springframework.beans.factory.annotation.Autowired;

import dev.hwiveloper.app.woomin.repository.EduRepository;

@Entity(name="woomin_edu")
@Getter
@Setter
@NoArgsConstructor
public class Edu implements Serializable {
	@EmbeddedId
	private EduPK key;

	private String eduName;
	private int eOrder;
	
	public Edu(int parseInt, String eduIdPart) {
		// TODO Auto-generated constructor stub
	}
}
