package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.MemberStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank
    private String name;

    private String password;

    private String passwordConfirm;

    private String email;

    private String phone;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .name(this.name)
                .password(encodedPassword)
                .email(this.email)
                .phone(this.phone)
                .status(MemberStatus.ACTIVE)
                .build();
    }
}
