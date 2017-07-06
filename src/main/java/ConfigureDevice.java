import org.openqa.selenium.remote.DesiredCapabilities;
import sun.security.krb5.internal.crypto.Des;

/**
 * Created by test_user_1 on 04.07.17.
 */
public class ConfigureDevice {

        private final String Applink="";

    public static DesiredCapabilities SelectoriOS(String devicename){
        DesiredCapabilities capabilities= new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("app", "/Users/test_user_1/Downloads/Clip&Go.ipa");
        capabilities.setCapability("bundleId", "com.movavi.clips");
        capabilities.setCapability("xcodeOrgId", "U8KD48QDBK");

        switch (devicename){
            case "iPhone_6":
                capabilities.setCapability("deviceName", "iPhone");
                capabilities.setCapability("platformVersion", "10.0.1");
                capabilities.setCapability("udid","961a911aa9951d1ff9e11a3cf046d37150ac5ce4");
                break;
            case "iPhone_7":
                capabilities.setCapability("deviceName", "iPhone");
                capabilities.setCapability("platformVersion", "10.3.1");
                capabilities.setCapability("udid","bf37fda6d49e9d1082d8f33afbc207d139728dcb");
                break;
            case "iPhone_5s":
                capabilities.setCapability("deviceName", "iPhone");
                capabilities.setCapability("platformVersion", "9.3.5");
                capabilities.setCapability("udid","1e441aadef054f289c87e0bc701eb33086ded8a5");
                break;
                case "iPad_Air_2":
                capabilities.setCapability("deviceName", "iPad");
                capabilities.setCapability("platformVersion", "10.2.1");
                capabilities.setCapability("udid","4d5a4cc71ffbdb5ac387785b756e983e9b5b463c");
                break;
            case "iPad_mini_2":
                capabilities.setCapability("deviceName", "iPad");
                capabilities.setCapability("platformVersion", "10.2.1");
                capabilities.setCapability("udid","429b8d572b9944dd288fb74b6c5fdc38e99ba70c");
                break;
        }
    return capabilities;
    }
}
