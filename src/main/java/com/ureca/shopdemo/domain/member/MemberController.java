package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponse> join(@RequestBody MemberJoinRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.join(request));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestParam String password,
                                         @RequestParam Long memberId) {
        memberService.withdraw(password, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
