package test.coreModule;

import test.utility.PropertyConfig;

import java.util.HashMap;
import java.util.Map;

public class TestStep {
    private Map step ;

    @Override
    public String toString() {
        return super.toString();
    }

    private boolean isPassed = true  ;
    public TestStep(){

    }

    public TestStep(Map step){
        this.step = step;
    }

    public String getStepNumber(){
        try {
           return (String) step.get(PropertyConfig.STEP_NO);
        }catch(Exception ex) {
            return null;
        }
    }
    public String getLogMessage(){
        try {
            return (String) step.get(PropertyConfig.LOGMESSAGE);
        }catch(Exception ex) {
            return null;
        }
    }
    public String getTestStepDescription(){
        try {
            return (String) step.get(PropertyConfig.TEST_STEP_DESCRIPTION);
        }catch(Exception ex) {
            return null;
        }
    }

    public String getFieldName(){
        try {
            return (String) step.get(PropertyConfig.FIELD_NAME);
        }catch(Exception ex) {
            return null;
        }
    }

    public String getAction(){
        try {
            return (String) step.get(PropertyConfig.ACTION);
        }catch(Exception ex) {
            return null;
        }
    }

    public String getObjectLocator(){
        try {
            return (String) step.get(PropertyConfig.OBJECT_LOCATORS);
        }catch(Exception ex) {
            return null;
        }
    }

    public String getTestData(){
        try {
            return (String) step.get(PropertyConfig.TEST_DATA);
        }catch(Exception ex) {
            return null;
        }
    }
    public void setTestData(String testData){
        step.put(PropertyConfig.TEST_DATA,testData);
    }
    public boolean isCritical(){
        try {
            return  step.get(PropertyConfig.CRITICAL).toString().toLowerCase().equals("yes") ? true : false;
        }catch(Exception ex) {
            return false;
        }
    }

    public boolean isExecutionFlagOn(){
        try {
            return  step.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes") ? true : false;
        }catch(Exception ex) {
            return false;
        }
    }
    public String delayTime(){
        try {
            return (String) step.get(PropertyConfig.DELAY);
        }catch (Exception e){
            return null;
        }
    }

    public boolean isRefreshPageOn(){
        try{
            return  step.get(PropertyConfig.PAGE_REFRESH).toString().toLowerCase().equals("yes") ? true : false;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean isPassed() {
        return isPassed ;
    }

    public void setPassed(Boolean value) {
         isPassed = value ;
    }

    public Map getStep() {
        return step;
    }

    public void setStep(Map step) {
       // this.step = step;
        this.step = new HashMap<>(step);
    }
}
