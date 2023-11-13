package kr.co.kwonshzzang.largescalemysql.largescalemysql.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.MemberNicknameHistoryDto;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.dto.RegisterMemberCommand;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service.MemberReadService;
import kr.co.kwonshzzang.largescalemysql.largescalemysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        return memberWriteService.register(command);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable(name ="id") Long id) {
        System.out.println("id " + id);
        return memberReadService.getMember(id);
    }

    @PostMapping("/members/{id}/nickname")
    public MemberDto changeNickname(@PathVariable(name = "id") Long id, @RequestBody String nickname) {
         memberWriteService.changeNickname(id, nickname);
         return memberReadService.getMember(id);
    }

    @GetMapping("/members/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable(name = "memberId") Long memberId) {
        return memberReadService.getNicknameHistories(memberId);
    }
}
