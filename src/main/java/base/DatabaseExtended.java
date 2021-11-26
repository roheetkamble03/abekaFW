package base;

import constants.CommonConstants;
import lombok.SneakyThrows;
import oracle.jdbc.pool.OracleDataSource;

import java.lang.reflect.Type;
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
        ArrayList<HashMap<String,String>> resultSetRowList = new ArrayList<>();
        int retryCount = 0;
        while (resultSetRowList.size()==0 && retryCount < 3) {
            connection = getDBConnection(dataBase);
            try {
                resultSet = connection.createStatement().executeQuery(selectQuery);
                log("\n\nExecuted query:\n" + selectQuery);
            } catch (Throwable e) {
                throw new Exception(e + "\n\nExecute query failed for \n\n" + selectQuery);
            }
            resultSetRowList = convertResultSetToArrayList();
            connection.close();
            retryCount++;
            if(resultSetRowList.size() == 0)Thread.sleep(5000);
        }
        if(resultSetRowList.size()==0)softAssertions.fail("No DB records found for following query: \n"
                +selectQuery+"\n DB"+dataBase);
        return resultSetRowList;
    }

    @SneakyThrows
    public void executeQuery(String selectQuery, String dataBase){
        log("Executing SQL query");
        connection = getDBConnection(dataBase);
        try {
            connection.createStatement().executeQuery(selectQuery);
            log("Executed query:\n"+selectQuery);
        }catch (Throwable e){
            throw new Exception(e+"Execute query failed for \n" +selectQuery);
        }
        connection.close();
    }

    @SneakyThrows
    private ArrayList<HashMap<String,String>> convertResultSetToArrayList(){
        //Logic yet to implement
        ArrayList<HashMap<String,String>> resultSetList = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        ArrayList<String> columnNameList = new ArrayList<>();
        HashMap<String,String> columnDataMap;

        for(int i=1;i<=columnCount;i++){
            columnNameList.add(resultSetMetaData.getColumnName(i).toUpperCase().trim());
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
    public void clearProcessOfAssessmentMarkVideoAsNotViewedStoredProcedure(String storedProcedure, String loginId, String subjectId, String startLesson, String endLesson, String dataBase){
        log("Executing stored procedure To process the specified assessment to unlocked");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setInt(1, Integer.parseInt(loginId));

        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.setInt(2, Integer.parseInt(subjectId));

        callableStatement.registerOutParameter(3, Types.INTEGER);
        callableStatement.setInt(3, Integer.parseInt(startLesson));

        callableStatement.registerOutParameter(4, Types.INTEGER);
        callableStatement.setInt(4, Integer.parseInt(endLesson)-1);

        callableStatement.execute();
        log("Successfully executed the stored procedure:\n"+storedProcedure+"\n LoginId:"+loginId +"\n SubjectId:"+subjectId+" \n StartLesson:"+startLesson+"\n EndLesson:"+endLesson);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void processSpecificAssessmentToUnlockedStoredProcedure(String storedProcedure, String loginId, String subjectId, String endLesson, String dataBase){
        log("Executing stored procedure To process the specified assessment to unlocked");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setInt(1, Integer.parseInt(loginId));

        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.setInt(2, Integer.parseInt(subjectId));

        callableStatement.registerOutParameter(3, Types.INTEGER);
        callableStatement.setInt(3, Integer.parseInt(endLesson)-1);

        callableStatement.execute();
        log("Successfully executed the stored procedure:\n"+storedProcedure+"\n LoginId:"+loginId +"\n SubjectId:"+subjectId+"\n EndLesson:"+endLesson);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void executeSetAllVideoCompletedStoredProcedure(String storedProcedure, String loginId, String dataBase){
        log("Executing stored procedure of marking single video as completed");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setInt(1, Integer.parseInt(loginId));
        callableStatement.execute();
        log("Successfully executed the stored procedure:\n"+storedProcedure+"\n LoginId:"+loginId);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void executeDeleteStudentAccountStoredProcedure(String storedProcedure, String studentId, String dataBase){
        log("Executing stored procedure of deleting student account");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setInt(1, Integer.parseInt(studentId));
        callableStatement.execute();
        log("Successfully executed the stored procedure:\n"+storedProcedure+"\n studentId:"+studentId);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void executeRemoveABAHoldStoredProcedure(String storedProcedure, String applicationNumber, String holdReasonCode, String releasedBy, String dataBase){
        log("Executing stored procedure to remove ABA hold");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.registerOutParameter(3, Types.VARCHAR);
        callableStatement.setInt(1,Integer.parseInt(applicationNumber));
        callableStatement.setInt(2,Integer.parseInt(holdReasonCode));
        callableStatement.setString(3,releasedBy);
        callableStatement.execute();
        log("Successfully executed the stored procedure on AD DB:\n"+storedProcedure+"\n applicationNumber:"
                +applicationNumber+"\n holdReasonCode:"+holdReasonCode+"\n releasedBy:"+releasedBy);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void executeMarkApplicationAsCompletedStoredProcedure(String storedProcedure, String applicationNumber, String changedBy, String dataBase){
        log("Executing stored procedure to mark application as complete");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.registerOutParameter(2, Types.VARCHAR);
        callableStatement.setInt(1,Integer.parseInt(applicationNumber));
        callableStatement.setString(2,changedBy);
        callableStatement.execute();
        log("Successfully executed the stored procedure on AD DB:\n"+storedProcedure+"\n applicationNumber:"
                +applicationNumber+"\n changedBy:"+changedBy);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void executeUpdateCourseBeginDateToBackDate(String storedProcedure, String applicationNumber, String pastBeginDate, String studentUserId, String dataBase){
        log("Executing stored procedure of changing course begin date of student account");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.setInt(1, Integer.parseInt(applicationNumber));
        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.setInt(2, Integer.parseInt(pastBeginDate));
        callableStatement.registerOutParameter(3, Types.VARCHAR);
        callableStatement.setString(3, studentUserId);
        callableStatement.execute();
        log("Successfully executed the stored procedure:\n"+storedProcedure+"\n studentUserID:"+studentUserId);
        callableStatement.close();
        connection.close();
    }

    @SneakyThrows
    public void executeSetVideoCompletedStoredProcedure(String storedProcedure, String subscriptionNumber, String subscriptionItem, String loginId, String segmentId,
                                                        String userID, String dataBase){
        log("Executing stored procedure of marking single video as completed");
        connection = getDBConnection(dataBase);
        CallableStatement callableStatement = connection.prepareCall(storedProcedure);
        callableStatement.registerOutParameter(1, Types.INTEGER);
        callableStatement.registerOutParameter(2, Types.INTEGER);
        callableStatement.registerOutParameter(3, Types.INTEGER);
        callableStatement.registerOutParameter(4, Types.INTEGER);
        callableStatement.registerOutParameter(5, Types.VARCHAR);

        callableStatement.setInt(1,Integer.parseInt(subscriptionNumber));
        callableStatement.setInt(2,Integer.parseInt(subscriptionItem));
        callableStatement.setInt(3,Integer.parseInt(loginId));
        callableStatement.setInt(4,Integer.parseInt(segmentId));
        callableStatement.setString(5,userID);
        callableStatement.execute();
        log("Successfully executed the stored procedure:\n"+storedProcedure+"\n subscriptionNumber:"+subscriptionNumber+"\nsubscriptionItem:"+subscriptionItem+"\nloginId:"+loginId+"\nsegmentId:"+segmentId+"\nuserID:"+userID);
        callableStatement.close();
        connection.close();

//        CallableStatement cstmt = connection.prepareCall("{call sampleProcedure()}");
//        //Executing the CallableStatement
//        ResultSet rs1 = cstmt.executeQuery();
//        //Displaying the result
//        System.out.println("Contents of the first result-set");
//        while(rs1.next()) {
//            System.out.print("First Name: "+rs1.getString("First_Name")+", ");
//            System.out.print("Last Name: "+rs1.getString("Last_Name")+", ");
//            System.out.print("Year of Birth: "+rs1.getDate("Year_Of_Birth")+", ");
//            System.out.print("Place of Birth: "+rs1.getString("Place_Of_Birth")+", ");
//            System.out.print("Country: "+rs1.getString("Country"));
//            System.out.println();
//        }
//        System.out.println(" ");
//        cstmt.getMoreResults();
//        System.out.println("Contents of the second result-set");
//        ResultSet rs2 = cstmt.getResultSet();
//        while(rs2.next()) {
//            System.out.print("Product Name: "+rs2.getString("Product_Name")+", ");
//            System.out.print("Name of Customer: "+rs2.getString("Name_Of_Customer")+", ");
//            System.out.print("Dispatch Date: "+rs2.getDate("Dispatch_Date")+", ");
//            System.out.print("Location: "+rs2.getString("Location"));
//            System.out.println();
//        }
    }
}
