package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomRequestDto {
    private String roomName;
    private String roomImg;

    public Room toEntity() {
        return Room.builder()
                .roomName(roomName)
                .roomImg(roomImg)
                .build();
    }
}
