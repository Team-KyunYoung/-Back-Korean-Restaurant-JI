package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@ApiModel(value = "객실 예약 상태 정보", description = "객실 예약 상태 정보를 가진 Class")
@Entity(name = "ROOM_STATUS")
@Getter
@NoArgsConstructor
@DynamicUpdate //실제 값이 변경된 컬럼으로만 update 쿼리를 만드는 기능. 몇몇개의 컬럼만 자주 업데이트 하는 경우에 사용한다.
public class RoomStatus {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_status_number")
    private Long roomStatusNumber;

    @ApiModelProperty(value = "room")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_number", foreignKey = @ForeignKey(name = "FK_room_status"))
    private Room room;

    @ApiModelProperty(value = "예약 날짜")
    @Column(name = "reservation_date", nullable = false)
    private String reservationDate;

    @ApiModelProperty(value = "예약 시간")
    @Column(name = "reservation_time", nullable = false)
    private String reservationTime;

    @ApiModelProperty(value = "잔여 객실 수")
    @Column(name = "room_remaining", nullable = false)
    @ColumnDefault("15") //
    private int roomRemaining;

    @Builder
    public RoomStatus(String reservationDate, String reservationTime, int roomRemaining) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.roomRemaining = roomRemaining;
    }
}
