package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.PolPlace;
import dev.hwiveloper.app.woomin.domain.common.PolPlacePK;

public interface PolPlaceRepository extends CrudRepository<PolPlace, PolPlacePK> {

}
