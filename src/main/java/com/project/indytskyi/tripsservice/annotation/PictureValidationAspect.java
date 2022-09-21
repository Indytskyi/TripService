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

    @Around("@annotation(PictureValidation)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("picture validation");
        List<MultipartFile> files = (List<MultipartFile>) joinPoint.getArgs()[1];
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

        if (!errorResponses.isEmpty()) {
            throw new ApiValidationException(errorResponses);
        }

        return joinPoint.proceed();
    }


}
