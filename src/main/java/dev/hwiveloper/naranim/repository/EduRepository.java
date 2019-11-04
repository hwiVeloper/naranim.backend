package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import dev.hwiveloper.naranim.domain.common.Edu;
import dev.hwiveloper.naranim.domain.common.EduPK;

public interface EduRepository extends CrudRepository<Edu, EduPK> {
	List<Edu> findByKeySgId(@Param("sgId") String sgId);
	List<Edu> findByKeySgIdOrKeyEduId(@Param("sgId") String sgId, @Param("eduId") String eduId);
}
