package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.election.Promise;
import dev.hwiveloper.naranim.domain.election.PromisePK;

public interface PromiseRepository extends CrudRepository<Promise, PromisePK> {

}
