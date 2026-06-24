package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.domain.member.dto.MemberResponse;
import com.ureca.shopdemo.domain.member.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {     // Todo: 로그인 커스텀 구현

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponse> join(@RequestBody MemberJoinRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.join(request));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestParam String password,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.withdraw(password, userDetails.getMember().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
