package hellofx;

public class User {
    private String username;
    private String email;
    private String password;
    private String profilePhoto;
    private String bio;

    public User(String username, String email, String password) {
        // Must have a username, email, and password
        this.username = username;
        this.email = email;
        this.password = password;
        // You can set a default profile photo and bio
        this.profilePhoto = "default.png";
        this.bio = "";
    }

    public boolean changePassword(String newPassword, String oldPassword) {
        // Check if the old password is correct
        // If it is, change the password
        if (oldPassword.equals(this.password)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    

}
