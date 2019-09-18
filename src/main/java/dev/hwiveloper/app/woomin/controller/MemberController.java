package dev.hwiveloper.app.woomin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.assembly.Member;
import dev.hwiveloper.app.woomin.repository.MemberRepository;

@RestController
@RequestMapping("/members")
public class MemberController {
	@Autowired
	MemberRepository memberRepo;
	
	@GetMapping("/")
	public ResponseEntity<List<Member>> getMembers() {
		return new ResponseEntity<List<Member>>((List<Member>) memberRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{dept_cd}")
	public ResponseEntity<?> getMemberDetail(
		@PathVariable("dept_cd") String deptCd
	) {
		Member member = memberRepo.findById(deptCd).get();
		if (member == null) {
			return new ResponseEntity("User with id " + deptCd + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
}
