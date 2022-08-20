package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequestDto {
    private String roomName;

    public Room toEntity() {
        return Room.builder()
                .roomName(roomName)
                .build();
    }
}
