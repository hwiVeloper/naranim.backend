package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.election.PolPlace;
import dev.hwiveloper.naranim.domain.election.PolPlacePK;

public interface PolPlaceRepository extends CrudRepository<PolPlace, PolPlacePK> {

}
