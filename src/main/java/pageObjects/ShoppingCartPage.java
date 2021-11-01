package pageObjects;

import base.GenericAction;
import constants.Product;
import elementConstants.ShoppingCart;

import java.util.List;

public class ShoppingCartPage extends GenericAction {
    @Override
    public void setUp(String browserName, String platform) {

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
        implicitWaitInSeconds(5);
        softAssertions.assertThat(isElementTextEquals(ShoppingCart.shoppingCartTitle,String.format(ShoppingCart.SHOPPING_CART_TITLE_TEXT,productList.size())))
                .as("Shopping cart actual header ["+getElementText(ShoppingCart.shoppingCartTitle)+"] is not matching with expected header ["
                        +String.format(ShoppingCart.SHOPPING_CART_TITLE_TEXT,productList.size())).isTrue();
        softAssertions.assertThat(isElementTextEquals(String.format(ShoppingCart.productRow,
                productList.get(0).getItemNumber(),ShoppingCart.productTitle),
                productList.get(0).getProductTitle() +"\nItem No. "+productList.get(0).getItemNumber())).as("Product title is not present for "+productList.get(0).getProductTitle()).isTrue();
        softAssertions.assertThat(isElementTextEquals(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber(),ShoppingCart.productPrice),price))
                .as("Product's expected price ["+price+"] is not equal to actual price ["+getElementText(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber(),ShoppingCart.productPrice))+"] for "+productList.get(0).getProductTitle()).isTrue();
        softAssertions.assertThat(isElementValueEquals(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber(),ShoppingCart.quantityTextBox),quantity))
                .as( "Product expected quantity ["+ quantity+"] is not equal to actual quantity ["+getElementValue(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber(),ShoppingCart.quantityTextBox))+"] for "+productList.get(0).getProductTitle()).isTrue();
        implicitWaitInSeconds(3);
        softAssertions.assertThat(isElementTextEquals(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber(),ShoppingCart.subTotal),subTotal))
                .as("Product expected subtotal ["+subTotal+"] is not equal to actual subtotal["+getElementText(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber(),ShoppingCart.subTotal))+"] for "+productList.get(0).getProductTitle()).isTrue();
        return this;
    }
}
