package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage {

    private final Page page;
    private final Locator loggedInUser;
    private final Locator dropdownToggle;
    private final Locator logoutButton;
    private final Locator homeButton;
    private final Locator settingsButton;

    public HomePage(Page page) {
        this.page = page;
        this.loggedInUser = page.locator(".nav-link.dropdown-toggle.cursor-pointer");
        this.dropdownToggle = page.locator("div.nav-link.dropdown-toggle");
        this.logoutButton = page.locator(".ion-log-out");
        this.homeButton = page.locator("a.navbar-brand[href='#/']");
        this.settingsButton = page.locator(".ion-gear-a");
    }

    public String getLoggedInUsername() {
        loggedInUser.waitFor();
        return loggedInUser.textContent().trim();
    }

    public void logout() {
        dropdownToggle.click();
        logoutButton.waitFor();
        logoutButton.click();
        loggedInUser.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    public void goToSettings() {
        dropdownToggle.click();
        settingsButton.waitFor();
        settingsButton.click();
    }

    public void goToHomePage() {
        homeButton.click();
    }
}
