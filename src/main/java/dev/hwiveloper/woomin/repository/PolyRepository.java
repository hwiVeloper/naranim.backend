package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.assembly.Poly;

public interface PolyRepository extends CrudRepository<Poly, String> {
	Poly findByPolyNm(String polyNm);
}
