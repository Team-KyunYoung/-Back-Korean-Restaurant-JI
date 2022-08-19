package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "음식 정보", description = "음식 이름, 가격, 사진 등의 정보를 가진 Class")
@Entity(name = "DISH")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DISH")
public class Dish {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_number")
    private Long dishNumber;

    @ApiModelProperty(value = "음식 이름")
    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @ApiModelProperty(value = "음식 이미지")
    @Column(name = "dish_photo", nullable = false)
    private String dishPhoto;

    @ApiModelProperty(value = "음식 가격")
    @Column(name = "dish_price", nullable = false)
    @ColumnDefault("0")
    private int dishPrice;

    @ApiModelProperty(value = "음식 카테고리")
    @Column(name = "dish_category", nullable = false)
    private String dishCategory;


    @ApiModelProperty(value = "음식 상세정보")
    @Column(name = "dish_description")
    private String dishDescription;

    @Builder
    public Dish(long dishNumber, String dishName) {
        this.dishNumber = dishNumber;
        this.dishName = dishName;
    }
    @Builder
    public Dish(String dishName, String dishPhoto, int dishPrice, String dishCategory , String dishDescription) {
        this.dishName = dishName;
        this.dishPhoto = dishPhoto;
        this.dishPrice = dishPrice;
        this.dishCategory = dishCategory;
        this.dishDescription = dishDescription;
    }
}
