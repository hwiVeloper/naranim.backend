package dev.hwiveloper.app.woomin.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@IdClass(EduPK.class)
@NoArgsConstructor
public class Edu {
	@Id
    private String sgId;
	@Id
    private String eduId;

    private String eduName;
    private int eOrder;
    
    public Edu(
    		String sgId,
    		String eduId,
    		String eduName,
    		int eOrder
    		) {
		this.sgId = sgId;
		this.eduId = eduId;
		this.eduName = eduName;
		this.eOrder = eOrder;
	}
}
