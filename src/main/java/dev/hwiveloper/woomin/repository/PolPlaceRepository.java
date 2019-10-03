package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.election.PolPlace;
import dev.hwiveloper.woomin.domain.election.PolPlacePK;

public interface PolPlaceRepository extends CrudRepository<PolPlace, PolPlacePK> {

}
