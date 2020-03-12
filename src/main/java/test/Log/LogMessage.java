package test.Log;

import java.util.ArrayList;
import java.util.List;

public class LogMessage {
    private Boolean passed = false ;  ;
    private String logMessage = "" ;
    private Boolean skipped = false;
    private List<LogMessage> subLogMessages= new ArrayList<LogMessage>();
    public LogMessage(){

    }

    public LogMessage(Boolean passed,String logMeassge){
        this.passed = passed ;
        this.logMessage = logMeassge ;
    }

    public Boolean isPassed(){
        return passed ;
    }

    public void setPassed(Boolean passedValue) {
        this.passed = passedValue ;
    }
    public void addSubLogMessage(LogMessage logMessage){ this.subLogMessages.add(logMessage); }
    public boolean isAnySubLogFailed(){
        return subLogMessages.stream().anyMatch(o -> o.isPassed().equals(false));
    }

    public List<LogMessage> getSubLogMessage(){ return this.subLogMessages; }
    public String getLogMessage(){
        return logMessage ;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage ;
    }

    public boolean isSkipped(){
        return  skipped ;
    }

    public void setSkippedTrue(){
        skipped = true;
    }
}
