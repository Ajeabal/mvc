package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ReplyModifyRequestDTO {

    @NotNull
    private Long rno; // 수정할 댓글 번호
    @NotBlank
    private String text; // 수정할 댓글 내용
    @NotNull
    private Long bno; // 수정 이후 수정된 목록을 조회하기 위해 받음

    // dto를 entity로 변경
    public Reply toEntity() {
        return Reply.builder()
                .replyNo(rno)
                .replyText(text)
                .boardNo(bno)
                .build();
    }
}
