package com.ureca.shopdemo.domain.member.address;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
    // @Query("select a from MemberAddress a where a.isDefault = true and a.member.id = :memberId")
    Optional<MemberAddress> findByMemberIdAndIsDefaultTrue(Long memberId);

    List<MemberAddress> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);

    Optional<MemberAddress> findTopByMemberIdOrderByIdDesc(Long memberId);
}
