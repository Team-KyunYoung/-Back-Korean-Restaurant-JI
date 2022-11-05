package com.example.koreanrestaurantji.dto.event;

import com.example.koreanrestaurantji.domain.EventBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class EventResponseDto {
    private final Long eventNumber;
    private final String eventTitle;
    private final String eventImage;
    private final String writeDate;
    private final int eventView;

    public EventResponseDto(EventBoard eventBoard){
        this.eventNumber = eventBoard.getEventNumber();
        this.eventTitle = eventBoard.getEventTitle();
        this.eventImage = eventBoard.getEventImage();
        this.writeDate = eventBoard.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.eventView = eventBoard.getEventView();
    }
}
