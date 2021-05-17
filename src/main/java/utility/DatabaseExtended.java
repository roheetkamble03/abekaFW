package utility;

import base.BaseClass;
import base.GenericAction;
import constants.StudentToDoList;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseExtended extends BaseClass {

    Statement statement;
    ResultSet resultSet;

    @SneakyThrows
    public ResultSet executeSelectQuery(String selectQuery){
        statement = connection.createStatement();
        return statement.executeQuery(selectQuery);
    }

    @SneakyThrows
    public ArrayList<StudentToDoList> getStudentToDoListFromDB(String studentName){
        //Logic yet to implement
        ArrayList<StudentToDoList> studentToDoLists = new ArrayList<>();
        ResultSet studentToDoList = executeSelectQuery("SELECT tboxh.txh_subj, tnp.eno_desc || ' - ' || tboxd.txd_desc AS assessment,\n" +
                "                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (3, 5, 6) THEN 'Grade Pending' WHEN assign.da_status IN (1) THEN 'In Progress' ELSE to_char(assign.grade_percentage) || '%' END AS grade,\n" +
                "                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN '' ELSE lgrds.lgr_lgrd END AS lettergrade,\n" +
                "                            assign.completion_date, assign.da_grading_lockdate, assign.da_enddate\n" +
                "                   FROM homeschoolhouse.student_assignments assign\n" +
                "                       JOIN linc.abadb_tboxh tboxh\n" +
                "                         ON assign.pr_form = tboxh.txh_form\n" +
                "                        AND assign.pr_version = tboxh.txh_ver\n" +
                "                        AND assign.pr_school = tboxh.txh_school\n" +
                "                        AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
                "                       JOIN linc.abadb_tboxd tboxd\n" +
                "                         ON tboxh.txh_form = tboxd.form_number\n" +
                "                        AND tboxh.txh_school = tboxd.school\n" +
                "                        AND tboxh.txh_boxltr = tboxd.box_letter\n" +
                "                        AND tboxh.txh_ver = tboxd.version\n" +
                "                        AND assign.pr_itemnumber = tboxd.txd_itmnbr\n" +
                "                        AND CASE WHEN assign.pr_subitemnumber IS NULL THEN 0 ELSE assign.pr_subitemnumber END = 0\n" +
                "                       JOIN linc.abadb_tenop tnp\n" +
                "                         ON tboxh.txh_subj = tnp.eno_rcard\n" +
                "                       JOIN linc.abadb_appld appld\n" +
                "                         ON assign.apref = appld.ap_apref\n" +
                "                       JOIN linc.abadb_applc applc\n" +
                "                         ON appld.ap_apref = applc.apc_apref\n" +
                "                        AND applc.ap_item = tnp.eno_subj\n" +
                "                      INNER JOIN linc.abadb_grads grads\n" +
                "                         ON appld.ap_grade = grads.gr_grade\n" +
                "                        AND appld.ap_beg_dt BETWEEN grads.gr_beg_dt AND grads.gr_end_dt\n" +
                "                       LEFT OUTER JOIN linc.abadb_lgrds lgrds\n" +
                "                         ON grads.gr_scale = lgrds.lgr_scale\n" +
                "                        AND CASE WHEN assign.grade_percentage = 0 THEN 1 ELSE assign.grade_percentage END BETWEEN lgrds.lgr_min_gr AND lgrds.lgr_max_gr\n" +
                "                      WHERE assign.student_id = '427725'\n" +
                "                        AND appld.ap_end_dt >= trunc(sysdate)\n" +
                "                        AND assign.da_enddate is not null\n" +
                "                        AND coalesce(assign.da_grading_lockdate, CASE WHEN da_status NOT IN(7,8) THEN sysdate ELSE assign.da_enddate END) >= sysdate - '5'\n" +
                "                        AND tboxd.txd_da_testid > 0\n" +
                "                      UNION\n" +
                "                     SELECT tboxh.txh_subj, tnp.eno_desc || ' - ' || tboxm.txm_desc AS assessment,\n" +
                "                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN 'Grade Pending' WHEN assign.da_status IN (1) THEN 'In Progress' ELSE to_char(assign.grade_percentage) || '%' END AS grade,\n" +
                "                            CASE WHEN assign.grade_percentage = 777.7 OR assign.da_status IN (1, 3, 5, 6) THEN '' ELSE lgrds.lgr_lgrd END AS lettergrade,\n" +
                "                            assign.completion_date, assign.da_grading_lockdate, assign.da_enddate\n" +
                "                       FROM homeschoolhouse.student_assignments assign\n" +
                "                       JOIN linc.abadb_tboxh tboxh\n" +
                "                         ON assign.pr_form = tboxh.txh_form\n" +
                "                        AND assign.pr_version = tboxh.txh_ver\n" +
                "                        AND assign.pr_school = tboxh.txh_school\n" +
                "                        AND assign.pr_boxletter = tboxh.txh_boxltr\n" +
                "                       JOIN linc.abadb_tboxm tboxm\n" +
                "                         ON tboxh.txh_form = tboxm.form_number\n" +
                "                        AND tboxh.txh_school = tboxm.school\n" +
                "                        AND tboxh.txh_boxltr = tboxm.box_letter\n" +
                "                        AND tboxh.txh_ver = tboxm.version\n" +
                "                        AND assign.pr_itemnumber = tboxm.txm_itmnbr\n" +
                "                        AND assign.pr_subitemnumber = tboxm.txm_sub_itmnbr\n" +
                "                       JOIN linc.abadb_tenop tnp\n" +
                "                         ON tboxh.txh_subj = tnp.eno_rcard\n" +
                "                       JOIN linc.abadb_appld appld\n" +
                "                         ON assign.apref = appld.ap_apref\n" +
                "                       JOIN linc.abadb_applc applc\n" +
                "                         ON appld.ap_apref = applc.apc_apref\n" +
                "                        AND applc.ap_item = tnp.eno_subj\n" +
                "                      INNER JOIN linc.abadb_grads grads\n" +
                "                         ON appld.ap_grade = grads.gr_grade\n" +
                "                        AND appld.ap_beg_dt BETWEEN grads.gr_beg_dt AND grads.gr_end_dt\n" +
                "                       LEFT OUTER JOIN linc.abadb_lgrds lgrds\n" +
                "                         ON grads.gr_scale = lgrds.lgr_scale\n" +
                "                        AND CASE WHEN assign.grade_percentage = 0 THEN 1 ELSE assign.grade_percentage END BETWEEN lgrds.lgr_min_gr AND lgrds.lgr_max_gr\n" +
                "                      WHERE assign.student_id = '427725'\n" +
                "                        AND appld.ap_end_dt >= trunc(sysdate)\n" +
                "                        AND assign.da_enddate is not null\n" +
                "                        AND coalesce(assign.da_grading_lockdate, CASE WHEN da_status NOT IN(7,8) THEN sysdate ELSE assign.da_enddate END) >= sysdate - '5'\n" +
                "                        AND tboxm.txm_da_testid > 0\n" +
                "                      ORDER BY completion_date");
        while(studentToDoList.next()){
            System.out.print("Assignment:"+ studentToDoList.getString("ASSESSMENT") + "Grade:"+ studentToDoList.getString("GRADE")+"\n");
        }
        return studentToDoLists;
    }

    @SneakyThrows
    public void executeStoredProcedure(String spQuery){
        CallableStatement callableStatement = connection.prepareCall("{call procedure_name(?, ?, ?)}");
        resultSet = callableStatement.executeQuery();

        CallableStatement cstmt = connection.prepareCall("{call sampleProcedure()}");
        //Executing the CallableStatement
        ResultSet rs1 = cstmt.executeQuery();
        //Displaying the result
        System.out.println("Contents of the first result-set");
        while(rs1.next()) {
            System.out.print("First Name: "+rs1.getString("First_Name")+", ");
            System.out.print("Last Name: "+rs1.getString("Last_Name")+", ");
            System.out.print("Year of Birth: "+rs1.getDate("Year_Of_Birth")+", ");
            System.out.print("Place of Birth: "+rs1.getString("Place_Of_Birth")+", ");
            System.out.print("Country: "+rs1.getString("Country"));
            System.out.println();
        }
        System.out.println(" ");
        cstmt.getMoreResults();
        System.out.println("Contents of the second result-set");
        ResultSet rs2 = cstmt.getResultSet();
        while(rs2.next()) {
            System.out.print("Product Name: "+rs2.getString("Product_Name")+", ");
            System.out.print("Name of Customer: "+rs2.getString("Name_Of_Customer")+", ");
            System.out.print("Dispatch Date: "+rs2.getDate("Dispatch_Date")+", ");
            System.out.print("Location: "+rs2.getString("Location"));
            System.out.println();
        }
    }
}
