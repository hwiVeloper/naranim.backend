package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Sungeogu;
import dev.hwiveloper.app.woomin.domain.common.SungeoguPK;

import java.util.List;

public interface SungeoguRepository extends CrudRepository<Sungeogu, SungeoguPK> {
	@Query(value = "SELECT * FROM woomin_sungeogu s WHERE s.sd_name != '전국' group by s.sg_id, s.sd_name ORDER BY s.sg_id, s.s_order", nativeQuery = true)
    List<Sungeogu> findDistinctSgIdSdNameBySdNameOrderBySgIdSOrder();
}
