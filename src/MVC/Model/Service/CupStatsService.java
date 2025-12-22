package MVC.Model.Service;


import Database.TeaLogToDB;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

public class CupStatsService{

    private final TeaLogToDB logdb;
    private final int dailyLimit;


    public CupStatsService(TeaLogToDB logdb, int dailyLimit) {
        this.logdb = logdb;
        this.dailyLimit = dailyLimit;
    }

    public int getDailyTotal() throws SQLException{
        return logdb.getDailyTotal(LocalDate.now());
    }

    public String getMonthlyTotal() throws SQLException{
        YearMonth curMonth = YearMonth.now();
        return logdb.getMonthlyTotal(curMonth);
    }

    public boolean isDailyLimitExceeded() throws SQLException{
        return getDailyTotal() >= dailyLimit;
    }

    public void updateCupsDB() {
        logdb.cupAmount ++;
    }

}
