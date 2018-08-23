package com.miya.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miya.Repository.MemberInfoRepository;
import com.miya.model.MemberInfo;
import com.miya.service.MemberService;
import com.miya.utils.Result;

/**
 * @date 2018年6月28日 下午4:19:29
 *
 * @author zhoutuo
 */
@RestController
@RequestMapping("/memberInfo")
public class SimpleController {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	@RequestMapping("/findByMemberIdAndSaasId")
	public Result findByMemberIdAndSaasId() {
		MemberInfo memberInfo = memberService.findByMemberIdAndSaasId("0551000235534", "hzgd");
		return Result.success(memberInfo);
	}

	@RequestMapping("/findBySaasId")
	public Result findBySaasId() {
		List<MemberInfo> memberInfo = memberService.findBySaasId("hzgd");
		return Result.success(memberInfo);
	}

	@RequestMapping("/findByMemberName")
	public Result findByMemberName() {
		MemberInfo memberInfo = memberService.findByMemberName("张先生");
		return Result.success(memberInfo);
	}

	@RequestMapping("/updateMmeberPhone")
	public Result updateMmeberPhone() {
		int i = memberService.updateMmeberPhone("111111", "hzgd", "0551000235534");
		return Result.success(i);
	}

	@RequestMapping("/findBySaasIdWithPage")
	public Result findBySaasIdWithPage(@RequestParam String saasId, @RequestParam Integer page, Integer size) {
		Page<MemberInfo> memberInfo = memberService.findBySaasIdWithPage(saasId, page, size);
		return Result.success(memberInfo);
	}

	@RequestMapping("/produceTest")
	public Result produceTest(@RequestParam Integer in) {

		StoredProcedureQuery store = entityManager.createNamedStoredProcedureQuery("in_param");
		store.setParameter("p_in", 1);
		store.setParameter("str", 1);
		Integer o_in = (Integer) store.getOutputParameterValue("o_in");
		Integer ss = (Integer) store.getOutputParameterValue("ss");
		List<Integer> list = new ArrayList<>();
		list.add(o_in);
		list.add(ss);
		return Result.success(list);

	}

}
