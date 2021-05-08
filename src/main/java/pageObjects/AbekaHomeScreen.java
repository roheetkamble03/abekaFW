package pageObjects;

import base.GenericAction;
import elementConstants.AbekaHome ;
import elementConstants.ShoppingCart;

public class AbekaHomeScreen extends GenericAction {
    public SearchScreen navigateToShopByGrade(){
        navigateToHeaderBannerSubmenu(AbekaHome.shop,AbekaHome.firstGrade);
        return new SearchScreen();
    }

    public void navigateToHeaderBannerSubmenu(String menu, String submenu){
        mouseOverOnElement(menu);
        click(submenu);
    }

    public ShoppingCartPage navigateToShoppingCartPage(){
        click(AbekaHome.cart);
        waitForElementTobeVisible(ShoppingCart.checkOutBtn);
        return new ShoppingCartPage();
    }

    @Override
    protected void setUp(String browserName) {

    }
}
