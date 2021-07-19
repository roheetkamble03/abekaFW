package constants;

import elementConstants.Enrollments;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public @Data class EnrollmentOptions {
    private String penmanship = Enrollments.MANUSCRIPT;
    private String program = Enrollments.ACCREDITED_PROGRAM;
    private String streaming = Enrollments.STREAMING;
    private String guardians = Enrollments.EXISTING_PARENT;
    private String parentName = "Mr. RCG Testing";
    private String relation = "Father";
    private String signature = "RCG Testing";
    private String assessmentType = Enrollments.DIGITAL_ASSESSMENTS;
}
