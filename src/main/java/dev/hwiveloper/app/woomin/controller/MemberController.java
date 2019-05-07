package dev.hwiveloper.app.woomin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.hwiveloper.app.woomin.domain.Member;
import dev.hwiveloper.app.woomin.domain.Orig;
import dev.hwiveloper.app.woomin.repository.MemberRepository;
import dev.hwiveloper.app.woomin.repository.OrigRepository;

@Controller
@RequestMapping("/members")
public class MemberController {
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	OrigRepository origRepo;
	
	@GetMapping("/")
	public ResponseEntity<List<Member>> getMembers() {
		return new ResponseEntity<List<Member>>((List<Member>) memberRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{dept_cd}")
	public ResponseEntity<Member> getMemberDetail(@PathVariable("dept_cd") String dept_cd) {
		return new ResponseEntity<Member>(memberRepo.findById(dept_cd).get(), HttpStatus.OK);
	}
}
