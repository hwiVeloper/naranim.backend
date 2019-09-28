package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.Vote;
import dev.hwiveloper.app.woomin.domain.election.VotePK;

public interface VoteRepository extends CrudRepository<Vote, VotePK> {
	@Query("SELECT DISTINCT v.sdName FROM woomin_vote v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode AND v.sdName != '합계'")
	public List<String> findDistinctSdNameByKey(String sgId, String sgTypeCode);

	@Query("SELECT v FROM woomin_vote v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode AND v.sdName=:sdName")
	public List<Vote> findAllByKeyAndSdName(String sgId, String sgTypeCode, String sdName);
	
	@Query("SELECT v FROM woomin_vote v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode")
	public List<Vote> findAllByKey(String sgId, String sgTypeCode);
	
	@Query("SELECT v FROM woomin_vote v WHERE v.key.sgId=:sgId AND v.key.sgTypeCode=:sgTypeCode AND v.wiwName='합계'")
	public List<Vote> findAllTotal(String sgId, String sgTypeCode);
}
