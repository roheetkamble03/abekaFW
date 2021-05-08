package dataProvider;

import org.testng.annotations.DataProvider;
import utility.ExcelUtils;

public class DataProviders {
    ExcelUtils excelUtils = new ExcelUtils();

//Class --> LoginPageTest,HomePageTest Test Case--> loginTest, wishListTest, orderHistoryandDetailsTest

    @DataProvider(name = "credentials")
    public Object[][] getCredentials() {
        // Totals rows count
        int rows = excelUtils.getRowCount("Credentials");
        // Total Columns
        int column = excelUtils.getColumnCount("Credentials");
        int actRows = rows - 1;

        Object[][] data = new Object[actRows][column];

        for (int i = 0; i < actRows; i++) {
            for (int j = 0; j < column; j++) {
                data[i][j] = excelUtils.getCellData("Credentials", j, i + 2);
            }
        }
        return data;
    }

}
