package base;

import base.BaseClass;
import base.GenericAction;
import constants.CommonConstants;
import constants.StudentToDoList;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.poi.ss.formula.functions.T;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatabaseExtended extends BaseClass {

    Statement statement;
    ResultSet resultSet;
    public static Connection connection;
    public static String dbUserName;
    public static String dbUserPassword;
    public static String dbConnectionURL;

    /**
     * Connecting to Data base
     * @param
     * @return
     */
    public Connection getDBConnection(){
        boolean dbNotConnected = true;
        int retryCount = 0;
        dbConnectionURL = properties.getProperty(CommonConstants.DB_CONNECTION_URL);
        dbUserName = properties.getProperty(CommonConstants.DB_USER_NAME);
        dbUserPassword = properties.getProperty(CommonConstants.DB_USER_PASSWORD);
        while (dbNotConnected && retryCount < 3 && Boolean.parseBoolean(properties.getProperty(CommonConstants.IS_CONNECT_TO_DB))){
            try {
                OracleDataSource dataSource = new OracleDataSource();
                dataSource.setServerName("ad.oracle.pcci.edu");
                dataSource.setUser(dbUserName);
                dataSource.setPassword(dbUserPassword);
                dataSource.setDatabaseName("AD");
                dataSource.setPortNumber(1521);
                dataSource.setDriverType("thin");
                dbNotConnected = false;
                retryCount++;
                return dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @SneakyThrows
    public ArrayList<HashMap<String,String>> executeAndGetSelectQueryData(String selectQuery){
        connection = getDBConnection();
        resultSet = connection.createStatement().executeQuery(selectQuery);
        ArrayList<HashMap<String,String>> resultSetRowList = convertResultSetToArrayList();
        connection.close();
        return resultSetRowList;
    }

    @SneakyThrows
    public ArrayList<HashMap<String,String>> convertResultSetToArrayList(){
        //Logic yet to implement
        ArrayList<HashMap<String,String>> resultSetList = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        ArrayList<String> columnNameList = new ArrayList<>();
        HashMap<String,String> columnDataMap;
        String key;
        String value;

        for(int i=1;i<=columnCount;i++){
            columnNameList.add(resultSetMetaData.getColumnName(i).toUpperCase());
        }

        while (resultSet.next()){
            columnDataMap = new HashMap<>();
            for(int i=1;i<=columnCount;i++){
                columnDataMap.put(columnNameList.get(i-1),resultSet.getString(i));
            }
            resultSetList.add(columnDataMap);
        }
        return resultSetList;
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
