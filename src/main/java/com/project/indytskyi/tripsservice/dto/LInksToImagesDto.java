package com.project.indytskyi.tripsservice.dto;

import java.net.URL;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class LInksToImagesDto {
    private long tripId;
    private List<URL> imageUrls;
}
