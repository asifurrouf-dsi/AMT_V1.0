# Automation Test Framework
 
This is a keyword and data driven Testing Framework with cross browsing testing facility. The plan is writing  test steps with keyword and data in excel file. Every step has a action field, which is basically a class.method format, and it will be invoked with provided keyword and test data. Every excel file has a Execution-Flag field, if it is not 'yes' the corresponding  excel row is not considered for execution. There is a default property file, in where browser name can be provided. There are multiple excel files and sheets for test plan, modules, test suits, test cases and test steps.  
 
## Getting Started

### 1.ModuleController.xlsx (Test plan) :

![alt text](https://github.com/shahriar1628/seleniumTestingFramework/blob/master/readmdImage/tplan.PNG)
If Execution flag is any other value except yes, the module  will not be executed else executed. 


### 2.Module( Test suite, Test case, Test step) : 
module is a composite of multiple test suites. And test suites is actually a different tab in excel file. In test suite multiple test cases can be kept. 
![alt text](https://github.com/shahriar1628/seleniumTestingFramework/blob/master/readmdImage/tsuite.PNG)


![alt text](https://github.com/shahriar1628/seleniumTestingFramework/blob/master/readmdImage/tcase.PNG)

Here a test case is illustrated  located in  test suite(tab). Here  action is method name with class which need to be invoked. Test data keeps  data, and object-locators has  keyword reference. Format is “ExcelFileName.TabName.KeywordName.” 
Example : 
Common.Login.txtUserName. 
There is a keyword in common.xlsx in Login tab named txtUserName.  

###  3. Keyword(ObjectLocators) 
Keywords are kept in difference directory and sheet, There is multiple sheet, multiple tab for keyword record.  It just need proper reference in test cases. 
![alt text](https://github.com/shahriar1628/seleniumTestingFramework/blob/master/readmdImage/tObj.PNG)

###  3. Default.properties : 
It provides the various property.  At present in which browser  test cases will be executed, it can be mentioned.    


## Application running 
-> git clone https://github.com/shahriar1628/seleniumTestingFramework.git 

-> mvn install (for build and  running the sample application test)  

### Prerequisites
1.Java 8 

2.Maven 

## Running the tests 
-> mvn test -Dtest=UnitTesting test  ( for running unit test cases) 

-> mvn test -Dtest=AppTest  test  ( for running  sample test application.) 




 

