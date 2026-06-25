package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class MemberDummy {

    public static Member createMockMember() {
        return Member.builder()
                .id(1L)
                .name("홍길동")
                .email("test@example.com")
                .status(MemberStatus.ACTIVE)
                .build();
    }

    // expiredAt 제거 → deletedAt으로 대체 (탈퇴된 회원 테스트용)
    public static Member createDeletedMockMember() {
        return Member.builder()
                .id(1L)
                .name("홍길동")
                .email("test@example.com")
                .status(MemberStatus.WITHDRAW)
                .deletedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
                .build();
    }

    public static MemberJoinRequest createMemberJoinRequest() {

        MemberJoinRequest request = new MemberJoinRequest();
        ReflectionTestUtils.setField(request, "name", "홍길동");
        ReflectionTestUtils.setField(request, "email", "test@example.com");
        ReflectionTestUtils.setField(request, "password", "test");
        ReflectionTestUtils.setField(request, "passwordConfirm", "test");
        ReflectionTestUtils.setField(request, "phone", "1");   // telno → phone

        return request;
    }

    public static MemberJoinRequest createDuplicatedEmailMemberJoinRequest() {

        MemberJoinRequest request = new MemberJoinRequest();
        ReflectionTestUtils.setField(request, "name", "홍길동중복");
        ReflectionTestUtils.setField(request, "email", "test@example.com");
        ReflectionTestUtils.setField(request, "password", "test");
        ReflectionTestUtils.setField(request, "passwordConfirm", "test");
        ReflectionTestUtils.setField(request, "phone", "2");   // telno → phone

        return request;
    }
}
