package dev.hwiveloper.naranim.domain.election;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name="pre_candidate")
@Getter
@Setter
public class PreCandidate {
	@EmbeddedId
	private PreCandidatePK key;
	
	private String sggName;
	private String sdName;
	private String wiwName;
	private String jdName;
	private String name;
	private String hanjaName;
	private String gender;
	private String birthday;
	private int age;
	private String addr;
	private String jobId;
	private String job;
	private String eduId;
	private String edu;
	private String career1;
	private String career2;
	private String status;
}
