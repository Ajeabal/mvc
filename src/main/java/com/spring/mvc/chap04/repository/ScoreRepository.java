package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;

import java.util.List;

/*
    성적정보를 저장, 조회, 수정, 삭제하는 역할
    데이터베이스와 같은 저장소에 접근하는 객체(Data Access Object :DAO)
    저장소라는 개념은 구체적이지 않아서 인터페이스로 추상화한다.
     ex) 파일, 인메모리, (관계형)DB, NOSQL 같이 다양한 것을 사용해서 저장소라는게 바뀔 수 있음.
 */
public interface ScoreRepository {

    // 성적 정보 전체 목록 조회
    List<Score> findAll();

    // 성적 정보 등록
    boolean save(Score score);

    // 성적 정보 삭제 - 1개 삭제
    boolean delete(int stuNum);

    // 성적 정보 개별 조회
    Score findOne(int stuNum);

}
