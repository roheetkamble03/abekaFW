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
    String CC_NUMBER = "6011000000000004";

    /**
     * Element xpath
     */
    String iHaveAnAccountBtn = "id=lbnLogin";
    String paymentTermRadio = "xpath=//label[contains(normalize-space(text()),'%s')]/parent::*/preceding-sibling::div//input[@type='radio']";
    String newCreditCardRadio = "id=rbNewCreditCard";
    String ccNumber = "id=txtCCNum";
    String ccExpiryMonthTextBox = "id=ddlExpirationMonth-selectized";
    String ccExpiryMonth = "xpath=//select[@id='ddlExpirationMonth']/following-sibling::div/descendant::div[@class='option' and @data-value='03']";
    String getCcExpiryYearTextBox = "id=ddlExpirationYear-selectized";
    String ccExpiryYear = "xpath=//select[@id='ddlExpirationYear']/following-sibling::div/descendant::div[@class='option' and @data-value='%s']";
    String ccSecurityCode = "id=txtSecurityCode";
    String shippingAddressMainRadio = "id=rbnShippingAddress_MAIN";
    String freeShippingRadio = "id=rbnDefaultShipping";
    String shippingBillingSameChkBox = "id=chkSameAsShippingAddress";
    String savedCreditCard = "xpath=//input[contains(@id,'rptSavedCards') and @name='PaymentChoice']";
    String termsAgreedChkBox = "id=ckbAgree";
    String placeOrder = "id=lbnPlaceOrder";
    String suggestedShipAddresBtn = "xpath=//a[@data-use-suggested='SHIP']";
}
