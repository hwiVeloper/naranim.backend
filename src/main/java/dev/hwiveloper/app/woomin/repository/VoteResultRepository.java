package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.VoteResult;
import dev.hwiveloper.app.woomin.domain.election.VoteResultPK;

public interface VoteResultRepository extends CrudRepository<VoteResult, VoteResultPK>{

}
