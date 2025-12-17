package Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class TeaLogToDB {

    public int cupAmount;

    public TeaLogToDB(){
        cupAmount = 0;
    }

    public void insertLog(LocalDate date) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String line = "INSERT INTO tea_log (cups, log_date) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(line);

        stmt.setInt(1, this.cupAmount);
        stmt.setDate(2, Date.valueOf(date));

        stmt.executeUpdate();

    }

    public int getDailyTotal(LocalDate date) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String line = "SELECT SUM(cups) FROM tea_log WHERE log_date = ?";
        PreparedStatement stmt = conn.prepareStatement(line);
        stmt.setDate(1, Date.valueOf(date));
        ResultSet rs = stmt.executeQuery();
        return  rs.getInt(1);
    }
    public int getMonthlyTotal(YearMonth month) throws SQLException {
        Connection conn = DBConnection.getConnection();

        LocalDate startMonth = month.atDay(1);
        LocalDate endMonth = month.atEndOfMonth();

        String line = "SELECT SUM(cups) FROM tea_log WHERE log_date BETWEEN ? AND ?";
        PreparedStatement stmt = conn.prepareStatement(line);
        stmt.setDate(1, Date.valueOf(startMonth));
        stmt.setDate(2, Date.valueOf(endMonth));

        ResultSet rs = stmt.executeQuery();
        return rs.getInt("sum(cups)");

    }
}
