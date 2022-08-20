package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomNameResponseDto {
    private final String roomName;

    public RoomNameResponseDto(Room room) {
        this.roomName = room.getRoomName();
    }
}
