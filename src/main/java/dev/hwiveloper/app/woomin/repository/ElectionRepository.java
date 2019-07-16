package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.common.ElectionPK;

public interface ElectionRepository extends CrudRepository<Election, ElectionPK> {
	@Query("SELECT e.key.sgId as sgId FROM woomin_election e GROUP BY e.key.sgId")
	List<String> findDistinctKeySgId();

	@Query("SELECT e FROM woomin_election e WHERE e.key.sgId=:sgId")
	List<Election> findAllBySgId(String sgId);
}
