package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.room.*;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.RoomRepository;
import com.example.koreanrestaurantji.repository.RoomStatusRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;
    private final UserRepository userRepository;

    public User findUserByToken(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    public SuccessResponseDto create(RoomRequestDto roomRequestDto) {
        User user = findUserByToken();
        if(user.isRole()) {
            try {
                roomRepository.save(roomRequestDto.toEntity());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ROOM);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<RoomDataResponseDto> roomAllName() {
        return roomRepository.findAll()
                .stream()
                .map(RoomDataResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<RoomSearchResponseDto> searchRoom(RoomSearchRequestDto roomSearchRequestDto) {
        List<Room> rooms = roomRepository.findByRoomNameContaining(roomSearchRequestDto.getInput());
        if(rooms.isEmpty() || rooms == null){
            throw new BaseException(BaseResponseCode.ROOM_NOT_FOUND);
        } else {
            return rooms.stream().map(RoomSearchResponseDto::new).collect(Collectors.toList());
        }
    }

    public Room findRoomByRoomNumber(long roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new BaseException(BaseResponseCode.ROOM_NOT_FOUND));
    }

    public RoomResponseDto roomAllStatus(long roomNumber) {
        Room room = findRoomByRoomNumber(roomNumber);
        List<RoomStatus> roomStatus = roomStatusRepository.findByRoom(room);
        if(roomStatus.size() == 0)
            throw new BaseException(BaseResponseCode.ROOM_STATUS_NOT_FOUND);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room, roomStatus);

        return roomResponseDto;
    }

    public RoomResponseDto roomStatus(long roomNumber, RoomStatusDateRequestDto roomStatusDateRequestDto) {
        Room room = findRoomByRoomNumber(roomNumber);
        List<RoomStatus> roomStatus = roomStatusRepository.findByRoomAndReservationDate(room,
                roomStatusDateRequestDto.getReservationDate());
//        if(roomStatus.size() == 0)
//            throw new BaseException(BaseResponseCode.ROOM_STATUS_NOT_FOUND);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room, roomStatus);

        return roomResponseDto;
    }

    public String roomRemaining(long roomNumber, RoomStatusTimeRequestDto roomStatusTimeRequestDto) {
        Room room = findRoomByRoomNumber(roomNumber);
        RoomStatus roomStatus = roomStatusRepository.findByRoomAndReservationDateAndReservationTime(room,
                roomStatusTimeRequestDto.getReservationDate(),
                roomStatusTimeRequestDto.getReservationTime());

        String roomRemaining = String.valueOf(15);
        if(roomStatus != null){
            roomRemaining = String.valueOf(roomStatus.getRoomRemaining());
        }

        return roomRemaining;
    }

    public SuccessResponseDto delete(Long roomNumber){
        User user = findUserByToken();
        if(user.isRole()) {
            Room room = findRoomByRoomNumber(roomNumber);
            for(RoomStatus roomStatus : roomStatusRepository.findByRoom(room)){
                roomStatusRepository.delete(roomStatus);
            }
            roomRepository.delete(room);
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto deleteBeforeToday(){
        if(findUserByToken().isRole()) {
            try {
                roomStatusRepository.deleteAllByReservationDateBefore(LocalDate.now());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }
}
