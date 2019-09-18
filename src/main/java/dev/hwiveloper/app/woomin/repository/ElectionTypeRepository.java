package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.ElectionType;

public interface ElectionTypeRepository extends CrudRepository<ElectionType, String> {
}
