package com.ureca.shopdemo.domain.member.dto;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.MemberStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(unique = true)
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
