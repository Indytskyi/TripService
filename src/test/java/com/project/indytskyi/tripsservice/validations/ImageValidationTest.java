package com.project.indytskyi.tripsservice.validations;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImageValidationTest {


//    @Test
//    @DisplayName("Validate MultipartFiles with incorrect format of files")
//    void validateImagesIncorrectTypeOfFiles() {
//        //GIVEN
//        PictureValidationAspect imageValidation = new PictureValidationAspect();
//        MockMultipartFile file
//                = new MockMultipartFile(
//                "files",
//                "hello.txt",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, World!".getBytes()
//        );
//        int expected = 1;
//        List<MultipartFile> files = List.of(file);
//
//        //WHEN
//
//        List<ErrorResponse> errorResponses = imageValidation.validate(files);
//
//        //THEN
//        assertEquals(expected, errorResponses.size());
//    }
//
//    @Test
//    @DisplayName("Validate MultipartFiles with incorrect format of files")
//    void validateImagesIncorrectSize() throws IOException {
//        //GIVEN
//        File file = new File("./src/test/resources/tripsservice.png");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        ImageValidation imageValidation = new ImageValidation();
//
//        MultipartFile file1 = new MockMultipartFile("fileItem",
//                "tripsservice.png",
//                "image/png",
//                IOUtils.toByteArray(fileInputStream));
//
//        int expected = 1;
//        List<MultipartFile> files = List.of(file1);
//
//        //WHEN
//
//        List<ErrorResponse> errorResponses = imageValidation.validateImages(files);
//
//        //THEN
//        assertEquals(expected, errorResponses.size());
//    }

}