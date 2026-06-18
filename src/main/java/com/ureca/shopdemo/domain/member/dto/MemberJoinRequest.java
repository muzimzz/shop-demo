package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    private String name;

    private String password;

    private String passwordConfirm;

    private String email;

    private String telno;

    private String roadAddress;

    private String detailAddress;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .name(this.name)
                .password(encodedPassword)
                .email(this.email)
                .telno(this.telno)
                .status(MemberStatus.ACTIVE)
                .build();
    }

}
