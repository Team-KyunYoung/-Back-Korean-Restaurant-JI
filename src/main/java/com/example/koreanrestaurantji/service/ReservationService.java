package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.*;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.reservation.*;
import com.example.koreanrestaurantji.dto.room.RoomStatusRequestDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.ReservationRepository;
import com.example.koreanrestaurantji.repository.RoomRepository;
import com.example.koreanrestaurantji.repository.RoomStatusRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;

    public User findUserByToken(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    public Room findRoomByRoomNumber(long roomNumber){
        return roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new BaseException(BaseResponseCode.ROOM_NOT_FOUND));
    }

    public Reservation findReservationByReservationNumber(long reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber).orElseThrow(() -> new BaseException(BaseResponseCode.RESERVATION_NOT_FOUND));
    }

    public RoomStatus findRoomStatusByStatus(Room room, LocalDate reservationDate, String reservationTime) {
        return roomStatusRepository.findByRoomAndReservationDateAndReservationTime(room, reservationDate, reservationTime).orElseThrow(() -> new BaseException(BaseResponseCode.ROOM_STATUS_NOT_FOUND));
    }

    public SuccessResponseDto create(ReservationRequestDto reservationRequestDto) throws ParseException {
        User user = findUserByToken();
        String roomName = findRoomByRoomNumber(reservationRequestDto.getReservationRoomNumber()).getRoomName();
        ReservationCreateRequestDto reservationCreateRequestDto = new ReservationCreateRequestDto(user, reservationRequestDto, roomName);

        try {
            reservationRepository.save(reservationCreateRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_RESERVATION);
        }

        //예약 데이터 등록 성공 시, 객실 예약 현황 데이터도 수정 필요
        Room room = findRoomByRoomNumber(reservationRequestDto.getReservationRoomNumber());
        LocalDate reservationDate = LocalDate.parse(reservationRequestDto.getReservationDate(), DateTimeFormatter.ISO_DATE);
        String reservationTime = reservationRequestDto.getReservationTime();
        int reservationTableCount = reservationRequestDto.getReservationTableCount();

        //이미 해당 객실-날짜-시간에 예약자가 존재하면, 남은 좌석 수 카운트
        int roomRemainingDefault = 15;
        if(roomStatusRepository.existsByRoomAndReservationDateAndReservationTime(room, reservationDate, reservationTime)){
            RoomStatus roomStatus = findRoomStatusByStatus(room, reservationDate, reservationTime);
            //update(남은 좌석 수 - 예약 좌석 수)
            roomRemainingDefault = roomStatus.getRoomRemaining();
            roomStatus.setRoomRemaining(roomRemainingDefault-reservationTableCount);
            roomStatusRepository.save(roomStatus);
        } else { // 처음 등록된다면, 새 RoomStatus 생성(이때 roomRemaining = default - 예약 좌석 수)
            RoomStatusRequestDto roomStatusRequestDto = RoomStatusRequestDto.builder()
                    .room(room)
                    .reservationDate(reservationDate)
                    .reservationTime(reservationTime)
                    .roomRemaining(roomRemainingDefault-reservationTableCount)
                    .build();
            roomStatusRepository.save(roomStatusRequestDto.toEntity());
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<ReservationResponseDto> findByUserBeforeDate() {
        User user = findUserByToken();
        return reservationRepository.findByUserAndReservationDateIsBeforeOrderByReservationDateDesc(user, LocalDate.now())
                .stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReservationResponseDto> findByUserAfterDate() {
        User user = findUserByToken();
        return reservationRepository.findByUserAndReservationDateIsGreaterThanEqualOrderByReservationDateAsc(user, LocalDate.now())
                .stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    public String tableCountToHeadCount(int reservationTableCount) {
        switch (reservationTableCount) {
            case 1:
                return "2~4인";
            case 2:
                return "5~8인";
            case 3:
                return "9~12인";
            default:
                return "비공개";
        }
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

    public Room findRoomByRoomName(String roomName) {
        return roomRepository.findByRoomName(roomName).orElseThrow(() -> new BaseException(BaseResponseCode.ROOM_NOT_FOUND));
    }

    public SuccessResponseDto update(Long reservationNumber, ReservationUpdateRequestDto reservationUpdateRequestDto){
        //Reservation
        Reservation reservation = findReservationByReservationNumber(reservationNumber);
        LocalDate reservationDate = LocalDate.parse(reservationUpdateRequestDto.getReservationDate(), DateTimeFormatter.ISO_DATE);
        String reservationTime = reservationUpdateRequestDto.getReservationTime();
        int reservationTableCount = reservationUpdateRequestDto.getReservationTableCount();

        //RoomStatus
        Room room = findRoomByRoomName(reservation.getReservationRoomName());
        RoomStatus roomStatus = findRoomStatusByStatus(room, reservation.getReservationDate(), reservation.getReservationTime());

        //RoomStatus 수정
        int roomRemaining = roomStatus.getRoomRemaining() + headCountToTableCount(reservation.getReservationHeadCount());
        if(roomRemaining >= 15){
            roomStatusRepository.delete(roomStatus);
        } else {
            roomStatus.setRoomRemaining(roomRemaining);
        }

        try {
            roomStatusRepository.save(roomStatus);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ROOM_STATUS);
        }

        //Reservation 수정
        reservation.setReservationRoomName(reservationUpdateRequestDto.getReservationRoomName());
        reservation.setReservationName(reservationUpdateRequestDto.getReservationName());
        reservation.setReservationPhoneNumber(reservationUpdateRequestDto.getReservationPhoneNumber());
        reservation.setReservationDate(reservationDate);
        reservation.setReservationTime(reservationTime);
        reservation.setReservationHeadCount(tableCountToHeadCount(reservationTableCount));

        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_RESERVATION);
        }

        //Reservation 수정에 따른 RoomStatus 수정
        //이미 해당 객실-날짜-시간에 예약자가 존재하면, 남은 좌석 수 카운트
        int roomRemainingDefault = 15;
        room = findRoomByRoomName(reservationUpdateRequestDto.getReservationRoomName());
        if(roomStatusRepository.existsByRoomAndReservationDateAndReservationTime(room, reservationDate, reservationTime)){
            roomStatus = findRoomStatusByStatus(room, reservationDate, reservationTime);
            //update(남은 좌석 수 - 예약 좌석 수)
            roomRemainingDefault = roomStatus.getRoomRemaining();
            roomStatus.setRoomRemaining(roomRemainingDefault-reservationTableCount);
            roomStatusRepository.save(roomStatus);
        } else { // 처음 등록된다면, 새 RoomStatus 생성(이때 roomRemaining = default - 예약 좌석 수)
            RoomStatusRequestDto roomStatusRequestDto = RoomStatusRequestDto.builder()
                    .room(room)
                    .reservationDate(reservationDate)
                    .reservationTime(reservationTime)
                    .roomRemaining(roomRemainingDefault-reservationTableCount)
                    .build();
            roomStatusRepository.save(roomStatusRequestDto.toEntity());
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(Long reservationNumber){
        Reservation reservation = findReservationByReservationNumber(reservationNumber);
        reservationRepository.delete(reservation);

        //RoomStatus 수정
        Room room = findRoomByRoomName(reservation.getReservationRoomName());
        RoomStatus roomStatus = findRoomStatusByStatus(room, reservation.getReservationDate(), reservation.getReservationTime());

        int roomRemaining = roomStatus.getRoomRemaining() + headCountToTableCount(reservation.getReservationHeadCount());
        if(roomRemaining >= 15){
            roomStatusRepository.delete(roomStatus);
        } else {
            roomStatus.setRoomRemaining(roomRemaining);
        }

        try {
            roomStatusRepository.save(roomStatus);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.BAD_REQUEST);
        }

       return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto deleteBeforeDate(){
        if(findUserByToken().isRole()) {
            LocalDate limitDate = LocalDate.now().minusMonths(6);

            try {
                reservationRepository.deleteAllByReservationDateBefore(limitDate);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }
}
