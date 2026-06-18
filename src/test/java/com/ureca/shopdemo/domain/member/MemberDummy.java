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
                .build();
    }

    public static Member createExpiredMockMember() {
        return Member.builder()
                .id(1L)
                .name("홍길동")
                .email("test@example.com")
                .expiredAt(LocalDateTime.of(2000, 1, 1, 1, 1))
                .build();
    }

    public static MemberJoinRequest createMemberJoinRequest() {

        MemberJoinRequest request = new MemberJoinRequest();
        ReflectionTestUtils.setField(request, "name", "홍길동");
        ReflectionTestUtils.setField(request, "email", "test@example.com");
        ReflectionTestUtils.setField(request, "password", "test");
        ReflectionTestUtils.setField(request, "passwordConfirm", "test");
        ReflectionTestUtils.setField(request, "telno", "1");

        return request;
    }

    public static MemberJoinRequest createDuplicatedEmailMemberJoinRequest() {

        MemberJoinRequest request = new MemberJoinRequest();
        ReflectionTestUtils.setField(request, "name", "홍길동중복");
        ReflectionTestUtils.setField(request, "email", "test@example.com");
        ReflectionTestUtils.setField(request, "password", "test");
        ReflectionTestUtils.setField(request, "passwordConfirm", "test");
        ReflectionTestUtils.setField(request, "telno", "2");

        return request;
    }

    public static MemberJoinRequest createInvalidPasswordConfirmMemberJoinRequest() {

        MemberJoinRequest request = new MemberJoinRequest();
        ReflectionTestUtils.setField(request, "name", "홍길동비밀번호불일치");
        ReflectionTestUtils.setField(request, "email", "test@example.com");
        ReflectionTestUtils.setField(request, "password", "test");
        ReflectionTestUtils.setField(request, "passwordConfirm", "test!!");
        ReflectionTestUtils.setField(request, "telno", "3");

        return request;
    }
}
