package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.common.Job;
import dev.hwiveloper.woomin.domain.common.JobPK;

public interface JobRepository extends CrudRepository<Job, JobPK> {

}
