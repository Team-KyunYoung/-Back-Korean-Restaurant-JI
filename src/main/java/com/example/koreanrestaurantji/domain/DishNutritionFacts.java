package com.example.koreanrestaurantji.domain;

import com.example.koreanrestaurantji.dto.dish.DishNutritionFactsRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "음식 정보", description = "음식 이름, 가격, 사진 등의 정보를 가진 Class")
@Entity(name = "Dish_Nutrion_Facts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Dish_Nutrion_Facts")
public class DishNutritionFacts {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_nutrion_facts")
    private Long dishNutrionFactsNumber;

    @ApiModelProperty(value = "음식 일련번호")
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Dish.class)
    @JoinColumn(name = "dish_number", foreignKey = @ForeignKey(name = "FK_dish_nutrition_facts"))
    private Dish dish;

    @ApiModelProperty(value = "총 용량")
    @Column(name = "dish_serving_size", nullable = false)
    @ColumnDefault("0")
    private int dishServingSize;

    @ApiModelProperty(value = "칼로리")
    @Column(name = "dish_calroies", nullable = false)
    @ColumnDefault("0")
    private int dishCalroies;

    @ApiModelProperty(value = "탄수화물")
    @Column(name = "dish_carbohydrate", nullable = false)
    @ColumnDefault("0")
    private int dishCarbohydrate;

    @ApiModelProperty(value = "당류")
    @Column(name = "dish_sugars", nullable = false)
    @ColumnDefault("0")
    private int dishSugars;

    @ApiModelProperty(value = "단백질")
    @Column(name = "dish_protein", nullable = false)
    @ColumnDefault("0")
    private int dishProtein;

    @ApiModelProperty(value = "지방")
    @Column(name = "dish_fat", nullable = false)
    @ColumnDefault("0")
    private int dishFat;

    @ApiModelProperty(value = "트랜스 지방")
    @Column(name = "dish_trans_fat", nullable = false)
    @ColumnDefault("0")
    private int dishTransFat;

    @ApiModelProperty(value = "콜레스테롤")
    @Column(name = "dish_cholesterol", nullable = false)
    @ColumnDefault("0")
    private int dishCholesterol;

    @ApiModelProperty(value = "나트륨")
    @Column(name = "dish_sodium", nullable = false)
    @ColumnDefault("0")
    private int dishSodium;

    @Builder
    public DishNutritionFacts(Dish dish, DishNutritionFactsRequestDto dishNutritionFactsRequestDto) {
        this.dish = dish;
        this.dishServingSize = dishNutritionFactsRequestDto.getDishServingSize();
        this.dishCalroies = dishNutritionFactsRequestDto.getDishCalroies();
        this.dishCarbohydrate = dishNutritionFactsRequestDto.getDishCarbohydrate();
        this.dishSugars = dishNutritionFactsRequestDto.getDishSugars();
        this.dishProtein = dishNutritionFactsRequestDto.getDishProtein();
        this.dishFat = dishNutritionFactsRequestDto.getDishFat();
        this.dishTransFat = dishNutritionFactsRequestDto.getDishTransFat();
        this.dishCholesterol = dishNutritionFactsRequestDto.getDishCholesterol();
        this.dishSodium = dishNutritionFactsRequestDto.getDishSodium();
    }
}
