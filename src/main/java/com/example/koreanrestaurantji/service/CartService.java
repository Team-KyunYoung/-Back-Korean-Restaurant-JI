package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Cart;
import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.dto.cart.CartCreateRequestDto;
import com.example.koreanrestaurantji.dto.cart.CartRequestDto;
import com.example.koreanrestaurantji.dto.cart.CartResponseDto;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.CartRepository;
import com.example.koreanrestaurantji.repository.DishRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public User findUserByToken(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                        .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    public Dish findDishByDishNumber(long dishNumber){
        return dishRepository.findByDishNumber(dishNumber).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
    }

    public SuccessResponseDto create(CartRequestDto cartRequestDto) {
        User user = findUserByToken();
        Dish dish = findDishByDishNumber(cartRequestDto.getDishNumber());

        if(cartRepository.existsByUserAndDish(user, dish)){
            Cart cart = cartRepository.findByUserAndDish(user, dish).orElseThrow(() -> new BaseException(BaseResponseCode.CART_NOT_FOUND));
            cart.setCartQuantity(cart.getCartQuantity() + cartRequestDto.getCartQuantity());
            try {
                cartRepository.save(cart);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_CART);
            }
        } else {
            CartCreateRequestDto cartCreateRequestDto = new CartCreateRequestDto(user, dish, cartRequestDto.getCartQuantity());
            try {
                cartRepository.save(cartCreateRequestDto.toEntity());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_CART);
            }
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<CartResponseDto> findCartByUser() {
        User user = findUserByToken();
        return cartRepository.findByUser(user)
                .stream()
                .map(CartResponseDto::new)
                .collect(Collectors.toList());
    }

    public SuccessResponseDto update(long cartNumber, int cartQuantity){
        Cart cart = cartRepository.findByCartNumber(cartNumber).orElseThrow(() -> new BaseException(BaseResponseCode.CART_NOT_FOUND));

        try {
            cart.setCartQuantity(cartQuantity);
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_CART);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(Long cartNumber){
        Cart cart = cartRepository.findByCartNumber(cartNumber).orElseThrow(() -> new BaseException(BaseResponseCode.CART_NOT_FOUND));

        try {
            cartRepository.delete(cart);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.BAD_REQUEST);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
