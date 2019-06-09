package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.Vote;
import dev.hwiveloper.app.woomin.domain.election.VotePK;

public interface VoteRepository extends CrudRepository<Vote, VotePK> {

}
