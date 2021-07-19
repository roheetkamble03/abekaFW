package utility;

import base.GenericAction;
import constants.StudentDetails;
import elementConstants.Enrollments;

public class StudentAccountDetails extends GenericAction {
    public static StudentDetails getGradeOneAccreditedStudentDetails(){
        return new StudentDetails();
    }

    public static StudentDetails getGradeFourStudentDetails(){
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setGrade(Enrollments.GRADE_FOUR);
        studentDetails.setBirthDate(generateStudentBirthDate(Enrollments.GRADE_FOUR));
        return studentDetails;
    }

    public static StudentDetails getGradeNineStudentDetails(){
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setGrade(Enrollments.GRADE_NINE);
        studentDetails.setBirthDate(generateStudentBirthDate(Enrollments.GRADE_NINE));
        return studentDetails;
    }
}
