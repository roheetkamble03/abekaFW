package pageObjects;

import base.GenericAction;
import constants.Product;
import elementConstants.ShoppingCart;

import java.util.List;

public class ShoppingCartPage extends GenericAction {
    @Override
    protected void setUp(String browserName) {

    }

    public CheckoutScreen clickOnCheckOut(){
        bringElementIntoView(ShoppingCart.checkOutBtn);
        click(ShoppingCart.checkOutBtn);
        return new CheckoutScreen();
    }

    public ShoppingCartPage validateProductInCart(List<Product> productList){
        softAssertions.assertThat(isElementTextEquals(ShoppingCart.shoppingCartTitle,String.format(ShoppingCart.shoppingCartTitleText,productList.size())))
                .as("Shopping cart actual header ["+getElementText(ShoppingCart.shoppingCartTitle)+"] is not matching with expected header ["
                        +String.format(ShoppingCart.shoppingCartTitleText,productList.size())).isTrue();
        softAssertions.assertThat(isElementDisplayed(getElement(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber()))
                ,ShoppingCart.gradeOneVideoBookAccredited+"\\n\\nItem No. "+productList.get(0).getItemNumber())).
                as(ShoppingCart.gradeOneVideoBookAccredited+"\\n\\nItem No. "+productList.get(0).getItemNumber() + "Product title is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        softAssertions.assertThat(isElementDisplayed(getElement(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber()))
                ,formatCurrencyToDollar(productList.get(0).getPrice()))).as(formatCurrencyToDollar(productList.get(0).getPrice()) + "Product price is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        softAssertions.assertThat(isElementDisplayed(getElement(String.format(ShoppingCart.productRow,productList.get(0).getQuantity()))
                ,Integer.toString(productList.get(0).getQuantity()))).as(Integer.toString(productList.get(0).getQuantity()) + "Product quantity is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        softAssertions.assertThat(isElementDisplayed(getElement(String.format(ShoppingCart.productRow,productList.get(0).getItemNumber()))
                ,formatCurrencyToDollar(productList.get(0).getSubtotal()))).as(formatCurrencyToDollar(productList.get(0).getSubtotal()) + "Product subtotal is not present for "+ShoppingCart.gradeOneVideoBookAccredited).isTrue();
        return this;
    }
}
