package com.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AIResponse {
    private List<Candidate> candidates;

    @Data
    public static class Candidate {
        private Content content;
    }

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }

    public String getFirstText() {
        if (candidates != null && !candidates.isEmpty()) {
            return candidates.get(0).getContent().getParts().get(0).getText();
        }
        return "";
    }
}
