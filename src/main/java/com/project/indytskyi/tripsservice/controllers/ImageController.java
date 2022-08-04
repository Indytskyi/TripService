package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.services.ImageService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/trip/review")
public class ImageController {
    private final ImageService imageService;
    private final TrafficOrderService trafficOrderService;
}
