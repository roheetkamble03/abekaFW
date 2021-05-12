package constants;

import base.GenericAction;
import elementConstants.Enrollments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;

@NoArgsConstructor
@AllArgsConstructor
public @Data class StudentDetails extends GenericAction {

    private String firstName = "Auto"+ RandomStringUtils.randomAlphabetic(5);

    private String middleName = "Auto"+RandomStringUtils.randomAlphabetic(5);

    private String lastName = "Auto"+RandomStringUtils.randomAlphabetic(5);

    private String suffix = Enrollments.SUFFIX_JR;

    private String gender = Enrollments.GENDER;

    private String birthDate = generateStudentBirthDate(Enrollments.GRADE_ONE_ACCREDITED);

    private String userName = "Auto"+RandomStringUtils.randomAlphabetic(5);

    private String password = "Password@123";
}
