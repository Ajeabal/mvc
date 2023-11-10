package com.spring.mvc.chap01;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class OrderRequestDTO {

    // 클라이언트가 보내는 파라미터의 이름을 필드로 똑같이 구성
    // setter와 기본 생성자가 반드시 있어야 한다.
    private String orderNum; // 주문 번호
    private String goodsName; // 상품 이름
    private int amount; // 주문 수량
    private int price; // 주문 가격

}
