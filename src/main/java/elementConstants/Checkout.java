package elementConstants;

public @interface Checkout {
    /**
     * Text
     */
    String PAY_IN_FULL = "Pay in Full";
    String TEN_MONTHS_PLAN = "10-Month Pay Plan";
    String DEFAULT_SHIPPING_ADDRESS = "Default shipping address";
    String NEW_SHIPPING_ADDRESS = "New Shipping Address";
    String STANDARD = "Standard";
    String PRIORITY = "Priority";
    String DEFAULT = "Default";

    /**
     * Element xpath
     */
    String iHaveAnAccountBtn = "id=lbnLogin";
    String paymentTermRadio = "xpath=//label[contains(normalize-space(text()),'%s')]/parent::*/preceding-sibling::div//input[@type='radio']";
    String shippingAddressMainRadio = "id=rbnShippingAddress_MAIN";
    String freeShippingRadio = "id=rbnDefaultShipping";
    String shippingBillingSameChkBox = "id=chkSameAsShippingAddress";
    String savedCreditCard = "xpath=//input[contains(@id,'rptSavedCards') and @name='PaymentChoice']";
    String termsAgreedChkBox = "id=ckbAgree";
    String placeOrder = "id=lbnPlaceOrder";
}
