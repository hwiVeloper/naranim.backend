package dev.hwiveloper.app.woomin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.Orig;

public interface OrigRepository extends CrudRepository<Orig, String> {

	List<Orig> findByUpOrigCd(Object object);
	Orig findByOrigNm(String origNm);
}
