package test.objectLocator;

import test.utility.PropertyConfig;
import test.utility.ReadExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrRead {
    String orPath  ;

    public  OrRead(String path){
        this.orPath = path ;
    }
    public Map getOrFromSheet() {
        List<Map> orLists = new ArrayList<Map>() ;
        String[] splitPath = orPath.split("\\.");
        List<Map> orRecords = new ArrayList<Map>();
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = classLoader.getResource("objectLocator/"+"OR_"+splitPath[0]+".xlsx").getPath();
        ReadExcel readExcel = new ReadExcel(filePath);
        List<Map> records = readExcel.read(splitPath[1]);
        Map objectLocator = new HashMap();
        for(Map map: records) {
           String objectReference = (String) map.get(PropertyConfig.OBJECT_REFERENCE) ;
            String objectKey = splitPath[0] + "." +  splitPath[1] + "." +  objectReference ;
            objectLocator.put(objectKey,map) ;
        }
        return objectLocator ;
    }
}
