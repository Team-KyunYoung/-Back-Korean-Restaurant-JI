package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ApiModel(value = "전체 사용자 예약 정보", description = "테이블 예약 정보를 가진 Class")
@Entity(name = "RESERVATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESERVATION")
public class Reservation {

    @ApiModelProperty(value = "일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_number")
    private Long reservationNumber;

    @ApiModelProperty(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", foreignKey = @ForeignKey(name = "FK_user_reservation"))
    private User user;

    @ApiModelProperty(value = "예약 객실")
    @Column(name = "reservation_room_name", nullable = false)
    private String reservationRoomName;

    @ApiModelProperty(value = "예약자 성함")
    @Column(name = "reservation_name", nullable = false)
    private String reservationName;

    @ApiModelProperty(value = "예약자 연락처")
    @Column(name = "reservation_phone_number", nullable = false)
    private String reservationPhoneNumber;

    @ApiModelProperty(value = "예약 날짜")
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @ApiModelProperty(value = "예약 시간")
    @Column(name = "reservation_time", nullable = false)
    private String reservationTime;

    @ApiModelProperty(value = "예약 인원")
    @Column(name = "reservation_headCount", nullable = false)
    private String reservationHeadCount;

    @ApiModelProperty(value = "요청 사항")
    @Column(name = "reservation_request", length = 100)
    private String reservationRequest;

    @Builder(builderMethodName = "createBuilder")
    public Reservation(User user, String reservationName, String reservationPhoneNumber,
                       String reservationRoomName, LocalDate reservationDate, String reservationTime,
                       String reservationHeadCount, String reservationRequest) {
        this.user = user;
        this.reservationName = reservationName;
        this.reservationPhoneNumber = reservationPhoneNumber;
        this.reservationRoomName = reservationRoomName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationHeadCount = reservationHeadCount;
        this.reservationRequest = reservationRequest == "" || reservationRequest == null ? "요청사항 없음" : reservationRequest;
    }
}
