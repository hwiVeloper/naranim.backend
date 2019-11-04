package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.assembly.Poly;

public interface PolyRepository extends CrudRepository<Poly, String> {
	Poly findByPolyNm(String polyNm);
}
