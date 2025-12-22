package MVC.Controller.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class TeaLogToDB {


    public TeaLogToDB() {

    }

    public void insertLog(int cupAmount) throws SQLException {
        LocalDate date =  LocalDate.now();
        Connection conn = DBConnectionProvider.getConnection();

        String line = "INSERT INTO tea_log (cups, log_date) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(line);

        stmt.setInt(1, cupAmount);
        stmt.setDate(2, Date.valueOf(date));

        stmt.executeUpdate();

    }

    public int getDailyTotal(LocalDate date) throws SQLException {

        Connection conn = DBConnectionProvider.getConnection();
        String line = "SELECT SUM(cups) FROM tea_log WHERE log_date = ?";
        PreparedStatement stmt = conn.prepareStatement(line);
        stmt.setDate(1, Date.valueOf(date));
        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }

    public String getMonthlyTotal(YearMonth month) throws SQLException {
        Connection conn = DBConnectionProvider.getConnection();

        LocalDate startMonth = month.atDay(1);
        LocalDate endMonth = month.atEndOfMonth();

        String line = "SELECT SUM(cups) FROM tea_log WHERE log_date BETWEEN ? AND ?";
        PreparedStatement stmt = conn.prepareStatement(line);
        stmt.setDate(1, Date.valueOf(startMonth));
        stmt.setDate(2, Date.valueOf(endMonth));

        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            return String.valueOf(rs.getInt("sum(cups)"));
        return "";
    }
}
