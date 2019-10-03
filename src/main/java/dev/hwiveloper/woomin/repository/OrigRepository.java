package dev.hwiveloper.woomin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.assembly.Orig;

public interface OrigRepository extends CrudRepository<Orig, String> {

	List<Orig> findByUpOrigCd(Object object);
	Orig findByOrigNm(String origNm);
}
