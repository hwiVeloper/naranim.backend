package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.assembly.ReeleGbn;

public interface ReeleGbnRepository extends CrudRepository<ReeleGbn, String> {
	ReeleGbn findByReeleGbnNm(String reeleGbnNm);
}
