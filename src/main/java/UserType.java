public enum UserType {
    BUYER("Buyer"),
    ADMIN("Admin"),
    SELLER("Seller");
    private final String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
