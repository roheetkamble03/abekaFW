package pageObjects;

import base.GenericAction;
import elementConstants.Dashboard;
import java.util.ArrayList;

public class DashboardScreen extends GenericAction {
    public void validateDashboardNewTab(){
        ArrayList<String> tabList = getAllAvailableBrowserTab();
        getDriver().switchTo().window(tabList.get(1));
        softAssertions.assertThat(tabList.size() == Dashboard.tabCount)
                .as("Actual total tab count ["+tabList.size()+"] is not equal to expected tab count ["+Dashboard.tabCount+"]").isTrue();
        softAssertions.assertThat(getPageTitle().equals(Dashboard.TAB_TITLE))
                .as("Actual page title ["+ getDriver().getTitle()+"] is not equal to expected page title ["+Dashboard.TAB_TITLE+"]").isTrue();
        softAssertions.assertThat(getCurrentURL().equals(afterLoginURL+Dashboard.URL))
                .as("Actual page URL ["+ getCurrentURL()+"] is not equal to expected page URL ["+afterLoginURL+Dashboard.URL+"]").isTrue();
    }
}
