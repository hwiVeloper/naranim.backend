package dev.hwiveloper.woomin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.election.Candidate;
import dev.hwiveloper.woomin.domain.election.CandidatePK;

public interface CandidateRepository extends CrudRepository<Candidate, CandidatePK> {
	@Query("SELECT c FROM woomin_candidate c WHERE c.key.sgId=:sgId AND c.key.sgTypeCode=:sgTypeCode AND c.wiwName=:wiwName ORDER BY c.key.giho")
	public List<Candidate> findCandidatesByWiwName(String sgId, String sgTypeCode, String wiwName);
	
	@Query("SELECT c FROM woomin_candidate c WHERE c.key.sgId=:sgId AND c.key.sgTypeCode=:sgTypeCode AND c.sggName=:sggName ORDER BY c.key.giho")
	public List<Candidate> findCandidatesBySggName(String sgId, String sgTypeCode, String sggName);
	
	@Query("SELECT c FROm woomin_candidate c WHERE c.key.huboId=:huboId")
	public Candidate findByKeyHuboId(String huboId);
}