package dev.hwiveloper.naranim.domain.election;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name="evaluation")
@Table(name = "evaluation")
@Getter
@Setter
public class Evaluation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String userId;
	
	@Column(length = 50)
	private String candidateName;
	@Column(length = 8)
	private String candidateBirthday;
	@Column(length = 50)
	private String candidateHanjaName;
	
	@Column(length = 8)
	private String sgId;
	@Column(length = 5)
	private String sgTypeCode;
	@Column(length = 100)
	private String sdName;
	@Column(length = 100)
	private String wiwName;
	@Column(length = 100)
	private String sggName;
	
	@Column(name="eval_pledge_s")
	private Integer evalPledgeS;
	@Column(name="eval_pledge_m")
	private Integer evalPledgeM;
	@Column(name="eval_pledge_a")
	private Integer evalPledgeA;
	@Column(name="eval_pledge_r")
	private Integer evalPledgeR;
	@Column(name="eval_pledge_t")
	private Integer evalPledgeT;
	
	@Column(name="eval_individual_p")
	private Integer evalIndividualP;
	@Column(name="eval_individual_l")
	private Integer evalIndividualL;
	@Column(name="eval_individual_u")
	private Integer evalIndividualU;
	@Column(name="eval_individual_s")
	private Integer evalIndividualS;
	
	@Column(length = 255, nullable = false)
	private String evalTitle;
	@Column(length = 4000, nullable = false)
	private String evalDescription;
	
	@Column(columnDefinition = "char(1) default 'N' not null")
	private Character applyYn; // Y, N
	
	@CreationTimestamp
	private Timestamp regDate;
}
