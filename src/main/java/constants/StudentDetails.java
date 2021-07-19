package constants;

import base.GenericAction;
import elementConstants.Enrollments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
public @Data class StudentDetails extends GenericAction {

    private String firstName = "Auto"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String middleName = "Auto"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String lastName = "Auto"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String suffix = Enrollments.SUFFIX_JR;

    private String gender = Enrollments.GENDER;

    private String birthDate = generateStudentBirthDate(Enrollments.GRADE_ONE_ACCREDITED);

    private String userName = "Auto"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern(CommonConstants.NAME_DATE_FORMAT));

    private String password = "Password@123";

    private String grade = Enrollments.GRADE_ONE;
}
