package dev.hwiveloper.app.woomin.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.app.woomin.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
