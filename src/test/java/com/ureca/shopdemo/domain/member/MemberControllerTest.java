package com.ureca.shopdemo.domain.member;

import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockCustomUser  // memberId=1L 고정, 모든 테스트에 적용
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ====== 회원가입 ======

    @Test
    void 회원가입_성공() throws Exception {
        mockMvc.perform(post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MemberDummy.createMemberJoinRequest())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void 회원가입_이메일중복_실패() throws Exception {
        // 먼저 한 명 가입
        mockMvc.perform(post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MemberDummy.createMemberJoinRequest())))
                .andExpect(status().isCreated());

        // 같은 이메일로 재가입 시도
        mockMvc.perform(post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MemberDummy.createDuplicatedEmailMemberJoinRequest())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.DUPLICATE_EMAIL.getMessage()));
    }

    // ====== 회원탈퇴 ======

    @Test
    void 회원탈퇴_성공() throws Exception {
        // 가입 → H2 auto_increment로 memberId=1L 생성
        mockMvc.perform(post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MemberDummy.createMemberJoinRequest())))
                .andExpect(status().isCreated());

        // @WithMockCustomUser의 memberId=1L과 일치 → .with() 불필요
        mockMvc.perform(delete("/member/withdraw")
                        .param("password", "test"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void 회원탈퇴_비밀번호불일치_실패() throws Exception {
        // 가입
        mockMvc.perform(post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MemberDummy.createMemberJoinRequest())))
                .andExpect(status().isCreated());

        // 틀린 비밀번호로 탈퇴 시도
        mockMvc.perform(delete("/member/withdraw")
                        .param("password", "test!!"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PASSWORD.getMessage()));
    }
}
