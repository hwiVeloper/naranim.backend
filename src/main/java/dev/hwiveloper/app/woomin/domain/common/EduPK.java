package dev.hwiveloper.app.woomin.domain.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class EduPK {
    @Column(name="sg_id")
    private String sgId;

    @Column(name="edu_id")
    private String eduId;
}
