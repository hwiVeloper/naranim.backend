package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.ElectionType;

public interface ElectionTypeRepository extends CrudRepository<ElectionType, String> {
}
