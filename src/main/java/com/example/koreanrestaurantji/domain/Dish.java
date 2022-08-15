package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "코스 정보", description = "전식, 본식, 후식 Dish 정보를 가진 Class")
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

    @ApiModelProperty(value = "")
    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @ApiModelProperty(value = "")
    @Column(name = "dish_photo", nullable = false)
    private String dishPhoto;

    @ApiModelProperty(value = "")
    @Column(name = "dish_price", nullable = false)
    @ColumnDefault("0")
    private int dishPrice;

    @ApiModelProperty(value = "상위 카테고리")
    @Column(name = "dish_category_upper", nullable = false)
    private String dishCategoryUpper;

    @ApiModelProperty(value = "하위 카테고리")
    @Column(name = "dish_category_lower")
    private String dishCategoryLower;

    @ApiModelProperty(value = "")
    @Column(name = "dish_description")
    private String dishDescription;

    @Builder
    public Dish(String dishName, String dishPhoto, int dishPrice, String dishCategoryUpper, String dishCategoryLower, String dishDescription) {
        this.dishName = dishName;
        this.dishPhoto = dishPhoto;
        this.dishPrice = dishPrice;
        this.dishCategoryUpper = dishCategoryUpper;
        this.dishCategoryLower = dishCategoryLower;
        this.dishDescription = dishDescription;
    }
}
