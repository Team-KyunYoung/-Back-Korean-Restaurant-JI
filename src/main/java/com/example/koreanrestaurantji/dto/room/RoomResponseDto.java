package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RoomResponseDto {
    private long roomNumber;
    private List<RoomStatusResponseDto> roomStatus;

    public RoomResponseDto(Room room, List<RoomStatus> status){
        this.roomNumber = room.getRoomNumber();
        List<RoomStatusResponseDto> statusList = new ArrayList<>();
        RoomStatusResponseDto roomStatusResponseDto;
        for(RoomStatus roomStatus : status) {
            roomStatusResponseDto = RoomStatusResponseDto.builder()
                    .reservationDate(roomStatus.getReservationDate())
                    .reservationTime(roomStatus.getReservationTime())
                    .roomRemaining(roomStatus.getRoomRemaining())
                    .build();

            statusList.add(roomStatusResponseDto);
        }
        this.roomStatus = statusList;
    }
}
