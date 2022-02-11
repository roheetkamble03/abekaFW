package constants;

import base.GenericAction;
import elementConstants.Enrollments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
public @Data class StudentDetails extends GenericAction {

    private String firstName = "ATM"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String middleName = "ATM";

    private String lastName = "ATM"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String suffix = Enrollments.SUFFIX_JR;

    private String gender = Enrollments.GENDER;

    private String birthDate = generateStudentBirthDate(Enrollments.GRADE_ONE_VIDEO);

    private String studentUserId = "ATM"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String password = "Password@123";

    private String grade = Enrollments.GRADE_ONE_ACCREDITED;

    private String studentFullName = "";
}
