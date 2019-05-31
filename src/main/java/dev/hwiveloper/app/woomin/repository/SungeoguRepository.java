package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Sungeogu;
import dev.hwiveloper.app.woomin.domain.common.SungeoguPK;

import java.util.List;

public interface SungeoguRepository extends CrudRepository<Sungeogu, SungeoguPK> {
	@Query(value = "SELECT DISTINCT s.key.sgId, s.sdName FROM woomin_sungeogu s WHERE s.sdName not '전국' ORDER BY s.key.sgId, s.sOrder", nativeQuery = true)
    List<Sungeogu> findDistinctSgIdSdNameBySdNameOrderBySgIdSOrder();
}
