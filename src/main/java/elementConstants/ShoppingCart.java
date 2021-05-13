package elementConstants;

import lombok.Getter;

public @interface ShoppingCart {
    /**
     * Text
     */
    double PRICE = 1074.00;
    int QUANTITY = 1;
    String GRADE_ONE_VIDEO_BOOKS_ENROLLMENT_ACCREDITED = "Grade 1 Video & Books Enrollment – Accredited";
    String SHOPPING_CART_TITLE_TEXT = "You have %s items in your cart.";
    String ITEM_NUMBER = "381748";

    /**
     * Element xpath
     */

    String productRow = "xpath=//div[@data-sbn='%s']";
    final String productTitle = "xpath=./descendant::div[position()=3]";
    final String productPrice = "xpath=./descendant::div[position()=5]";
    final String quantityTextBox = "xpath=./descendant::input[@class='txtQuantity shop-item-qty']";
    final String subTotal = "xpath=./descendant::div[position()=7]";
    String shoppingCartTitle = "id=cartItems";
    String checkOutBtn = "id=lbnFinishOrder";

}
