package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.service.EventService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"9. Event"})
@RequestMapping(value = "/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
}
