package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.common.ElectionPK;
import dev.hwiveloper.app.woomin.domain.common.ElectionType;

public interface ElectionTypeRepository extends CrudRepository<ElectionType, String> {
}
