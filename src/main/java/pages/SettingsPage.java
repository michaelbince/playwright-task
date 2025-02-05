package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SettingsPage {
    private final Page page;
    private final Locator profileImageInput;
    private final Locator usernameInput;
    private final Locator bioTextarea;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator updateButton;

    public SettingsPage(Page page) {
        this.page = page;
        this.profileImageInput = page.locator("input[name='image']");
        this.usernameInput = page.locator("input[name='username']");
        this.bioTextarea = page.locator("textarea[name='bio']");
        this.emailInput = page.locator("input[name='email']");
        this.passwordInput = page.locator("input[name='password']");
        this.updateButton = page.locator("button[type='submit']");
    }

    public void updateProfile(String imageUrl, String username, String bio, String email, String password) {
        profileImageInput.fill(imageUrl);
        usernameInput.fill(username);
        bioTextarea.fill(bio);
        emailInput.fill(email);
        passwordInput.clear();
        passwordInput.fill(password);
        updateButton.click();
    }

    public boolean isUpdateButtonEnabled() {
        return updateButton.isEnabled();
    }

    public String getProfileImageUrl() {
        return profileImageInput.inputValue();
    }

    public String getUsername() {
        return usernameInput.inputValue();
    }

    public String getBio() {
        return bioTextarea.inputValue();
    }

    public String getEmail() {
        return emailInput.inputValue();
    }
}
