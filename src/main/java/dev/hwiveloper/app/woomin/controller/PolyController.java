package dev.hwiveloper.app.woomin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.assembly.Poly;
import dev.hwiveloper.app.woomin.repository.PolyRepository;

@RestController
@RequestMapping("/polys")
public class PolyController {
	@Autowired
	PolyRepository polyRepo;
	
	@GetMapping("/")
	public ResponseEntity<List<Poly>> getPolys() {
		return new ResponseEntity<List<Poly>>((List<Poly>) polyRepo.findAll(), HttpStatus.OK);
	}
}