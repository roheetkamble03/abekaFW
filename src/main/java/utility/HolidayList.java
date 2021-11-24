package utility;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class HolidayList {
    private String holiday;
    private LocalDate beginDate;
    private LocalDate endDate;
}
