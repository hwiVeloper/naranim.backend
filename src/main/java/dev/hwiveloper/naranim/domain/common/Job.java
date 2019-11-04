package dev.hwiveloper.naranim.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_job")
@Getter
@Setter
public class Job {
	@EmbeddedId
	private JobPK key;
	
	private String jobName;
	private int jOrder;
}
