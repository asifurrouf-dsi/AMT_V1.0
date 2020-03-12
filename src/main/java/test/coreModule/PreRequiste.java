package test.coreModule;

import test.utility.PropertyConfig;

import java.util.Optional;

public class PreRequiste {

    public PreRequiste() {

    }

    public Optional<TestCase>  getPrequisiteTestCase(String moduleName,String testCaseNumber) {
        try {
             TestModule testModule =  TestPlan.getInstance().getTestModule(moduleName) ;
            Optional<TestSuite> testSuite =  testModule.getTestSuiteByTestCaseNumber(testCaseNumber) ;
            if( !testSuite.isPresent())
                return Optional.empty() ;
            TestSuite ts = testSuite.get() ;
            if(!ts.getState().equals(PropertyConfig.CREATED))
                MainController.readTestSuite(ts,moduleName);
            TestCase testCase = ts.getTestCase(testCaseNumber) ;
            if(null == testCase)
                return  Optional.empty() ;
            TestCase newTestCase = new TestCase() ;
            newTestCase.setTestCaseName(  testCase.getTestCaseName());
            newTestCase.setTestCaseNumber( testCase.getTestCaseNumber());
            newTestCase.setTestSteps(testCase.getAllTestSteps());
            return Optional.ofNullable(newTestCase);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty() ;
        }
    }
}
