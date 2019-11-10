package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.election.VoteResult;
import dev.hwiveloper.naranim.domain.election.VoteResultPK;

public interface VoteResultRepository extends CrudRepository<VoteResult, VoteResultPK>{
	@Query("SELECT DISTINCT v.sdName FROM woomin_vote_result v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode")
	public List<String> findDistinctSdNameByKey(String sgId, String sgTypeCode);
	
	@Query("SELECT DISTINCT v.sdName, v.key.sggName FROM woomin_vote_result v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode ORDER BY v.sdName, v.key.sggName, v.crOrder")
	public List<String> findDistinctSdNameSggNameByKey(String sgId, String sgTypeCode);
	
	@Query("SELECT v FROM woomin_vote_result v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode AND v.key.sggName=:sggName")
	public List<VoteResult> findAllByKey(String sgId, String sgTypeCode, String sggName);
}