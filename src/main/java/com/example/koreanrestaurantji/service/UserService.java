package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.*;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.user.*;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.*;
import com.example.koreanrestaurantji.util.JwtTokenProvider;
import com.example.koreanrestaurantji.util.SendEmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final SendEmailUtil sendEmailUtil;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;

    public Long signUp(UserSignupRequestDto userSignupRequestDto) throws BaseException {
        userSignupRequestDto.setUserPassword(passwordEncoder.encode(userSignupRequestDto.getUserPassword()));

        Long userNumber;
        try {
            userNumber = userRepository.save(userSignupRequestDto.toEntity()).getUserNumber();
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_USER);
        }
        return userNumber;
    }

    public SuccessResponseDto nicknameCheck(String nickname) {
        boolean exitsUserCheck = userRepository.existsByUserNickname(nickname).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));

        if (exitsUserCheck) {
            throw new BaseException(BaseResponseCode.DUPLICATE_NICKNAME);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public String signupEmailAuth(String userEmail) {
        boolean exitsUserCheck = userRepository.existsByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.BAD_REQUEST));

        if (exitsUserCheck) {
            throw new BaseException(BaseResponseCode.DUPLICATE_EMAIL);
        }

        String authCode = "";
        try {
            authCode = sendEmailUtil.sendSimpleMessage(userEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authCode;
    }

    public User findUserByEmail(String userEmail){
        return userRepository.findByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        User user = findUserByEmail(userLoginRequestDto.getUserEmail());
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getUserPassword()))
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);

        String token = jwtTokenProvider.createToken(userLoginRequestDto.getUserEmail());
        return new UserLoginResponseDto(HttpStatus.OK, token);
    }

    public User findUserByToken(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    public List<UserAdminResponseDto> findAll() {
        if (findUserByToken().isRole()) {
            return userRepository.findAll()
                    .stream()
                    .map(UserAdminResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            throw new BaseException(BaseResponseCode.BAD_REQUEST);
        }
    }

    public UserResponseDto findByUser() {
        return new UserResponseDto(findUserByToken());
    }

    public String findEmailAuth(String userEmail) {
        boolean exitsUserCheck = userRepository.existsByUserEmail(userEmail).orElseThrow(() -> new BaseException(BaseResponseCode.BAD_REQUEST));

        if (!exitsUserCheck) {
            throw new BaseException(BaseResponseCode.DUPLICATE_EMAIL);
        }

        String authCode = "";
        try {
            authCode = sendEmailUtil.sendSimpleMessage(userEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authCode;
    }

    public SuccessResponseDto updateNickname(UserUpdateNameRequestDto userUpdateNameDto) {
        User user = findUserByToken();

        user.setUserNickname(userUpdateNameDto.getUserNickname());
        userRepository.save(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto findUpdatePassword(UserFindUpdatePwdRequestDto userFindUpdatePwdRequestDto) {
        User user = findUserByEmail(userFindUpdatePwdRequestDto.getUserEmail());

        user.setUserPassword(passwordEncoder.encode(userFindUpdatePwdRequestDto.getUserPassword()));
        userRepository.save(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto verifyPassword(UserUpdatePwdRequestDto userUpdatePwdDto) {
        User user = findUserByToken();

        if (!passwordEncoder.matches(userUpdatePwdDto.getUserPassword(), user.getUserPassword()))
            throw new BaseException(BaseResponseCode.INVALID_PASSWORD);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto updatePassword(UserUpdatePwdRequestDto userUpdatePwdDto) {
        User user = findUserByToken();

        user.setUserPassword(passwordEncoder.encode(userUpdatePwdDto.getUserPassword()));
        userRepository.save(user);

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public int headCountToTableCount(String reservationHeadCount) {
        if(reservationHeadCount.equals("2~4인")) {
            return 1;
        } else if(reservationHeadCount.equals("5~8인")) {
            return 2;
        } else if(reservationHeadCount.equals("9~12인")) {
            return 3;
        } else {
            throw new BaseException(BaseResponseCode.BAD_REQUEST);
        }
    }

    public SuccessResponseDto deleteAllUserData(User user){
        List<Cart> cartList = cartRepository.findByUser(user);
        List<Orders> orderList = orderRepository.findByUser(user);
        List<Reservation> reservList = reservationRepository.findByUser(user);
        List<QuestionBoard> questionBoardList = questionRepository.findByUser(user);

        try {
            if(cartList.size() != 0) {
                for (Cart cart : cartList) { cartRepository.delete(cart); }
            }
            if(orderList.size() != 0) {
                for (Orders order : orderList) {
                    for(OrderDish orderDish : orderDishRepository.findByOrders(order)) {
                        orderDishRepository.delete(orderDish);
                    }
                    orderRepository.delete(order);
                }
            }
            if(reservList.size() != 0) {
                for (Reservation reservation : reservList) {
                    Room room = roomRepository.findByRoomName(reservation.getReservationRoomName()).orElseThrow(() -> new BaseException(BaseResponseCode.ROOM_NOT_FOUND));
                    RoomStatus roomStatus = roomStatusRepository.findByRoomAndReservationDateAndReservationTime(room, reservation.getReservationDate(), reservation.getReservationTime());

                    int roomRemaining = roomStatus.getRoomRemaining() + headCountToTableCount(reservation.getReservationHeadCount());
                    if(roomRemaining >= 15){
                        roomStatusRepository.delete(roomStatus);
                    } else {
                        roomStatus.setRoomRemaining(roomRemaining);
                        try {
                            roomStatusRepository.save(roomStatus);
                        } catch (Exception e) {
                            throw new BaseException(BaseResponseCode.BAD_REQUEST);
                        }
                    }
                    reservationRepository.delete(reservation);
                }
            }
            if(questionBoardList.size() != 0) {
                for (QuestionBoard questionBoard : questionBoardList) {
                    List<Comment> commentList = commentRepository.findByQuestionBoard(questionBoard);
                    if(commentList.size() != 0) {
                        for (Comment comment : commentList) {
                            commentRepository.delete(comment);
                        }
                    }
                    questionRepository.delete(questionBoard);
                }
            }
            userRepository.delete(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto deleteUser(){
        User user = findUserByToken();
        return deleteAllUserData(user);
    }

    public SuccessResponseDto deleteUserByNumber(Long userNumber){
        User user = userRepository.findByUserNumber(userNumber).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        return deleteAllUserData(user);
    }

    public boolean userAdminCheck(){
        return findUserByToken().isRole();
    }
}
