
package test.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.Test;
import test.coreModule.TestCase;
import test.keywordScripts.UtilDate;


import java.util.HashMap;

public class CreateLog {

    ExtentReports extent;
    ExtentHtmlReporter reporter;
    HashMap<String,ExtentTest> storeAllLogger;

    public CreateLog(String fileName){

        reporter = new ExtentHtmlReporter("./Report/" + fileName + ".html");

        extent = new ExtentReports();

        extent.attachReporter(reporter);

        storeAllLogger = new HashMap<String, ExtentTest>();
    }

    @Test
    public void createLogger(String header, String categoryName){

        String key = header.trim();

        ExtentTest logger = extent.createTest(key);
        logger.assignCategory(categoryName);
        storeAllLogger.put(key , logger);

    }

    public void writeLog(String header, String message, boolean status){

        String key = header.trim();

        if (storeAllLogger.containsKey(key)){

            ExtentTest objExistLogger = storeAllLogger.get(key);


            if (status){
                objExistLogger.log(Status.PASS, message);
            }
            else {
                objExistLogger.log(Status.FAIL, message);
            }
            extent.flush();
        }


    }

    public void writeLog(String header, String message, boolean status,boolean skipped){

        String key = header.trim();

        if (storeAllLogger.containsKey(key)){

            ExtentTest objExistLogger = storeAllLogger.get(key);
            if(skipped)
                objExistLogger.log(Status.SKIP, message+"(Skipped)");
            else if (status){
                objExistLogger.log(Status.PASS, message);
            }
            else {
                objExistLogger.log(Status.FAIL, message);
            }
            extent.flush();
        }


    }


}

