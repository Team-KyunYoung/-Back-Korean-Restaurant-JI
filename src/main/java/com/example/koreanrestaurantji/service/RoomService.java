package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import com.example.koreanrestaurantji.dto.room.*;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.RoomRepository;
import com.example.koreanrestaurantji.repository.RoomStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;

    public RoomSuccessResponseDto create(String roomName) {
        RoomRequestDto roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomName(roomName);
        try {
            roomRepository.save(roomRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ROOM);
        }
        return new RoomSuccessResponseDto(HttpStatus.OK);
    }

    public List<RoomNameResponseDto> roomAllName() {
        return roomRepository.findAll()
                .stream()
                .map(RoomNameResponseDto::new)
                .collect(Collectors.toList());
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
        if(roomStatus.size() == 0)
            throw new BaseException(BaseResponseCode.ROOM_STATUS_NOT_FOUND);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room, roomStatus);

        return roomResponseDto;
    }

    public String roomRemaining(long roomNumber, RoomStatusTimeRequestDto roomStatusTimeRequestDto) {
        Room room = findRoomByRoomNumber(roomNumber);
        RoomStatus roomStatus = roomStatusRepository.findByRoomAndReservationDateAndReservationTime(room,
                roomStatusTimeRequestDto.getReservationDate(),
                roomStatusTimeRequestDto.getReservationTime()).orElseThrow(() -> new BaseException(BaseResponseCode.ROOM_STATUS_NOT_FOUND));;

        String roomRemaining = String.valueOf(roomStatus.getRoomRemaining());

        return roomRemaining;
    }

    public RoomSuccessResponseDto delete(Long roomNumber){
        Room room = findRoomByRoomNumber(roomNumber);
        roomRepository.delete(room);

        return new RoomSuccessResponseDto(HttpStatus.OK);
    }
}
