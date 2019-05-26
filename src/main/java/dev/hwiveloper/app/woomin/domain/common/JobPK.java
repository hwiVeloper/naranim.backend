package dev.hwiveloper.app.woomin.domain.common;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class JobPK implements Serializable {
	@Column(name="sg_id")
	private String sgId;
	
	@Column(name="job_id")
	private String jobId;
}
