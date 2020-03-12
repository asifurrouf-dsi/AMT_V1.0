package test.Log;

import test.coreModule.TestCase;

import java.awt.*;
import java.nio.file.LinkOption;
import java.util.List;

public class LogReport {

    private static final LogReport LogReportSingleton = new LogReport();
    private static final CreateLog PassedLogReport = new CreateLog("PassedTCReport");;
    private static final CreateLog FailedLogReport =  new CreateLog("FailedTCReport");  ;

    private LogReport(){

    }

    public static LogReport getInstance(){
        return LogReportSingleton ;
    }

    public void addTestcaseLogreport(TestCase testCase, List<LogMessage> logmessages ) {
        CreateLog createLog = PassedLogReport;
        CreateLog createFailedLog = null;
        String logName = testCase.getTestCaseNumber() + "--" + testCase.getTestCaseName() ;
        createLog.createLogger(logName ,testCase.getcategoryName());

        String failedLogName;
        if(!testCase.isPassed()) {
            createFailedLog = FailedLogReport;
            failedLogName = testCase.getTestCaseNumber() + "--" + testCase.getTestCaseName() ;
            createFailedLog.createLogger(failedLogName,testCase.getcategoryName());
        }
        
        for(LogMessage logMessage : logmessages) {
            List<LogMessage> subLogMessages=logMessage.getSubLogMessage();
            if(!subLogMessages.isEmpty())
            {
                for(LogMessage subLogMessage: subLogMessages)
                {
                    createLog.writeLog(logName,subLogMessage.getLogMessage(),subLogMessage.isPassed(),subLogMessage.isSkipped());
                }
            }
            createLog.writeLog(logName,logMessage.getLogMessage(),logMessage.isPassed(),logMessage.isSkipped());
            if(!testCase.isPassed()){
                createFailedLog.writeLog(logName,logMessage.getLogMessage(),logMessage.isPassed(),logMessage.isSkipped());
            }
        }
    }
}
