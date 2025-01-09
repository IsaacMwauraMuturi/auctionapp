import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionManager {

    List<BeanAuction> auctions;
    DatabaseHandler databaseHandler;

    /**
     * Constructor initializes auction list and database handler.
     */
    public AuctionManager() {
        auctions = new ArrayList<>();
        databaseHandler = new DatabaseHandler();
        UserDao userDao = new UserDao(databaseHandler);
    }

    /**
     * Creates a new auction and saves it to the database.
     *
     * @param seller The user who is selling the item.
     * @param name The name of the auction.
     * @param description The description of the auction.
     * @param startingBid The starting bid of the auction.
     * @param startTime The start time of the auction.
     * @param endTime The end time of the auction.
     * @param ended The status of whether the auction has ended.
     * @return The created BeanAuction object.
     */
    public BeanAuction createAuction(BeanUser seller, String name, String description, double startingBid, LocalDateTime startTime, LocalDateTime endTime, Boolean ended) {
        BeanAuction auction = new BeanAuction(auctions.size() + 1, name, description, startingBid, startTime, endTime, seller, new ArrayList<>(), ended);
        auctions.add(auction);
        saveAuctionToDatabase(auction);
        System.out.println(auction);
        return auction;
    }

    /**
     * Saves an auction to the database.
     *
     * @param auction The auction to be saved.
     */
    void saveAuctionToDatabase(BeanAuction auction) {
        try {
            Connection connection = databaseHandler.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO auctions (name, description, starting_bid, start_time, end_time, seller_id, ended) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, auction.getName());
            statement.setString(2, auction.getDescription());
            statement.setDouble(3, auction.getStartingBid());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(auction.getStartTime()));
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(auction.getEndTime()));
            statement.setInt(6, auction.getSeller().getId());
            statement.setBoolean(7, auction.isEnded());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all auctions from the database.
     *
     * @return A list of loaded auctions.
     */
    public ArrayList<BeanAuction> loadAuctionsFromDatabase() {
        ArrayList<BeanAuction> loadedAuctions = new ArrayList<>();
        try {
            Connection connection = databaseHandler.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM test_jdbc.auctions;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int auctionId = resultSet.getInt(1);
                LocalDateTime startTime = resultSet.getTimestamp(5).toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp(6).toLocalDateTime();
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                double startingBid = resultSet.getDouble(4);
                int sellerId = resultSet.getInt(7);
                boolean ended = resultSet.getBoolean(8);

                UserDao userDao = new UserDao(databaseHandler);
                BeanUser seller = userDao.getUserById(sellerId);

                BeanAuction auction = new BeanAuction(auctionId, name, description, startingBid, startTime, endTime, seller, new ArrayList<>(), ended);
                loadedAuctions.add(auction);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loadedAuctions;
    }

    /**
     * Checks if the auctions table is empty.
     *
     * @return True if the table is empty, otherwise false.
     * @throws SQLException If a database access error occurs.
     */
    private boolean isAuctionsTableEmpty() throws SQLException {
        boolean isEmpty = true;
        String query = "SELECT COUNT(*) FROM auctions";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc", "root", ""); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                isEmpty = (rowCount == 0);
            }
        }
        return isEmpty;
    }

    /**
     * Updates an auction's start and end times in the database.
     *
     * @param auction The auction to be updated.
     * @param startTime The new start time of the auction.
     * @param endTime The new end time of the auction.
     */
    public void updateAuctionInDatabase(BeanAuction auction, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            if (isAuctionsTableEmpty()) {
                System.out.println("The auctions table is empty. Cannot update the auction.");
                return;
            }
            if (auction == null) {
                throw new IllegalArgumentException("Auction cannot be null");
            }
            if (startTime == null || endTime == null) {
                throw new IllegalArgumentException("Start time and end time must be specified");
            }
            Connection connection = databaseHandler.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE auctions SET start_time = ?, end_time = ? WHERE id = ?");
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(startTime));
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(endTime));
            statement.setInt(3, auction.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a list of active auctions.
     *
     * @return A list of active auctions.
     */
    public ArrayList<BeanAuction> getActiveAuctions() {
        ArrayList<BeanAuction> activeAuctions = new ArrayList<>();
        for (BeanAuction auction : loadAuctionsFromDatabase()) {
            if (auction.isActive()) {
                activeAuctions.add(auction);
            }
        }
        return activeAuctions;
    }

    /**
     * Searches for auctions based on search criteria.
     *
     * @param searchCriteria The criteria to search for.
     * @return A list of matching auctions.
     */
    public ArrayList<BeanAuction> searchAuctions(String searchCriteria) {
        ArrayList<BeanAuction> matchingAuctions = new ArrayList<>();
        ArrayList<BeanAuction> allAuctions = getActiveAuctions();
        for (BeanAuction auction : allAuctions) {
            if (auction.getName().contains(searchCriteria) || auction.getSeller().getUsername().contains(searchCriteria)) {
                matchingAuctions.add(auction);
            }
        }
        return matchingAuctions;
    }

    /**
     * Gets a list of auctions created by a specific user.
     *
     * @param user The user whose auctions are to be retrieved.
     * @return A list of auctions created by the specified user.
     */
    public ArrayList<BeanAuction> getAuctionsByUser(BeanUser user) {
        ArrayList<BeanAuction> auctionsByUser = new ArrayList<>();
        for (BeanAuction auction : auctions) {
            if (auction.getSeller().equals(user)) {
                auctionsByUser.add(auction);
            }
        }
        return auctionsByUser;
    }

    /**
     * Displays the details of an auction.
     *
     * @param auction The auction whose details are to be displayed.
     */
    public void displayAuctionDetails(BeanAuction auction) {
        System.out.println(auction.toString());
    }

}
