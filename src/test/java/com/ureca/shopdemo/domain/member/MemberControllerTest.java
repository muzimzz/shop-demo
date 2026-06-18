package com.ureca.shopdemo.domain.member;

import com.jayway.jsonpath.JsonPath;
import com.ureca.shopdemo.domain.member.dto.MemberJoinRequest;
import com.ureca.shopdemo.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
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
@WithMockUser
// @WebMvcTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원가입_회원탈퇴_플로우() throws Exception {
        // 회원가입
        MemberJoinRequest memberJoinRequest = MemberDummy.createMemberJoinRequest();
        String jsonContent = objectMapper.writeValueAsString(memberJoinRequest);

        String responseBody = mockMvc.perform(post("/member/join")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonContent))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

        Long generatedMemberId = JsonPath.parse(responseBody).read("$.id", Long.class);

        // 이메일 중복으로 회원가입 실패
        memberJoinRequest = MemberDummy.createDuplicatedEmailMemberJoinRequest();
        jsonContent = objectMapper.writeValueAsString(memberJoinRequest);

        mockMvc.perform(post("/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.DUPLICATE_EMAIL.getMessage()));

        // Todo: 시큐리티 문제로 401: ??
//        // 비밀번호 불일치로 회원탈퇴 실패
//        mockMvc.perform(delete("/member/withdraw")
//                        .param("password", "test!!")
//                        .param("memberId", String.valueOf(generatedMemberId)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PASSWORD.getMessage()));
//
//        mockMvc.perform(delete("/member/withdraw")
//                        .param("password", "test")
//                        .param("memberId", "1"))
//                .andExpect(status().isNoContent());
    }
}