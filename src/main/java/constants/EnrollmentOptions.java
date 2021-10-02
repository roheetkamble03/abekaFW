package constants;

import elementConstants.Enrollments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class EnrollmentOptions {
    private String penmanship = Enrollments.MANUSCRIPT;
    private String program = Enrollments.ACCREDITED_PROGRAM;
    private String streaming = Enrollments.STREAMING;
    private String guardians = Enrollments.NEW_PARENT;
    private String parentName = "Joe Customer";
    private String relation = "Father";
    private String signature = "Joe Customer";
    private String assessmentType = Enrollments.DIGITAL_ASSESSMENTS;
}
