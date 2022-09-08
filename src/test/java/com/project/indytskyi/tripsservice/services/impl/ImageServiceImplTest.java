package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.s3.AmazonS3;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImagesRepository imagesRepository;

    @Mock
    private AmazonS3 s3;

    @InjectMocks
    private ImageServiceImpl underTest;



    @Test
    void canSaveImages() throws IOException {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        File file = new File("./src/test/resources/umlDiagramOfEntity.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "umlDiagramOfEntity.png",
                "image/png",
                IOUtils.toByteArray(fileInputStream));
        List<MultipartFile> files = List.of(multipartFile);

        //WHEN
        underTest.saveImages(trafficOrder, multipartFile.getOriginalFilename());

        //THEN
        verify(imagesRepository, atLeastOnce()).save(any());

    }

}
