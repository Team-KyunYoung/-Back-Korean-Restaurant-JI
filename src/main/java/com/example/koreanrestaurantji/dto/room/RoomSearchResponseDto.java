package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomSearchResponseDto {
        private final long roomNumber;
        private final String roomName;

        public RoomSearchResponseDto(Room room) {
            this.roomNumber = room.getRoomNumber();
            this.roomName = room.getRoomName();
        }
}
