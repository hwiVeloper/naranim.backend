package dev.hwiveloper.naranim.domain.election;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//@Entity(name="candidate_master")
//@Table(name="candidate_master")
@Getter
@Setter
public class CandidateMaster {
	String name;
	String birthday;
	String hanjaName;
}
