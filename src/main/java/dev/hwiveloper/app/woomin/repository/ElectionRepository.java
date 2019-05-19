package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.Election;
import dev.hwiveloper.app.woomin.domain.ElectionPK;

public interface ElectionRepository extends CrudRepository<Election, ElectionPK> {
	@Description("findDistinctKeySgId :: Get distinct sgId")
	@Query("SELECT e.key.sgId as sgId FROM woomin_election e GROUP BY e.key.sgId")
	List<String> findDistinctKeySgId();
}
