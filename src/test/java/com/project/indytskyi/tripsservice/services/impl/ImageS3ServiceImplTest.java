package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.project.indytskyi.tripsservice.exceptions.DamagedFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ImageS3ServiceImplTest {

    @Mock
    MultipartFile mockFile;
    @Mock
    private AmazonS3 s3;
    @InjectMocks
    private ImageS3ServiceImpl underTest;

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


        String path = underTest.saveFile(TRAFFIC_ORDER_ID ,multipartFile);
        //THEN
        assertEquals("null/22/umlDiagramOfEntity.png", path);
    }



    @Test
    void canThrowExceptionWhenSaveFile() throws IOException {
        //GIVEN
        //WHEN
        when(mockFile.getInputStream()).thenThrow(IOException.class);
        when(mockFile.getOriginalFilename()).thenReturn("umlDiagramOfEntity");
        when(mockFile.getSize()).thenReturn(31212312L);

        //THEN
        assertThatThrownBy(() -> underTest.saveFile(TRAFFIC_ORDER_ID ,mockFile))
                .isInstanceOf(DamagedFileException.class);
    }

    @SneakyThrows
    @Test
    void canGenerateURl() {
        //GIVEN
        String path = "./src/test/resources/umlDiagramOfEntity.png";
        File file = new File(path);


        underTest.downloadFile(path);
        verify(s3, times(1)).generatePresignedUrl(any());

//        S3Object s3Object = new S3Object();
//        s3Object.setBucketName("bucket");
//        s3Object.setKey("key");
//        s3Object.setObjectContent(new FileInputStream(file));
//        S3ObjectInputStream objectContent = s3Object.getObjectContent();
//        byte[] expected =  IOUtils.toByteArray(objectContent);
//        //WHEN
//        when(s3.getObject(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(s3Object);
//        when(object.getObjectContent()).thenReturn(objectContent);
//
//        byte[] result = underTest.downloadFile(path);
//        assertEquals(expected, result);
    }



}