package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.assembly.ReeleGbn;

public interface ReeleGbnRepository extends CrudRepository<ReeleGbn, String> {
	ReeleGbn findByReeleGbnNm(String reeleGbnNm);
}
