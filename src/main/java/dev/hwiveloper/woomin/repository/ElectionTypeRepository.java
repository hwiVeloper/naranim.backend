package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.common.ElectionType;

public interface ElectionTypeRepository extends CrudRepository<ElectionType, String> {
}
