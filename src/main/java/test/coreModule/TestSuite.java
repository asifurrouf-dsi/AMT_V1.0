package test.coreModule;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSuite {
    private String testSuiteName ;
    private List<TestCase> testCases ;
    private Map<String,Integer> testCaseIndex;
    private Integer index = 0 ;
    private Integer state ;

    public TestSuite(){
        testCases = new ArrayList<TestCase>() ;
        testCaseIndex = new HashMap<String, Integer>();
    }
    public TestSuite(String name){
        testCases = new ArrayList<TestCase>() ;
        testCaseIndex = new HashMap<String, Integer>();
        this.testSuiteName = name;
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public void setTestSuiteName(String name){
        this.testSuiteName = name ;
    }

    public void addTestCase(TestCase testCase ){
        testCases.add(index,testCase);
        testCaseIndex.put(testCase.getTestCaseNumber(),index);
        index++;
    }

    public TestCase getTestCase(String testCaseNumber){
        if(testCaseIndex.containsKey(testCaseNumber)){
            return testCases.get(testCaseIndex.get(testCaseNumber));
        }
        return null;
    }

    public void updateTestCase(TestCase testCase){
        if(testCaseIndex.containsKey(testCase.getTestCaseNumber())){
            testCases.set(testCaseIndex.get(testCase.getTestCaseNumber()),testCase);
        }
    }

    public Integer getState() {
         return this.state ;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<TestCase>  getAllTestCases(){
        return  testCases ;
    }
}
