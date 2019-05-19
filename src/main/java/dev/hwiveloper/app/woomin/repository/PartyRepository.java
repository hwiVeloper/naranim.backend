package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.common.Party;
import dev.hwiveloper.app.woomin.domain.common.PartyPK;

public interface PartyRepository extends CrudRepository<Party, PartyPK> {
	
}
