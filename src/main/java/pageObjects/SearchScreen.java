package pageObjects;

import base.GenericAction;

public class SearchScreen extends GenericAction {
    @Override
    protected void setUp(String browserName) {

    }

    public BookDescriptionScreen selectProduct(String productName){
        //bringElementIntoView(productName);
        softAssertions.assertThat(isElementExists(productName));
        click(productName);
        return new BookDescriptionScreen();
    }
}
