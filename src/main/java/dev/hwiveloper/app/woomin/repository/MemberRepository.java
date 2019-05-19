package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.assembly.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
