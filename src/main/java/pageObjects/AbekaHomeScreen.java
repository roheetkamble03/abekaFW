package pageObjects;

import base.GenericAction;
import elementConstants.AbekaHome;
import elementConstants.ShoppingCart;

public class AbekaHomeScreen extends GenericAction {
    public SearchScreen navigateToShopByGrade(String grade){
        navigateToHeaderBannerSubmenu(AbekaHome.shop,grade);
        return new SearchScreen();
    }

    public void navigateToHeaderBannerSubmenu(String menu, String submenu){
        mouseOverOnElement(menu);
        click(bringElementIntoView(String.format(AbekaHome.HEADER_SUB_MENU,submenu)));
    }

    public ShoppingCartPage navigateToShoppingCartPage(){
        click(AbekaHome.cart);
        waitForElementTobeVisibleOrMoveAhead(ShoppingCart.checkOutBtn);
        return new ShoppingCartPage();
    }

    @Override
    public void setUp(String browserName, String platform) {

    }

    public void validatedURLContent(String urlContent){
        softAssertions.assertThat(getCurrentURL().contains(urlContent))
                .as("Navigated page's current URL is not having mentioned URL content.\nCurrent URL:"+getCurrentURL()
                            +"\n Expected content:"+urlContent).isTrue();
    }

}
