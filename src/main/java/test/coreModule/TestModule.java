package test.coreModule;


import test.utility.PropertyConfig;

import java.util.*;

public class TestModule {
    private String moduleName ;
    private List<TestSuite> testSuits ;
    private Map<String,Integer> testSuiteIndex;
    private Integer index = 0 ;
    private Integer state ;

    public TestModule(){
        testSuits = new ArrayList<TestSuite>() ;
        testSuiteIndex = new HashMap<String, Integer>();
        state = PropertyConfig.INIT ;
    }
    public TestModule(String name){
        testSuits = new ArrayList<TestSuite>() ;
        testSuiteIndex = new HashMap<String, Integer>();
        this.moduleName = name;
        state = PropertyConfig.INIT ;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String name){
        this.moduleName = name ;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state){
        this.state = state ;
    }
    public void addTestSuite(TestSuite testSuite ){
        testSuits.add(index,testSuite);
        testSuiteIndex.put(testSuite.getTestSuiteName(),index);
        index++;
    }

    public TestSuite getTestSuite(String testSuiteName){
        if(testSuiteIndex.containsKey(testSuiteName)){
            return testSuits.get(testSuiteIndex.get(testSuiteName));
        }
        return null;
    }

    public void updateTestSuite(TestSuite testSuite){
        if(testSuiteIndex.containsKey(testSuite.getTestSuiteName())){
            testSuits.set(testSuiteIndex.get(testSuite.getTestSuiteName()),testSuite);
        }
    }

    public Optional<TestSuite> getTestSuiteByTestCaseNumber(String testCaseNumber) {
      return getAllTestSuits().stream().filter(testSuite -> Optional.ofNullable(testSuite.getTestCase(testCaseNumber)).isPresent()).findFirst() ;
    }

    public List<TestSuite>  getAllTestSuits(){
        return  testSuits ;
    }
}
