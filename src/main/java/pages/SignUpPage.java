package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class SignUpPage {
    private final Page page;
    private final Locator signUpLabelLink;
    private final Locator userNameField;
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator signUpButton;

    public SignUpPage(Page page) {
        this.page = page;
        this.signUpLabelLink = page.locator("a[href='#/register']");
        this.userNameField = page.locator("input[name='username']");
        this.emailField = page.locator("input[name='email']");
        this.passwordField = page.locator("input[name='password']");
        this.signUpButton = page.locator("button.btn-lg");
    }

    public void goToSignUpPage() {
        signUpLabelLink.click();
    }

    public void signUp(String username, String email, String password) {
        userNameField.waitFor();
        userNameField.fill(username);
        emailField.fill(email);
        passwordField.fill(password);
        signUpButton.click();
    }
}
