package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.Candidate;
import dev.hwiveloper.app.woomin.domain.election.CandidatePK;

public interface CandidateRepository extends CrudRepository<Candidate, CandidatePK> {
}
