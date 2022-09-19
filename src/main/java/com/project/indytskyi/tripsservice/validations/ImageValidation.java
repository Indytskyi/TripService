package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageValidation {

    /**
     * Validation for pictures that saving in Amazon S3
     * Images must be:
     * only jpg or png format
     * Size of one picture must be less than 3 mb
     * The number of photos must be exactly 3"
     */
    public List<ErrorResponse> validateImages(List<MultipartFile> files) {

        log.info("picture validation");

        final int numberOfPictures = 3;
        final int sizeOfOnePicture = 3145728;

        List<ErrorResponse> errorResponses = new ArrayList<>();
        files.forEach(file -> {
            String imageName = file.getOriginalFilename();
            if (!imageName.endsWith("png")
                    && !file.getOriginalFilename().endsWith("jpg")) {
                log.error("Incorrect format of file = {}", file.getOriginalFilename());
                errorResponses.add(new ErrorResponse(file.getOriginalFilename(),
                        "Incorrect format of file. Only images with format (jpg or png)"));
            }

            if (file.getSize() > sizeOfOnePicture) {
                log.error("Incorrect size of file = {}", file.getOriginalFilename());
                errorResponses.add(new ErrorResponse(imageName,
                        "Incorrect size of file. Must be less than 3 mb"));
            }
        });

        if (files.size() != numberOfPictures) {
            log.error("The number of photos must be exactly 3");
            errorResponses.add(new ErrorResponse("number",
                    "The number of photos must be exactly 3"));
        }

        return errorResponses;
    }

}
