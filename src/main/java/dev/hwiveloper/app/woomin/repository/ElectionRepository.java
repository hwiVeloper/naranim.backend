package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.Election;

public interface ElectionRepository extends CrudRepository<Election, String> {
	
}
