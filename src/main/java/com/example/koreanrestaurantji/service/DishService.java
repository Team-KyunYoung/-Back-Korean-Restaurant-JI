package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.dto.dish.DishCreateRequestDto;
import com.example.koreanrestaurantji.dto.dish.DishResponseDto;
import com.example.koreanrestaurantji.dto.dish.DishSuccessResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    public DishSuccessResponseDto create(DishCreateRequestDto dishCreateRequestDto) {
        try {
            dishRepository.save(dishCreateRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DISH);
        }
        return new DishSuccessResponseDto(HttpStatus.OK);
    }

    public DishResponseDto findDish(Long number) {
        Dish dish = dishRepository.findByDishNumber(number).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        return new DishResponseDto(dish);
    }

    public DishSuccessResponseDto delete(Long number){
        Dish dish = dishRepository.findByDishNumber(number).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        dishRepository.delete(dish);

        return new DishSuccessResponseDto(HttpStatus.OK);
    }
}
