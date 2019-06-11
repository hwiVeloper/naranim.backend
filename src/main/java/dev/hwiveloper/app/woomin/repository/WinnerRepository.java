package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.election.Winner;
import dev.hwiveloper.app.woomin.domain.election.WinnerPK;

public interface WinnerRepository extends CrudRepository<Winner, WinnerPK> {

}
