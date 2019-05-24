package dev.hwiveloper.app.woomin.repository;

import dev.hwiveloper.app.woomin.domain.common.Edu;
import dev.hwiveloper.app.woomin.domain.common.EduPK;
import org.springframework.data.repository.CrudRepository;

public interface EduRepository extends CrudRepository<Edu, EduPK> {
}
