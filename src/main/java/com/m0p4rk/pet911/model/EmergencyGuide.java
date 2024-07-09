package com.m0p4rk.pet911.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmergencyGuide {
    private Long id;
    private Long speciesId;
    private String symptoms;

    @JsonProperty("guide_step")
    private int guideStep;

    @JsonProperty("guide_text")
    private String guideText;

    private Timestamp createdAt;
}
