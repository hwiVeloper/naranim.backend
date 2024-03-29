package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.election.Winner;
import dev.hwiveloper.naranim.domain.election.WinnerPK;

public interface WinnerRepository extends CrudRepository<Winner, WinnerPK> {
	@Query("SELECT w FROM winner w WHERE w.key.sgId=:sgId AND w.key.sgTypeCode=:sgTypeCode AND w.wiwName=:wiwName ORDER BY w.key.giho")
	public List<Winner> findWinnerByWiwName(String sgId, String sgTypeCode, String wiwName);
	
	@Query("SELECT w FROM winner w WHERE w.key.sgId=:sgId AND w.key.sgTypeCode=:sgTypeCode AND w.sggName=:sggName ORDER BY w.key.giho")
	public List<Winner> findWinnerBySggName(String sgId, String sgTypeCode, String sggName);
}
