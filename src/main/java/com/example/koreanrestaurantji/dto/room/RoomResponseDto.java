package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomResponseDto {
    private long roomNumber;
    private List<RoomStatusResponseDto> roomStatus;

    public RoomResponseDto(Room room, List<RoomStatus> status){
        this.roomNumber = room.getRoomNumber();
        RoomStatusResponseDto roomStatusResponseDto;
        for(RoomStatus roomStatus : status) {
            roomStatusResponseDto = RoomStatusResponseDto.builder()
                    .reservationDate(roomStatus.getReservationDate())
                    .reservationTime(roomStatus.getReservationTime())
                    .roomRemaining(roomStatus.getRoomRemaining())
                    .build();

            System.out.println(roomStatusResponseDto);
            this.roomStatus.add(roomStatusResponseDto);
        }
    }
}
