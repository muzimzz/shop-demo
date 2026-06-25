package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private boolean marketingAgreed = false;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(nullable = false)
    private boolean isBlocked = false;

    private LocalDateTime blockedAt;

    private LocalDateTime deletedAt;    // 소프트딜리트 (탈퇴 시 세팅)

    public void withdraw() {
        this.status = MemberStatus.WITHDRAW;
        this.deletedAt = LocalDateTime.now();
    }
}
