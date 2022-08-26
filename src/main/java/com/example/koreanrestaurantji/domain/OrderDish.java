package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "주문 상세 정보", description = "해당 주문의 음식 정보를 가진 Class")
@Entity(name = "ORDER_DISH")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDER_DISH")
public class OrderDish {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_dish_Number")
    private Long orderDishNumber;

    @ApiModelProperty(value = "주문 일련번호")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Orders.class)
    @JoinColumn(name = "order_number", foreignKey = @ForeignKey(name = "FK_order_dish"))
    private Orders orders;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "dish_number", referencedColumnName = "dish_number")
    private Dish dish;

    @ApiModelProperty(value = "주문 수량")
    @Column(name = "order_quantity", nullable = false)
    @ColumnDefault("1")
    private int orderQuantity;

    @ApiModelProperty(value = "총액")
    @Column(name = "order_dish_price", nullable = false)
    private int orderDishPrice;

    public OrderDish(Orders orders, Dish dish, int orderQuantity) {
        this.orders = orders;
        this.dish = dish;
        this.orderQuantity = orderQuantity;
        this.orderDishPrice = dish.getDishPrice() * orderQuantity;
    }
}
