package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.Election;
import dev.hwiveloper.naranim.domain.common.ElectionPK;

public interface ElectionRepository extends CrudRepository<Election, ElectionPK> {
	
	@Query("SELECT e.key.sgId as sgId FROM election e GROUP BY e.key.sgId")
	List<String> findDistinctKeySgId();

	@Query("SELECT e FROM election e WHERE e.key.sgId=:sgId")
	List<Election> findAllBySgId(String sgId);
	
}
