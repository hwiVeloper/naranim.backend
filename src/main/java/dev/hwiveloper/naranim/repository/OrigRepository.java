package dev.hwiveloper.naranim.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.assembly.Orig;

public interface OrigRepository extends CrudRepository<Orig, String> {

	List<Orig> findByUpOrigCd(Object object);
	Orig findByOrigNm(String origNm);
}
