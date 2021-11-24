package dataProvider;

import constants.CommonConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.DataProvider;
import utility.ExcelUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DataProviders {
    ExcelUtils excelUtils = new ExcelUtils();

//Class --> LoginPageTest,HomePageTest Test Case--> loginTest, wishListTest, orderHistoryandDetailsTest

    @DataProvider(name = "parentCredentials")
    public Object[][] getParentCredentials(Method method) {
        return getExcelData(CommonConstants.PARENT_CREDENTIALS, method.getAnnotation(DataRowNumber.class));
    }

    @DataProvider(name = "studentCredentials")
    public Object[][] getCredentials(Method method) {
        return getExcelData(CommonConstants.STUDENT_CREDENTIALS, method.getAnnotation(DataRowNumber.class));
    }

    public Object[][] getExcelData(String sheetName, DataRowNumber dataRowNumber){
        // Totals rows count
        int rows = excelUtils.getRowCount(sheetName);
        // Total Columns
        int column = excelUtils.getColumnCount(sheetName);
        int actRows = rows - 1;

        Object[][] data = new Object[actRows+1][column];

        for (int rowNumber = 0; rowNumber <= actRows; rowNumber++) {
            for (int j = 0; j < column; j++) {
                data[rowNumber][j] = excelUtils.getCellData(sheetName, j, rowNumber);
            }
        }

        if(dataRowNumber != null) {
            int fromDataRowNumber = Integer.parseInt(dataRowNumber.fromDataRowNumber());
            int toDataRowNumber = ((dataRowNumber.toDataRowNumber().length()>0)?Integer.parseInt(dataRowNumber.toDataRowNumber()):actRows);
            Object[][] finalData = new Object[(toDataRowNumber-fromDataRowNumber)+1][column];
            int row = 0;
            int col = 0;
            for (int i = fromDataRowNumber; i <= toDataRowNumber; i++) {
                for (int j = 0; j < column; j++) {
                    finalData[row][col] = data[i][j];
                    col++;
                }
                col = 0;
                row++;
            }
            return finalData;
        }
        return data;
    }

    public ArrayList<Map<Integer,String>> getExcelData(String sheetName, int fromDataRowNumber, int toDataRowNumber){
        // Totals rows count
        int rows = excelUtils.getRowCount(sheetName);
        // Total Columns
        int column = excelUtils.getColumnCount(sheetName);
        int actRows = rows - 1;

        Object[][] data = new Object[actRows][column];

        for (int rowNumber = 0; rowNumber < actRows; rowNumber++) {
            for (int j = 0; j < column; j++) {
                data[rowNumber][j] = excelUtils.getCellData(sheetName, j, rowNumber + 1);
            }
        }
        //if to you need to read all rows pass fromDataRowNumber, toDataRowNumber as 0
        fromDataRowNumber = (fromDataRowNumber == 0)?1:fromDataRowNumber;
        toDataRowNumber = (toDataRowNumber == 0)? (toDataRowNumber == fromDataRowNumber) ? actRows+1:actRows:toDataRowNumber;
        Object[][] finalData = new Object[(toDataRowNumber-fromDataRowNumber)+1][column];

            ArrayList<Map<Integer,String>> excelDataMapList = new ArrayList<>();
            int row = 0;
            int col = 0;
            for (int i = fromDataRowNumber-1; i <= toDataRowNumber-1; i++) {
                Map<Integer,String> dataMap = new HashMap<>();
                for (int j = 0; j < column; j++) {
                    finalData[row][col] = data[i][j];
                    dataMap.put(col,data[i][j].toString());
                    col++;
                }
                excelDataMapList.add(dataMap);
                col = 0;
                row++;
            }
            return excelDataMapList;
    }

    public Map<String,ArrayList<String>> getExcelDataInHashTable(String sheetName, int fromDataRowNumber, int toDataRowNumber){
        // Totals rows count
        int rows = excelUtils.getRowCount(sheetName);
        // Total Columns
        int column = excelUtils.getColumnCount(sheetName);
        int actRows = rows - 1;

        Object[][] data = new Object[actRows][column];
        Map<String,ArrayList<String>> excelTable = new HashMap<>();
        ArrayList<String> columnData = new ArrayList<>();

        for (int rowNumber = 0; rowNumber < actRows; rowNumber++) {
            for (int j = 0; j < column; j++) {
                data[rowNumber][j] = excelUtils.getCellData(sheetName, j, rowNumber + 1);
            }
        }
        //if to you need to read all rows pass toDataRowNumber as 0
        toDataRowNumber = (toDataRowNumber == 0)? (toDataRowNumber == fromDataRowNumber) ? actRows+1:actRows:toDataRowNumber;

        int columnHeader = 0;
        for (int i = 0; i < data[0].length; i++) {
            columnData = new ArrayList<>();
            for (int j = fromDataRowNumber; j < toDataRowNumber; j++) {
                columnData.add(data[j][i].toString());

            }
            excelTable.put(excelUtils.getCellData(sheetName,columnHeader,fromDataRowNumber), columnData);
            columnHeader++;
        }
        return excelTable;
    }
}
