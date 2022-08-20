package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "객실 정보", description = "객실 이름과 예약 상태 정보를 가진 Class")
@Entity(name = "ROOM")
@Getter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROOM")
public class Room {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_number")
    private Long roomNumber;

    @ApiModelProperty(value = "객실 이름")
    @Column(name = "room_name", nullable = false)
    private String roomName;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<RoomStatus> status = new ArrayList<>();

    @Builder
    public Room(String roomName, List<RoomStatus> status) {
        this.roomName = roomName;
        this.status = status;
    }
}
