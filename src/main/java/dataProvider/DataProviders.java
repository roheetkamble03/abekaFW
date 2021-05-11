package dataProvider;

import org.testng.annotations.DataProvider;
import utility.ExcelUtils;

public class DataProviders {
    ExcelUtils excelUtils = new ExcelUtils();

//Class --> LoginPageTest,HomePageTest Test Case--> loginTest, wishListTest, orderHistoryandDetailsTest

    @DataProvider(name = "parentCredentials")
    public Object[][] getParentCredentials() {
        return getExcelData("ParentCredentials");
    }

    @DataProvider(name = "studentCredentials")
    public Object[][] getCredentials() {
        return getExcelData("StudentCredentials");
    }

    private Object[][] getExcelData(String sheetName){
        // Totals rows count
        int rows = excelUtils.getRowCount(sheetName);
        // Total Columns
        int column = excelUtils.getColumnCount(sheetName);
        int actRows = rows - 1;

        Object[][] data = new Object[actRows][column];

        for (int i = 0; i < actRows; i++) {
            for (int j = 0; j < column; j++) {
                data[i][j] = excelUtils.getCellData(sheetName, j, i + 2);
            }
        }
        return data;
    }
}
