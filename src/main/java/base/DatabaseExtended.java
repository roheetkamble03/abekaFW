package base;

import constants.CommonConstants;
import lombok.SneakyThrows;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseExtended extends BaseClass {

    ResultSet resultSet;
    public static Connection connection;

    /**
     * Connecting to Data base
     * @param
     * @return
     */
    private Connection getDBConnection(String dataBase){
        boolean dbNotConnected = true;
        int retryCount = 0;
        while (dbNotConnected && retryCount < 3 && Boolean.parseBoolean(properties.getProperty(CommonConstants.IS_CONNECT_TO_DB))){
            try {
                OracleDataSource dataSource = getOracleDataSource(dataBase);
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
    private OracleDataSource getOracleDataSource(String dataBase){
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(properties.getProperty(CommonConstants.DB_USER_NAME));
        dataSource.setPassword(properties.getProperty(CommonConstants.DB_USER_PASSWORD));
        switch (dataBase){
            case CommonConstants.AD_DATA_BASE:
                dataSource.setServerName(properties.getProperty(CommonConstants.ADHost));
                dataSource.setDatabaseName(CommonConstants.AD_DATA_BASE);
                dataSource.setPortNumber(Integer.parseInt(properties.getProperty(CommonConstants.PORT)));
                dataSource.setDriverType(properties.getProperty(CommonConstants.DRIVER_TYPE));
                break;
            case CommonConstants.SD_DATA_BASE:
                dataSource.setServerName(properties.getProperty(CommonConstants.SDHost));
                dataSource.setDatabaseName(CommonConstants.SD_DATA_BASE);
                dataSource.setServiceName(properties.getProperty(CommonConstants.SDService));
                dataSource.setPortNumber(Integer.parseInt(properties.getProperty(CommonConstants.PORT)));
                dataSource.setDriverType(properties.getProperty(CommonConstants.DRIVER_TYPE));
                break;
            default:
                new Throwable("Wrong data base name provided");
        }
        return dataSource;
    }

    @SneakyThrows
    public ArrayList<HashMap<String,String>> executeAndGetSelectQueryData(String selectQuery, String dataBase){
        connection = getDBConnection(dataBase);
        try {
            resultSet = connection.createStatement().executeQuery(selectQuery);
        }catch (Throwable e){
            throw new Exception(e+"Execute query failed for \n" +selectQuery);
        }
        ArrayList<HashMap<String,String>> resultSetRowList = convertResultSetToArrayList();
        connection.close();
        return resultSetRowList;
    }

    @SneakyThrows
    private ArrayList<HashMap<String,String>> convertResultSetToArrayList(){
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
    private void executeStoredProcedure(String spQuery){
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
