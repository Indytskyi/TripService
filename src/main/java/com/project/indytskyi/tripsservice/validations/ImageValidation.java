package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageValidation {

    /**
     * Validation for pictures that saving in Amazon S3
     * Images must be:
     * only jpg or png format
     * Size of one picture must be less than 3 mb
     * The number of photos must be exactly 3"
     */
    public List<ErrorResponse> validateImages(List<MultipartFile> files) {
        final int numberOfPictures = 3;
        final int sizeOfOnePicture = 3145728;
        List<ErrorResponse> errorResponses = new ArrayList<>();
        files.forEach(file -> {
            String imageName = file.getOriginalFilename();
            if (!imageName.endsWith("png")
                    && !file.getOriginalFilename().endsWith("jpg")) {
                errorResponses.add(new ErrorResponse(file.getOriginalFilename(),
                        "Incorrect format of file. Only images with format (jpg or png)"));
            }

            if (file.getSize() > sizeOfOnePicture) {
                errorResponses.add(new ErrorResponse(imageName,
                        "Incorrect size of file. Must be less than 3 mb"));
            }
        });

        if (files.size() != numberOfPictures) {
            errorResponses.add(new ErrorResponse("number",
                    "The number of photos must be exactly 3"));
        }

        return errorResponses;
    }

}
