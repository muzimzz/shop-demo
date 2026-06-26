package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.address.MemberAddress;
import com.ureca.shopdemo.domain.member.address.MemberAddressRepository;
import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.domain.member.dto.MemberResponse;
import com.ureca.shopdemo.global.exception.ErrorCode;
import com.ureca.shopdemo.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 회원가입
    public MemberResponse join(MemberJoinRequest request) {

        // 이메일, 비밀번호, 전화번호 패턴/공백 검증 valid에서

        // 이메일 중복 검증
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new GeneralException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 일치 검증
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new GeneralException(ErrorCode.INVALID_PASSWORD_CONFIRM);
        }

        // 이메일/휴대폰 본인인증

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member savedMember = memberRepository.save(request.toEntity(encodedPassword));

        return MemberResponse.toDto(savedMember);
    }

    // 회원 탈퇴
    public void withdraw(String password, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getStatus() == MemberStatus.WITHDRAW) {
            throw new GeneralException(ErrorCode.ALREADY_WITHDRAWN);
        }

        // if (!passwordEncoder.encode(password).equals(member.getPassword())) {
        // 같은 문자열을 BCryptEncoding해도 다른 값이 나온다 --> matches() 사용
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new GeneralException(ErrorCode.INVALID_PASSWORD);
        }

        member.withdraw();
    }

    // ==================== Admin ====================

    // 회원 목록 조회 (페이징)
    public Page<MemberResponse> getMembers(Pageable pageable) {

        return memberRepository.findAll(pageable)
                .map(MemberResponse::toDto);
    }

    // 회원 상세 조회
    public MemberResponse getMember(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        return MemberResponse.toDto(member);
    }

    // 회원 차단
    public void blockMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        member.block();
    }

    // 회원 차단 해제
    public void unblockMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        member.unblock();
    }

    // 강제 탈퇴
    public void forceWithdraw(Long memberId) {}
}
