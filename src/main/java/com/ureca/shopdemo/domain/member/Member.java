package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private String email;

    private String telno;

    private MemberStatus status;

    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private boolean isBlocked = false;

    private LocalDateTime blockedAt;

    public void withdraw() {
        this.status = MemberStatus.WITHDRAW;
    }
}
