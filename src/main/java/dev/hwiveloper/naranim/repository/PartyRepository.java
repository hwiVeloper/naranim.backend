package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.Party;
import dev.hwiveloper.naranim.domain.common.PartyPK;

public interface PartyRepository extends CrudRepository<Party, PartyPK> {
	
}
