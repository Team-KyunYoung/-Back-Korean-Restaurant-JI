package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "장바구니 정보", description = "장바구니 정보를 가진 Class")
@Entity(name = "CART")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CART")
public class Cart {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_Number")
    private Long cartNumber;

    @ApiModelProperty(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", foreignKey = @ForeignKey(name = "FK_user_cart"))
    private User user;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "dish_number", referencedColumnName = "dish_number")
    private Dish dish;

    @ApiModelProperty(value = "주문 수량")
    @Column(name = "cart_quantity", nullable = false)
    @ColumnDefault("1")
    private int cartQuantity;

    @ApiModelProperty(value = "총액")
    @Column(name = "cart_dish_price", nullable = false)
    private int cartDishPrice;

    @Builder
    public Cart(User user, Dish dish, int cartQuantity, int cartDishPrice) {
        this.user = user;
        this.dish = dish;
        this.cartQuantity = cartQuantity;
        this.cartDishPrice = cartDishPrice;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public void setCartDishPrice(int cartDishPrice) {
        this.cartDishPrice = cartDishPrice;
    }
}
