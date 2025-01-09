public class BeanUser {

    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private UserType role;

    /**
     * Constructor initializes the user details.
     * 
     * @param id The user's id.
     * @param name The user's name.
     * @param username The user's username.
     * @param email The user's email.
     * @param password The user's password.
     * @param role The user's role.
     */
    public BeanUser(int id, String name, String username, String email, String password, UserType role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user's id.
     * 
     * @return The user's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's id.
     * 
     * @param id The new id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user's name.
     * 
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * 
     * @param name The new name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's username.
     * 
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * 
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's email.
     * 
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * 
     * @param email The new email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * 
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password The new password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's role.
     * 
     * @return The user's role.
     */
    public UserType getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * 
     * @param role The new role to be set.
     */
    public void setRole(UserType role) {
        this.role = role;
    }
}
