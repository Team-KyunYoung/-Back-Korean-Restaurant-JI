package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.EventBoard;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.event.EventCreateRequestDto;
import com.example.koreanrestaurantji.dto.event.EventPostResponseDto;
import com.example.koreanrestaurantji.dto.event.EventResponseDto;
import com.example.koreanrestaurantji.dto.event.EventUpdateRequestDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.EventRepository;
import com.example.koreanrestaurantji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Boolean findUserByTokenIsRole(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                        .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND)).isRole();
    }

    public SuccessResponseDto create(EventCreateRequestDto eventCreateRequestDto) {
        if(findUserByTokenIsRole()) {
            try {
                eventRepository.save(new EventBoard(eventCreateRequestDto.getEventTitle(),
                        eventCreateRequestDto.getEventImage(),
                        eventCreateRequestDto.getEventContents()));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_EVENT);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<EventResponseDto> findEventList() {
        return eventRepository.findAll()
                .stream()
                .map(EventResponseDto::new)
                .collect(Collectors.toList());
    }

    public EventBoard findEventByEventNumber(long eventNumber){
        return eventRepository.findByEventNumberOrderByWriteDateDesc(eventNumber).orElseThrow(() -> new BaseException(BaseResponseCode.EVENT_NOT_FOUND));
    }

    public EventPostResponseDto findEventPost(long eventNumber){
        try {
            eventRepository.updateViewCount(eventNumber);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_EVENT);
        }
        return new EventPostResponseDto(findEventByEventNumber(eventNumber));
    }

    public SuccessResponseDto updateEventPost(long eventNumber, EventUpdateRequestDto eventUpdateRequestDto){
        if(findUserByTokenIsRole()) {
            try {
                eventRepository.updatePost(eventNumber, eventUpdateRequestDto.getEventTitle(),
                        eventUpdateRequestDto.getEventImage(),
                        eventUpdateRequestDto.getEventContents());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_EVENT);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(Long eventNumber){
        if(findUserByTokenIsRole()) {
            try {
                eventRepository.delete(findEventByEventNumber(eventNumber));
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
