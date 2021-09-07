package abekaUISuite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteAccountApiResponse {
    @JsonProperty("Response")
    String response;

    @JsonProperty("Message")
    String message;
}
