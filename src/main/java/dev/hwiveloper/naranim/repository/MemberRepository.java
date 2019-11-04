package dev.hwiveloper.naranim.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hwiveloper.naranim.domain.assembly.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
