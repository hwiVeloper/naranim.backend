package dev.hwiveloper.app.woomin.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class EduPK implements Serializable {
    @Column(name="sg_id")
    private String sgId;

    @Column(name="edu_id")
    private String eduId;
    
    public EduPK(String sgId, String eduId) {
    	this.sgId = sgId;
    	this.eduId = eduId;
    }
}
