package dev.hwiveloper.naranim.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.Sungeogu;
import dev.hwiveloper.naranim.domain.common.SungeoguPK;

import java.util.List;

public interface SungeoguRepository extends CrudRepository<Sungeogu, SungeoguPK> {
	@Query(value = "SELECT * FROM sungeogu s WHERE s.sd_name != '전국' group by s.sg_id, s.sd_name ORDER BY s.sg_id, s.s_order", nativeQuery = true)
    List<Sungeogu> findDistinctSgIdSdNameBySdNameOrderBySgIdSOrder();
}
