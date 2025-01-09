public enum UserType {
    BUYER("Buyer"),
    ADMIN("Admin"),
    SELLER("Seller");

    private final String name;

    /**
     * Constructor initializes the user type name.
     * 
     * @param name The name of the user type.
     */
    UserType(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the user type.
     * 
     * @return The name of the user type.
     */
    public String getName() {
        return name;
    }
}
