package pageObjects;

import base.GenericAction;

public class SearchScreen extends GenericAction {
    @Override
    protected void setUp(String browserName, String platform) {

    }

    public BookDescriptionScreen selectProduct(String productName){
        //bringElementIntoView(productName);
        softAssertions.assertThat(isElementExists(productName));
        click(productName);
        return new BookDescriptionScreen();
    }
}
