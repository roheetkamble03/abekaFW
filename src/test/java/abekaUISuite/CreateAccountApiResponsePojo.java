package abekaUISuite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAccountApiResponsePojo {
    @JsonProperty("Response")
    private String response;
    @JsonProperty("CustomerNumber")
    private String customerNumber;
}
