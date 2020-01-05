package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.election.Evaluation;

public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
	public List<Evaluation> findByUserIdOrderByIdDesc(String userId);
	
	public List<Evaluation> findTop5ByApplyYnOrderByIdDesc(char applyYn);
}
