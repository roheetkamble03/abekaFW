package constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class RecentGrades {
    private String assignments = "";
    private String grade = "";
}
