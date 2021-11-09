package elementConstants;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoListTestData {
    List<String> myLessonsTodaySubjectList;
    List<String> myLessonsTodayLessonList;
    List<String> videoLibraryDropdownSubjectList;
    List<String> videoLibraryDropdownLongDescriptionList;
    List<String> segmentId;
    List<String> todayLessonOfVideoLibrary;
    List<String> nextDayLessonOfVideoLibrary;
}
