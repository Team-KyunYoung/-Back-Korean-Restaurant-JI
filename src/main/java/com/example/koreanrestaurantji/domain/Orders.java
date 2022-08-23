package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "주문 정보", description = "주문 정보를 가진 Class")
@Entity(name = "ORDERS")
@Getter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS")
public class Orders {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Long orderNumber;

    @ApiModelProperty(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", foreignKey = @ForeignKey(name = "FK_user_order"))
    private User user;

    // 날짜-시간 설정 : https://dev-coco.tistory.com/117
    @ApiModelProperty(value = "주문 시간")
    @Column(name = "order_time", nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, targetEntity=OrderDish.class)
    private List<OrderDish> orderDishs = new ArrayList<>();

    @ApiModelProperty(value = "주문 상태")
    @Column(name = "order_status", nullable = false)
    @ColumnDefault("'주문완료'")
    private String orderStatus;

    @Builder
    public Orders(User user) {
        this.user = user;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
