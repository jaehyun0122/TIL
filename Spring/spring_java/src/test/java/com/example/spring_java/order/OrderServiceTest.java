package com.example.spring_java.order;

import com.example.spring_java.AppConfig;
import com.example.spring_java.Member.Grade;
import com.example.spring_java.Member.Member;
import com.example.spring_java.Member.MemberService;
import com.example.spring_java.Member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder(){

        Long memberId = 1L;
        Member member = new Member(memberId, "testA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "testA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
