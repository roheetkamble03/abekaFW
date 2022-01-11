package pageObjects;

import base.GenericAction;
import elementConstants.Search;

public class SearchScreen extends GenericAction {
    @Override
    public void setUp(String browserName, String platform) {

    }

    public BookDescriptionScreen selectProduct(String productName){
        waitForElementTobeExist(productName);
        softAssertions.assertThat(isElementExists(productName, false));
        implicitWaitInSeconds(10);
        click(productName, false);
        return new BookDescriptionScreen();
    }

    public SearchScreen searchProduct(String productName){
        type(Search.searchBox,productName);
        click(Search.searchBtn, false);
        return this;
    }
}
