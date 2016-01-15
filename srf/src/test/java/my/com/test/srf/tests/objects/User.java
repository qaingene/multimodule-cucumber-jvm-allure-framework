package my.com.test.srf.tests.objects;

/**
 * Class to get values from User object
 */
public class User {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String active;
    private String role;
    private String timeZone;
    private String name;
    private String status;
    private String selected;
    private String title;
    private String version;
    private String summary;
    private String sendConfirmationEmail;
    private String forceChangePassword;

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public String getSummary() {
        return summary;
    }

    public String getSelected() {
        return selected;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getActive() {
        return active;
    }

    public String getRole() {
        return role;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getSendConfirmationEmail() {
        return sendConfirmationEmail;
    }

    public String getForceChangePassword() {
        return forceChangePassword;
    }
}
