package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.Region;
import dev.hwiveloper.naranim.domain.common.RegionPK;

public interface RegionRepository extends CrudRepository<Region, RegionPK> {
}
