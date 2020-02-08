package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.election.Candidate;
import dev.hwiveloper.naranim.domain.election.CandidatePK;
import dev.hwiveloper.naranim.domain.election.PreCandidate;
import dev.hwiveloper.naranim.domain.election.PreCandidatePK;

public interface PreCandidateRepository extends CrudRepository<PreCandidate, PreCandidatePK> {
	@Query("SELECT c FROM candidate c WHERE c.key.sgId=:sgId AND c.key.sgTypeCode=:sgTypeCode AND c.wiwName=:wiwName")
	public List<PreCandidate> findCandidatesByWiwName(String sgId, String sgTypeCode, String wiwName);
	
	@Query("SELECT c FROM candidate c WHERE c.key.sgId=:sgId AND c.key.sgTypeCode=:sgTypeCode AND c.sggName=:sggName")
	public List<PreCandidate> findCandidatesBySggName(String sgId, String sgTypeCode, String sggName);
	
	@Query("SELECT c FROm candidate c WHERE c.key.huboId=:huboId")
	public PreCandidate findByKeyHuboId(String huboId);
}