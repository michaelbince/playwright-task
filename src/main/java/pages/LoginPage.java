package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LoginPage {
    private final Page page;
    private final Locator loginLabelLink;
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        this.page = page;
        this.loginLabelLink = page.locator("a[href='#/login']");
        this.emailField = page.locator("input[name='email']");
        this.passwordField = page.locator("input[name='password']");
        this.loginButton = page.locator("button.btn-lg");
        this.errorMessage = page.locator("ul.error-messages li");
    }

    public void goToLoginPage() {
        loginLabelLink.click();
    }

    public void login(String email, String password) {
        emailField.waitFor();
        emailField.fill(email);
        passwordField.fill(password);
        loginButton.click();
    }

    public String getErrorMessage() {
        errorMessage.waitFor();
        return errorMessage.innerText();
    }
}
