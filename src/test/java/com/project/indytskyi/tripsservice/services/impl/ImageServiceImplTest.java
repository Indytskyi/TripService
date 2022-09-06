package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
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

    @Mock
    MultipartFile mockFile;

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
        underTest.saveImages(trafficOrder, files);

        //THEN
        verify(imagesRepository, atLeastOnce()).save(any());

    }

    @Test
    void canSaveImageToAmazonS3() throws IOException {
        //GIVEN
        File file = new File("./src/test/resources/umlDiagramOfEntity.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "umlDiagramOfEntity.png",
                "image/png",
                IOUtils.toByteArray(fileInputStream));
        PutObjectResult putObjectResult = new PutObjectResult();


        //WHEN
        when(s3.putObject(any(), any(), any(), any())).thenReturn(putObjectResult);

        String code = underTest.saveFile(multipartFile);
        //THEN
        assertEquals(null, code);
    }


    @Test
    void canThrowExceptionWhenSaveFile() throws IOException {
        //GIVEN
        File file = new File("./src/test/resources/umlDiagramOfEntity.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "umlDiagramOfEntity.png",
                "image/png",
                IOUtils.toByteArray(fileInputStream));

        //WHEN
        when(mockFile.getInputStream()).thenThrow(IOException.class);
        when(mockFile.getOriginalFilename()).thenReturn("umlDiagramOfEntity");
        when(mockFile.getSize()).thenReturn(31212312L);

//        when(multipartFile.getInputStream()).thenReturn(null);
        //THEN
        assertThatThrownBy(() -> underTest.saveFile(mockFile))
                .isInstanceOf(RuntimeException.class);
    }

}