package dev.hwiveloper.app.woomin.domain.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="woomin_edu")
@Getter
@Setter
public class Edu {
    @EmbeddedId
    private EduPK key;

    private String eduName;
    private int eOrder;
}
