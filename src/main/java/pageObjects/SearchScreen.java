package pageObjects;

import base.GenericAction;
import elementConstants.Search;

public class SearchScreen extends GenericAction {
    @Override
    public void setUp(String browserName, String platform) {

    }

    public BookDescriptionScreen selectProduct(String productName){
        implicitWaitInSeconds(5);
        softAssertions.assertThat(isElementExists(productName));
        click(productName);
        return new BookDescriptionScreen();
    }

    public SearchScreen searchProduct(String productName){
        type(Search.searchBox,productName);
        click(Search.searchBtn);
        return this;
    }
}
