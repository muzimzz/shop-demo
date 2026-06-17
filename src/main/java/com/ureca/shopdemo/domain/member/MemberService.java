package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.domain.member.dto.MemberResponse;
import com.ureca.shopdemo.global.exception.ErrorCode;
import com.ureca.shopdemo.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberAddressRepository memberAddressRepository;

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

        if (request.getRoadAddress() != null) {
            MemberAddress address = MemberAddress.builder()
                    .member(savedMember)
                    .roadAddress(request.getRoadAddress())
                    .detailAddress(request.getRoadAddress())
                    .isDefault(true)
                    .build();

            memberAddressRepository.save(address);
        }



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
}
