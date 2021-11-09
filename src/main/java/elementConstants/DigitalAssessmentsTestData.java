package elementConstants;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DigitalAssessmentsTestData {
    String subject;
    String quiz;
    String segmentId;
    boolean isLocked;
}
