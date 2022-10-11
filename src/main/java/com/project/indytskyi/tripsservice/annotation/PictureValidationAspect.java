package com.project.indytskyi.tripsservice.annotation;

import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
@Slf4j
public class PictureValidationAspect {

    private static final int NUMBER_OF_PICTURES = 3;
    private static final int SIZE_OF_ONE_PICTURES = 3145728;
    private static final String FIRST_FORMAT_FILE = "jpg";
    private static final String SECOND_FORMAT_FILE = "png";

    private List<ErrorResponse> errorResponses;

    @Around("@annotation(PictureValidation)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {

        List<MultipartFile> files = (List<MultipartFile>) joinPoint.getArgs()[1];

        log.info("Start validating files");

        errorResponses = new ArrayList<>();

        if (files.size() != NUMBER_OF_PICTURES) {
            log.error("The number of photos must be exactly 3");
            errorResponses.add(new ErrorResponse("number",
                    "The number of photos must be exactly 3"));
            throw new ApiValidationException(errorResponses);
        }

        files.forEach(this::validateFile);

        if (!errorResponses.isEmpty()) {
            throw new ApiValidationException(errorResponses);
        }

        return joinPoint.proceed();
    }

    private void validateFile(MultipartFile file) {
        String imageName = file.getOriginalFilename();
        if (!imageName.endsWith(SECOND_FORMAT_FILE)
                && !imageName.endsWith(FIRST_FORMAT_FILE)) {
            log.error("Incorrect format of file = {}", file.getOriginalFilename());
            errorResponses.add(new ErrorResponse(file.getOriginalFilename(),
                    "Incorrect format of file. Only images with format (jpg or png)"));
        }

        if (file.getSize() > SIZE_OF_ONE_PICTURES) {
            log.error("Incorrect size of file = {}", file.getOriginalFilename());
            errorResponses.add(new ErrorResponse(imageName,
                    "Incorrect size of file. Must be less than 3 mb"));
        }
    }

}
