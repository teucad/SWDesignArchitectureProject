package App;

import Database.TeaLogToDB;
import MVC.Model.Service.CupStatsService;
import MVC.Model.TeaMakerMachine;
import MVC.View.TeaMakerFrame;

public class AppMain {
    static void main(String[] args) {
        TeaLogToDB dbLog = new TeaLogToDB();
        int dailyLimit = 0;
        CupStatsService cupService = new CupStatsService(dbLog, dailyLimit);
        TeaMakerMachine machine = new TeaMakerMachine(cupService, dbLog);
        TeaMakerFrame application = new TeaMakerFrame(machine);
        application.run();
        application.setVisible(true);
    }
}
