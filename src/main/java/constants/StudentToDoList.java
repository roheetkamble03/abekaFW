package constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public @Data class StudentToDoList {
    private String subjectName = "";
    private ArrayList<String> task = new ArrayList<>();

}
