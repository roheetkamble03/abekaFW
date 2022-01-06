package apiPojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteAccountApiResponse {
    @JsonProperty("Response")
    String response;

    @JsonProperty("Message")
    String message;

    @Getter
    @NoArgsConstructor
    public static class DigitalAssessmentArrayPojo {
        private List<DigitalAssessmentPojo> digitalAssessmentPojoList;
    }

}
