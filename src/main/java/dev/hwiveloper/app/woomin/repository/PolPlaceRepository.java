package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.PolPlace;
import dev.hwiveloper.app.woomin.domain.election.PolPlacePK;

public interface PolPlaceRepository extends CrudRepository<PolPlace, PolPlacePK> {

}
