package com.project.indytskyi.tripsservice.validations;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ImageValidationTest {


    @Test
    @DisplayName("Validate MultipartFiles with incorrect format of files")
    void validateImagesIncorrectTypeOfFiles() {
        //GIVEN
        ImageValidation imageValidation = new ImageValidation();
        MockMultipartFile file
                = new MockMultipartFile(
                "files",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        int expected = 1;
        List<MultipartFile> files = List.of(file);

        //WHEN

        List<ErrorResponse> errorResponses = imageValidation.validateImages(files);

        //THEN
        assertEquals(expected, errorResponses.size());
    }

    @Test
    @DisplayName("Validate MultipartFiles with incorrect format of files")
    void validateImagesIncorrectSize() throws IOException {
        //GIVEN
        File file = new File("./src/test/resources/tripsservice.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        ImageValidation imageValidation = new ImageValidation();

        MultipartFile file1 = new MockMultipartFile("fileItem",
                "tripsservice.png",
                "image/png",
                IOUtils.toByteArray(fileInputStream));

        int expected = 1;
        List<MultipartFile> files = List.of(file1);

        //WHEN

        List<ErrorResponse> errorResponses = imageValidation.validateImages(files);

        //THEN
        assertEquals(expected, errorResponses.size());
    }

}