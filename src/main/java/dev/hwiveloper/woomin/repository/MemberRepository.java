package dev.hwiveloper.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.woomin.domain.assembly.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
