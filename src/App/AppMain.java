package App;

import Database.TeaLogToDB;
import MVC.Model.Service.CupStatsService;
import MVC.Model.TeaMakerMachine;
import MVC.View.TeaMakerFrame;

import java.sql.SQLException;

public class AppMain {
    public static void main(String[] args) throws SQLException {
        int dailyLimit = 0;
        TeaLogToDB dbLog = new TeaLogToDB();
        CupStatsService cupService = new CupStatsService(dbLog, dailyLimit);
        TeaMakerMachine machine = new TeaMakerMachine(cupService, dbLog);
        TeaMakerFrame application = new TeaMakerFrame(machine, cupService);
        application.run();
    }
}
