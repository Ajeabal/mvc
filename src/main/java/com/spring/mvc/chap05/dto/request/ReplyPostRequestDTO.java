package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReplyPostRequestDTO {


    @NotBlank
    @Size(min = 1, max = 300)
    private String text;

    @NotBlank
    @Size(min = 2, max = 8)
    private String author;

    @NotNull
    private Long bno;

    // dto를 entity로 바꾸는 변환 메서드
    public Reply toEntity() {
        return Reply.builder()
                .replyText(this.text)
                .replyWriter(this.author)
                .boardNo(this.bno)
                .build();
    }
}
