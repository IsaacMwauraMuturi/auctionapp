
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BiddingService {

    private final Connection conn;

    /**
     * Constructor initializes the connection.
     *
     * @param conn The database connection.
     */
    public BiddingService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Places a bid on an item.
     *
     * @param itemId The id of the item being bid on.
     * @param userId The id of the user placing the bid.
     * @param amount The amount of the bid.
     * @return True if the bid was placed successfully, otherwise false.
     * @throws SQLException if a database access error occurs.
     */
    public boolean placeBid(int itemId, int userId, double amount) throws SQLException {
        String query = "SELECT MAX(amount) FROM bids WHERE item_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, itemId);
        ResultSet rs = stmt.executeQuery();
        double currentHighestBid = 0;
        if (rs.next()) {
            currentHighestBid = rs.getDouble(1);
        }
        rs.close();
        stmt.close();
        if (amount <= currentHighestBid) {
            return false;
        }

        query = "INSERT INTO bids (item_id, user_id, amount) VALUES (?, ?, ?)";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, itemId);
        stmt.setInt(2, userId);
        stmt.setDouble(3, amount);
        int rowsAffected = stmt.executeUpdate();
        stmt.close();
        return rowsAffected == 1;
    }

    /**
     * Gets the highest bid for an item.
     *
     * @param itemId The id of the item.
     * @return The highest bid amount.
     * @throws SQLException if a database access error occurs.
     */
    public double getHighestBid(int itemId) throws SQLException {
        String query = "SELECT MAX(amount) FROM bids WHERE item_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, itemId);
        ResultSet rs = stmt.executeQuery();
        double highestBid = 0;
        if (rs.next()) {
            highestBid = rs.getDouble(1);
        }
        rs.close();
        stmt.close();
        return highestBid;
    }
}
