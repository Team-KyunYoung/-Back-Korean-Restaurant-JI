package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.dish.DishCreateRequestDto;
import com.example.koreanrestaurantji.dto.dish.DishResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    public SuccessResponseDto create(DishCreateRequestDto dishCreateRequestDto) {
        try {
            dishRepository.save(dishCreateRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DISH);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<DishResponseDto> findAllDish() {
        return dishRepository.findAll()
                .stream()
                .map(DishResponseDto::new)
                .collect(Collectors.toList());
    }

    public DishResponseDto findDish(Long number) {
        Dish dish = dishRepository.findByDishNumber(number).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        return new DishResponseDto(dish);
    }

    public SuccessResponseDto delete(Long number){
        Dish dish = dishRepository.findByDishNumber(number).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        dishRepository.delete(dish);

        return new SuccessResponseDto(HttpStatus.OK);
    }
}
