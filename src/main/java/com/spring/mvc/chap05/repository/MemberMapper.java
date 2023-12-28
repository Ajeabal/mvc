package com.spring.mvc.chap05.repository;

import com.spring.mvc.chap05.dto.request.AutoLoginDTO;
import com.spring.mvc.chap05.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    // 회원 가입
    boolean save(Member member);

    // 회원 정보 단일 조회
    Member findMember(String account);

    /*
        중복체크(account, email)기능
        @param type - 중복을 검사할 내용 (account, email)
        @param keyword - 중복을 검사할 입력값 (ex: abc@gmail.com)
        @return 중복이면 true, 중복이 안니면 false
     */
    // 중복체크 (account, email) 기능
    boolean isDuplicate(@Param("type") String type,@Param("keyword") String keyword);

    // 자동 로그인 세션 키, 만료시간 업데이트
    void saveAutoLogin(AutoLoginDTO dto);

    // 쿠키값(세션아이디)으로 회원 정보를 조회
    Member findMemberByCookie(String sessionId);
}
