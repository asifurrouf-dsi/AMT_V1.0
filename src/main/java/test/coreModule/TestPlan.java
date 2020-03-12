package test.coreModule;

import freemarker.ext.beans.HashAdapter;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

public class TestPlan {
    private static TestPlan TEST_PLAN = new TestPlan() ;
    private String testPlanName ;
    private List<TestModule> testModules;
    private LocalDateTime creationTime  ;
    private Map storeData = new HashMap<String,String>() ;

    public String getStoreData(String key) {
        return (String) storeData.get(key);
    }

    public void setStoreData(String key,String value) {
        storeData.put(key,value) ;
    }

    private TestPlan() {
        creationTime = LocalDateTime.now();
        testModules = new ArrayList<TestModule>();
    }

   public static  TestPlan getInstance() {
        return TEST_PLAN;
   }

    public String  getTestPlanName(){
        return this.testPlanName;
    }
    public void  setTestPlanName(String name){
         this.testPlanName = name ;
    }
    public LocalDateTime  getCreationTime(){
        return this.creationTime;
    }

    public void addTestModule(TestModule  testModule){
        testModules.add(testModule);
    }

    public TestModule getTestModule(String moduleName){
        Optional<TestModule>  testModule = testModules.stream().filter(tm -> tm.getModuleName().equals(moduleName)).findFirst() ;
        return  testModule.isPresent() ? testModule.get() : null ;

    }
    public String getUniqueData(){
        String hour = String.valueOf(creationTime.getHour());
        String minute = String.valueOf(creationTime.getMinute());
        if (hour.length()<2){
            hour = "0" + hour;
        }
        if (minute.length()<2){
            minute = "0" + minute;
        }
        return  hour + minute;
    }

    public List<TestModule>  getAllTesModules(){
        return  testModules ;
    }


}
