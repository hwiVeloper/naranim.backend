package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Job;
import dev.hwiveloper.app.woomin.domain.common.JobPK;

public interface JobRepository extends CrudRepository<Job, JobPK> {

}
