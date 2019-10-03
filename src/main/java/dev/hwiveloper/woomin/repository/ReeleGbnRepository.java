package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.assembly.ReeleGbn;

public interface ReeleGbnRepository extends CrudRepository<ReeleGbn, String> {
	ReeleGbn findByReeleGbnNm(String reeleGbnNm);
}
