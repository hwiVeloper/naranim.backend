package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.VoteResult;
import dev.hwiveloper.app.woomin.domain.election.VoteResultPK;

public interface VoteResultRepository extends CrudRepository<VoteResult, VoteResultPK>{
	@Query("SELECT DISTINCT v.sdName FROM woomin_vote_result v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode")
	public List<String> findDistinctSdNameByKey(String sgId, String sgTypeCode);
}
