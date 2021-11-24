package elementConstants;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProgressReportEventPreviewTestData {
    List<Integer> dayCount;
    List<String> eventID;
    List<String> previewTitle;
    List<String> popupTitle;
    List<String> popupType;
    List<String> previewDescription;
    List<String> previewEventLink;
    List<LocalDate> progressReportEventDate;
}
