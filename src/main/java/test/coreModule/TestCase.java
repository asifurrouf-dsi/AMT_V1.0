package test.coreModule;


import java.util.*;

public class TestCase {
    private String testCaseNumber;
    private String testCaseName;
    private String categoryName;
    private List<TestStep> testSteps ;
    private boolean passed ;

    private Optional<Map> preqUtilData = Optional.empty() ;

    public TestCase(){
        testSteps = new ArrayList<TestStep>() ;
    }
    public TestCase(String testCaseNumber){
        testSteps = new ArrayList<TestStep>() ;
        this.testCaseNumber = testCaseNumber.split("\\.")[0];
    }

    public String getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(String number){
        this.testCaseNumber = number ;
    }
    public String getTestCaseName(){
        return testCaseName;
    }

    public void setTestCaseName(String name){
        this.testCaseName= name;
    }
    public void setcategoryName(String name){
        this.categoryName= name;
    }
    public String getcategoryName(){
        return categoryName;
    }
    public void addTestStep(TestStep testStep ){
        testSteps.add(testStep);
    }

    public List<TestStep>  getAllTestSteps(){
        return  testSteps ;
    }

    public void setTestSteps(List<TestStep> tsteps){
         List<TestStep> steps = new ArrayList<TestStep>() ;
         for(TestStep tstp : tsteps) {
             TestStep testStep = new TestStep();
             testStep.setStep(tstp.getStep());
             steps.add(testStep) ;
         }
         this.testSteps = steps;

    }

    public boolean isPassed() {
        return  passed;
    }
    public void setPassed(Boolean val){
        passed = val ;
    }

    public void setPreqData(Optional<Map> data) {
        this.preqUtilData = data ;
    }

    public Optional<Map> getPreqData() {
        return this.preqUtilData ;
    }

}
