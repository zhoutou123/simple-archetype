package com.miya.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.miya.model.MemberInfo;

/**
 * @date 2018年6月28日 下午4:45:03
 *
 * @author zhoutuo
 */
public interface MemberInfoRepository extends PagingAndSortingRepository<MemberInfo, Long>, JpaSpecificationExecutor<MemberInfo> {

	MemberInfo findByMemberIdAndSaasId(String memberId, String saasId);

	List<MemberInfo> findBySaasId(String saasId);

	Page<MemberInfo> findBySaasId(String saasId, Pageable page);

	@Query(value = "select * from member_info_0 where name =?1", nativeQuery = true)
	MemberInfo findByMemberName(String name);

	@Modifying
	@Query(value = "update member_info_0 set phone =?1 where saas_id=?2 and member_id=?3", nativeQuery = true)
	int updateMmeberPhone(String phone, String saasId, String memberId);

	/**
	 * @description 只有一个返回参数的程序过程可使用这种方式调用
	 * @author zhoutuo
	 * @date 2018年8月23日 上午9:45:35
	 */
	@Procedure("in_param")
	int produceTest(@Param("p_in") Integer in);

}
