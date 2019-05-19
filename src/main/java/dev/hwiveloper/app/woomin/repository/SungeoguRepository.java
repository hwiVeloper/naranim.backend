package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.Sungeogu;
import dev.hwiveloper.app.woomin.domain.SungeoguPK;

public interface SungeoguRepository extends CrudRepository<Sungeogu, SungeoguPK> {
	
}
