package dev.hwiveloper.app.woomin.domain.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class EduPK implements Serializable {
    @Column(name="sg_id")
    private String sgId;

    @Column(name="edu_id")
    private String eduId;
}
