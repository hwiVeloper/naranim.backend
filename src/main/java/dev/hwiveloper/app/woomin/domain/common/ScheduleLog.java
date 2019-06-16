package dev.hwiveloper.app.woomin.domain.common;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name="woomin_schedule_log")
@Getter
@Setter
public class ScheduleLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger id;
	
	@Column(name="group_name", length=100, nullable=false)
	private String groupName;
	
	@Column(name="job_name", length=100, nullable=false)
	private String jobName;
	
	@Column(name="result_type", length=20, nullable=false)
	private String resultType;
	
	@Column(name="result_desc", length=255, nullable=false)
	private String resultDesc;

	@Column(name="start_time", nullable=false)
	private Date startTime;

	@CreationTimestamp
	@Column(name="end_time", nullable=false)
	private Date endTime;
}
