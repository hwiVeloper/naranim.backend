package dev.hwiveloper.naranim.repository;

import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.common.Region;
import dev.hwiveloper.naranim.domain.common.RegionPK;

public interface RegionRepository extends CrudRepository<Region, RegionPK> {
	List<Region> findAllByKey(@Nullable RegionPK key);
}
