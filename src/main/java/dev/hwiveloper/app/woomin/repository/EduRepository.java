package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import dev.hwiveloper.app.woomin.domain.common.Edu;
import dev.hwiveloper.app.woomin.domain.common.EduPK;

public interface EduRepository extends CrudRepository<Edu, EduPK> {
	List<Edu> findByKeySgIdAndKeyEduId(@Param("sgId") String sgId, @Param("eduId") String eduId);
}
