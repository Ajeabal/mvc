package com.spring.mvc.chap04.entity;

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import lombok.*;

/*
    엔터티 클래스
     - 데이터베이스에 저장할 데이터를 자바 클래스에 매칭하는 개념
*/
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    private String name; // 학생 이름
    private int kor, eng, math; // 국영수 점수

    private int stuNum; // 학번
    private int total; // 총점
    private double average; // 평균
    private Grade grade;

    public Score(ScoreRequestDTO score) {
        convertInputData(score);
        calculateTotalAndAverage();
        makeGrade();
    }

    public Score(ScoreRequestDTO score, int stuNum) {
        convertInputData(score, stuNum);
        calculateTotalAndAverage();
        makeGrade();
    }

    private void makeGrade() {
        if (average > 90) {
            this.grade = Grade.A;
        } else if (average > 80) {
            this.grade = Grade.B;
        } else if (average > 70) {
            this.grade = Grade.C;
        } else if (average > 60) {
            this.grade = Grade.D;
        } else {
            this.grade = Grade.F;
        }
    }

    private void calculateTotalAndAverage() {
        this.total = kor + eng + math;
        this.average = total / 3.0;
    }

    private void convertInputData(ScoreRequestDTO score) {
        this.name = score.getName();
        this.kor = score.getKor();
        this.eng = score.getEng();
        this.math = score.getMath();
    }
    private void convertInputData(ScoreRequestDTO score, int stuNum) {
        this.stuNum = stuNum;
        this.name = score.getName();
        this.kor = score.getKor();
        this.eng = score.getEng();
        this.math = score.getMath();
    }
}
