package com.ureca.shopdemo.domain.member.userdetails;

import com.ureca.shopdemo.domain.member.Member;
import com.ureca.shopdemo.domain.member.MemberStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private final Member member;

    // Todo: admin 구현 후 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;    // expiredAt 제거 - 만료 개념 미사용
    }

    @Override
    public boolean isAccountNonLocked() {
        return !member.isBlocked();
    }

    // 비밀번호 만료 설정
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        // ACTIVE 상태이고 탈퇴하지 않은 회원만 활성화
        return member.getStatus() == MemberStatus.ACTIVE
                && member.getDeletedAt() == null;
    }
}
