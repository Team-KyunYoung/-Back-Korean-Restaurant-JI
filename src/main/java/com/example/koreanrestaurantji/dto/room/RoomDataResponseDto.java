package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomDataResponseDto {
    private final long roomNumber;
    private final String roomName;
    private final String roomImg;

    public RoomDataResponseDto(Room room) {
        this.roomNumber = room.getRoomNumber();
        this.roomName = room.getRoomName();
        this.roomImg = room.getRoomImg();
    }
}
