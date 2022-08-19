package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@ApiModel(value = "코스 정보", description = "전식, 본식, 후식 Dish 정보를 가진 Class")
@Entity(name = "COURESE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURESE")
public class Course {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_number")
    private Long courseNumber;

    @ApiModelProperty(value = "코스 이름")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "appetizer_dish_number", updatable = false, referencedColumnName = "dish_number")
    private Dish appetizer;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "entree1_dish_number", updatable = false, referencedColumnName = "dish_number")
    private Dish entree1;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "entree2_dish_number", updatable = false, referencedColumnName = "dish_number")
    private Dish entree2;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "entree3_dish_number", updatable = false, referencedColumnName = "dish_number")
    private Dish entree3;

    @ApiModelProperty(value = "음식 일련번호")
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Dish.class)
    @JoinColumn(name = "dessert_dish_number", updatable = false, referencedColumnName = "dish_number")
    private Dish dessert;

    @ApiModelProperty(value = "코스 가격")
    @Column(name = "course_price", nullable = false)
    private int coursePrice;

    @Builder
    public Course(String courseName, Dish appetizer, Dish entree1, Dish entree2, Dish entree3, Dish dessert, int coursePrice) {
        this.courseName = courseName;
        this.appetizer = appetizer;
        this.entree1 = entree1;
        this.entree2 = entree2;
        this.entree3 = entree3;
        this.dessert = dessert;
        this.coursePrice = coursePrice;
    }
}
