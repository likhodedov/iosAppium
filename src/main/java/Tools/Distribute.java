package Tools;

import org.testng.Reporter;


public class Distribute {
    //private final Logger logger = LogManager.getLogger(AllureLogger.class);
    public static void message(String s){
        System.out.println(s);
        Reporter.log(s);
    }
    public static void error(String s){
        System.err.println(s);
        Reporter.log(s);
    }
}
