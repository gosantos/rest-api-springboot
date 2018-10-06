package com.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ElectionRequest {
    private static final int DEFAULT_VALUE = 60;

    private String subjectTitle;

    @Builder.Default
    private Integer duration = DEFAULT_VALUE;
}
