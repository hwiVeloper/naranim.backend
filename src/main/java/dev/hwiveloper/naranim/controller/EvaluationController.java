package dev.hwiveloper.naranim.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.naranim.domain.election.Evaluation;
import dev.hwiveloper.naranim.repository.EvaluationRepository;
import dev.hwiveloper.naranim.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/evaluation")
@Slf4j
public class EvaluationController {
	@Autowired
	EvaluationRepository evaluationRepo;
	
	@Autowired
	EvaluationService evaluationService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Evaluation> evaluationGetId(@PathVariable(value = "id") Long id) {
		Evaluation evaluation = evaluationRepo.findById(id).orElse(new Evaluation());
		
		return new ResponseEntity<Evaluation>(evaluation, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> evaluationPost(@RequestBody Evaluation evaluation) {
		evaluation.setApplyYn('N');
		
		evaluationRepo.save(evaluation);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/getEvaluationByUserId")
	public ResponseEntity<List<Evaluation>> getEvaluationByUserId(@RequestBody Evaluation evaluation) {
		List<Evaluation> myEvaluation = evaluationRepo.findByUserId(evaluation.getUserId());
		
		return new ResponseEntity<List<Evaluation>>(myEvaluation, HttpStatus.OK);
	}
}
