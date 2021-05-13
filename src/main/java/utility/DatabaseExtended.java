package utility;

import base.BaseClass;
import base.GenericAction;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;

import java.sql.*;

public class DatabaseExtended extends BaseClass {

    Statement statement;
    ResultSet resultSet;

    @SneakyThrows
    public ResultSet executeSelectQuery(String selectQuery){
        statement = connection.createStatement();
        return statement.executeQuery("select * from ABADB.agreements");
    }
}
