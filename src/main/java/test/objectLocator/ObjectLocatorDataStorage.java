package test.objectLocator;

import java.util.HashMap;
import java.util.Map;

public class ObjectLocatorDataStorage {
    private static Map<String,Map> objectLocatorStorage = new HashMap<String,Map>() ;
   // private Map<String,Boolean> sheetCalled ;

    public ObjectLocatorDataStorage(){
    }

    public static Map getObjectLocator(String orPath) throws Exception {
        if(objectLocatorStorage.containsKey(orPath) && null != objectLocatorStorage.get(orPath)) {
            return objectLocatorStorage.get(orPath) ;
        } else {
            OrRead orRead = new OrRead(orPath);
            addObjectLocaor(orRead.getOrFromSheet());
            if(objectLocatorStorage.containsKey(orPath) && null != objectLocatorStorage.get(orPath)) {
                return objectLocatorStorage.get(orPath) ;
            } else {
                throw  new Exception("ObjectLocator not found.");
            }
        }
    }

    private static void addObjectLocaor(Map objectLocator){
        objectLocatorStorage.putAll(objectLocator);
    }
}
