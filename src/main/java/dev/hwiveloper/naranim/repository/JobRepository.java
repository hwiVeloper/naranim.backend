package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.Job;
import dev.hwiveloper.naranim.domain.common.JobPK;

public interface JobRepository extends CrudRepository<Job, JobPK> {

}
