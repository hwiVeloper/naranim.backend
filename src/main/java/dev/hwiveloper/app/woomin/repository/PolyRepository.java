package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.assembly.Poly;

public interface PolyRepository extends CrudRepository<Poly, String> {
	Poly findByPolyNm(String polyNm);
}
