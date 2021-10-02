package pageObjects;

import base.GenericAction;
import elementConstants.OrderConfirmation;

public class OrderConfirmationScreen extends GenericAction {
    @Override
    public void setUp(String browserName, String platform) {

    }

    public AcademyEnrollmentsScreen clickOnFinishYourEnrollment(){
        waitForElementTobeExist(OrderConfirmation.finishYourEnrollment);
        bringElementIntoView(OrderConfirmation.finishYourEnrollment);
        click(OrderConfirmation.finishYourEnrollment);
        return new AcademyEnrollmentsScreen();
    }
}
