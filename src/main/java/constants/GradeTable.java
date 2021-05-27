package constants;

import lombok.Getter;

@Getter
public class GradeTable {
    String lesson;
    String description;
    String grade;

    public GradeTable(String lesson,String description, String grade){
        this.lesson = lesson;
        this.description = description;
        this.grade = grade;
    }
}
