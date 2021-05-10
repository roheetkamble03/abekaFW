package pageObjects;

import base.GenericAction;
import constants.Product;
import elementConstants.ShoppingCart;

import java.util.List;

public class ShoppingCartPage extends GenericAction {
    @Override
    protected void setUp(String browserName, String platform) {

    }

    public CheckoutScreen clickOnCheckOut(){
        bringElementIntoView(ShoppingCart.checkOutBtn);
        click(ShoppingCart.checkOutBtn);
        return new CheckoutScreen();
    }

    public ShoppingCartPage validateProductInCart(List<Product> productList){
        String price = formatCurrencyToDollar(productList.get(0).getPrice());
        String quantity = Integer.toString(productList.get(0).getQuantity());
        String subTotal = formatCurrencyToDollar(productList.get(0).getSubtotal());
        softAssertions.assertThat(isElementTextEquals(ShoppingCart.shoppingCartTitle,String.format(ShoppingCart.shoppingCartTitleText,productList.size())))
                .as("Shopping cart actual header ["+getElementText(ShoppingCart.shoppingCartTitle)+"] is not matching with expected header ["
                        +String.format(ShoppingCart.shoppingCartTitleText,productList.size())).isTrue();
        softAssertions.assertThat(isElementTextEquals(getChildElement(getElement(String.format(ShoppingCart.productRow,
                productList.get(0).getItemNumber())),ShoppingCart.productTitle),
                ShoppingCart.gradeOneVideoBookAccredited+"\nItem No. "+productList.get(0).getItemNumber())).as("Product title is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        softAssertions.assertThat(isElementTextEquals(getChildElement(getElement(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber())),ShoppingCart.productPrice),price))
                .as(price + "Product price is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        softAssertions.assertThat(isElementValueEquals(getChildElement(getElement(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber())),ShoppingCart.quantityTextBox),quantity))
                .as( "Product quantity ["+ quantity+"] is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        softAssertions.assertThat(isElementTextEquals(getChildElement(getElement(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber())),ShoppingCart.subTotal),subTotal))
                .as("Product subtotal ["+subTotal+"] is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        return this;
    }
}
