package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Job {
	@EmbeddedId
	private JobPK key;
	
	private String jobName;
}