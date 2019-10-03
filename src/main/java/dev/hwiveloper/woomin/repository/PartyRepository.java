package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.common.Party;
import dev.hwiveloper.woomin.domain.common.PartyPK;

public interface PartyRepository extends CrudRepository<Party, PartyPK> {
	
}
