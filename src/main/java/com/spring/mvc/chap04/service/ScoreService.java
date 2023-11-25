package com.spring.mvc.chap04.service;

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.dto.ScoreResponseDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
    컨트롤러와  레파지토리에 사이에 위치하여
    중간 로직을 처리하는 역할
    컨트롤러 -> 서비스 -> 레파지토리
*/

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository repository;

    // 목록 조회 중간처리
    /*
        컨트롤러는 데이터베이스에서 성적정보 리스트를 조회 해 오기를 원하는데
        데이터베이스는 민감한 정보와 컬럼명까지 그대로 노출하기 때문에
        이 모든 것을 숨기는 처리를 하려 한다.
     */
    public List<ScoreResponseDTO> getList(String sort) {
        return repository.findAll(sort)
                .stream()
                .map(ScoreResponseDTO::new)
                .collect(Collectors.toList());

    }

    // 성적입력 중간 처리
    public boolean insertScore(ScoreRequestDTO dto) {
        return repository.save(new Score(dto));
    }

    // 삭제 중간 처리
    public boolean deleteScore(int stuNum) {
        return repository.delete(stuNum);
    }

    // 개별 상세 조회 중간 처리
    public Score retrieve(int stuNum) {
        return repository.findOne(stuNum);
    }

    // 개별 수정 중간 처리
    public Score editScore(int stuNum, ScoreRequestDTO score) {
        Score editScore = new Score(score, stuNum);
        repository.edit(editScore, stuNum);
        return editScore;
    }

}
