package pageObjects;

import base.GenericAction;
import elementConstants.OrderConfirmation;

public class OrderConfirmationScreen extends GenericAction {
    @Override
    protected void setUp(String browserName) {

    }

    public AcademyEnrollmentsScreen clickOnFinishYourEnrollment(){
        bringElementIntoView(OrderConfirmation.finishYourEnrollment);
        click(OrderConfirmation.finishYourEnrollment);
        return new AcademyEnrollmentsScreen();
    }
}
