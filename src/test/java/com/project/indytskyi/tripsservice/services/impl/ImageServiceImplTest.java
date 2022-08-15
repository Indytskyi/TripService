package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.dto.TripFinishReceiverDtoFactory.TRIP_FINISH_RECEIVER_IMAGES;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImagesRepository imagesRepository;


    @InjectMocks
    private ImageServiceImpl underTest;

    @Test
    void canSaveImages() {

        TrafficOrderEntity trafficOrder = createTrafficOrder();


        underTest.saveImages(trafficOrder, TRIP_FINISH_RECEIVER_IMAGES);

        verify(imagesRepository, atLeastOnce()).save(any());

    }
}