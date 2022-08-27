package com.example.koreanrestaurantji.dto.event;

import com.example.koreanrestaurantji.domain.EventBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class EventPostResponseDto {
    private final Long eventNumber;
    private final String eventTitle;
    private final String eventImage;
    private final String eventContents;
    private final String writeDate;
    private final int eventView;

    public EventPostResponseDto(EventBoard eventBoard){
        this.eventNumber = eventBoard.getEventNumber();
        this.eventTitle = eventBoard.getEventTitle();
        this.eventImage = eventBoard.getEventImage();
        this.eventContents = eventBoard.getEventContents();
        this.writeDate = eventBoard.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.eventView = eventBoard.getEventView();
    }
}
