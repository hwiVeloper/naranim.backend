package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Sungeogu;
import dev.hwiveloper.app.woomin.domain.common.SungeoguPK;

public interface SungeoguRepository extends CrudRepository<Sungeogu, SungeoguPK> {
	
}
