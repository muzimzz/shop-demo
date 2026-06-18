package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.domain.member.dto.MemberResponse;
import com.ureca.shopdemo.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberAddressRepository memberAddressRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원가입_성공() {
        // given
        MemberJoinRequest request = MemberDummy.createMemberJoinRequest();
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(memberRepository.save(any())).willReturn(MemberDummy.createMockMember());

        // when
        MemberResponse response = memberService.join(request);

        // then
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
    }

    @Test
    void 회원가입_실패_이메일중복() {

        // given
        MemberJoinRequest request = MemberDummy.createMemberJoinRequest();
        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> memberService.join(request))
                .hasMessageContaining(ErrorCode.DUPLICATE_EMAIL.getMessage());
    }

    @Test
    void 회원탈퇴_성공() {

        // given
        Long memberId = 1L;
        String password = "test";

        Member member = MemberDummy.createMockMember();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        // when
        memberService.withdraw(password, memberId);

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.WITHDRAW);
    }

    @Test
    void 회원탈퇴_불일치() {

        // given
        Long memberId = 1L;
        String password = "test!";

        Member member = MemberDummy.createMockMember();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        // when, then
        assertThatThrownBy(() -> memberService.withdraw(password, memberId))
                .hasMessageContaining(ErrorCode.INVALID_PASSWORD.getMessage());
    }

}