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

    private final int numberOfPictures = 3;
    private final int sizeOfOnePicture = 3145728;

    private final String firstFormatFile = "jpg";
    private final String secondFormatFile = "png";

    @Around("@annotation(PictureValidation)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {

        List<MultipartFile> files = (List<MultipartFile>) joinPoint.getArgs()[1];

        log.info("Start validating files");

        List<ErrorResponse> errorResponses = new ArrayList<>();

        if (files.size() != numberOfPictures) {
            log.error("The number of photos must be exactly 3");
            errorResponses.add(new ErrorResponse("number",
                    "The number of photos must be exactly 3"));
            throw new ApiValidationException(errorResponses);
        }

        files.forEach(file -> {
            String imageName = file.getOriginalFilename();
            if (!imageName.endsWith(secondFormatFile)
                    && !imageName.endsWith(firstFormatFile)) {
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

        if (!errorResponses.isEmpty()) {
            throw new ApiValidationException(errorResponses);
        }

        return joinPoint.proceed();
    }

}
