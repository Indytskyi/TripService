package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.exceptions.ApiValidationImageException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageValidation {

    public void validateImages(List<MultipartFile> files) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        files.forEach(file -> {
            String imageName = file.getOriginalFilename();
            if (!imageName.endsWith("png")
                    && !file.getOriginalFilename().endsWith("jpg")) {
                errorResponses.add(new ErrorResponse(file.getOriginalFilename(),
                        "Incorrect format of file. Only images with format (jpg or png)"));
            }

            if (file.getSize() > 3145728) {
                errorResponses.add(new ErrorResponse(imageName,
                        "Incorrect size of file. Must be less than 3 mb"));
            }
        });

        if (!errorResponses.isEmpty()) {
            throw new ApiValidationImageException(errorResponses);
        }

    }
}

