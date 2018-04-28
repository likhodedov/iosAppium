package Tools;

import Tools.Distribute;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Artifact;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.apache.commons.io.IOUtils;
import org.apache.http.ConnectionClosedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import sun.security.krb5.internal.crypto.Des;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ConfigureDevice {
    public static String port;
    public static String deviceName;  //идентификатор устройства
    public static String platformVersion; // Версия устройства
    public static String udid; // Версия устройства
    public static int resize_factor;


    public static DesiredCapabilities getDevice(String devicename) throws URISyntaxException, IOException {
        DesiredCapabilities capabilities= new DesiredCapabilities();
        File appDir = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "test_builds" + File.separator);
        if (!appDir.exists() && appDir.isDirectory()) {
            appDir.mkdir();
        }
        Random rand = new Random();
        int n = rand.nextInt(100) + 1;
        File app;
        JenkinsServer jenkins = new JenkinsServer(new URI("http://jenkins.movavi.srv/"), "testrail",
                "006436088f74df66c6dc51841dda999d");
        Map<String, Job> jobs = jenkins.getJobs("Mobile");
        JobWithDetails job = jobs.get("Movavi_VideoEditor_iOS").details();
        BuildWithDetails buildWithDetails = job.getLastSuccessfulBuild().details();
        String buildnumber = buildWithDetails.getDisplayName();
        buildnumber = buildnumber.replaceAll("/","-");
        Map<String, String> ss = buildWithDetails.getParameters();
        String brunch = ss.get("branch");
        Distribute.message("BUILD: " + buildnumber + " brunch:" + brunch);
        List<Artifact> art = buildWithDetails.getArtifacts();
        app = new File(appDir, "(" + n + ")Build_" + buildnumber + ".ipa");
        String toFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "test_builds" + File.separator + "(" + n + ")Build_" + buildnumber + ".ipa";

        try {
            downloadArtifact(buildWithDetails,art,toFile);
        } catch (ConnectionClosedException e) {
            Distribute.error("Catch exception, try download again");
            downloadArtifact(buildWithDetails,art,toFile);
        }


        switch (devicename){
            case "961a911aa9951d1ff9e11a3cf046d37150ac5ce4":
                Distribute.message("DEVICE: iPhone_6");
                deviceName = "iPhone";
                platformVersion = "10.0.1";
                udid = "961a911aa9951d1ff9e11a3cf046d37150ac5ce4";
                resize_factor =3;
                break;
            case "bf37fda6d49e9d1082d8f33afbc207d139728dcb":
                Distribute.message("DEVICE: iPhone_7");
                deviceName =  "iPhone";
                platformVersion = "10.3.1";
                udid = "bf37fda6d49e9d1082d8f33afbc207d139728dcb";
                resize_factor =3;
                break;
            case "1e441aadef054f289c87e0bc701eb33086ded8a5":
                Distribute.message("DEVICE: iPhone_5s");
                deviceName = "iPhone";
                platformVersion = "9.3.5";
                udid = "1e441aadef054f289c87e0bc701eb33086ded8a5";
                resize_factor =3;
                break;
            case "4d5a4cc71ffbdb5ac387785b756e983e9b5b463c":
                Distribute.message("DEVICE: iPad_Air_2");
                deviceName = "iPad";
                platformVersion = "11.0";
                udid = "4d5a4cc71ffbdb5ac387785b756e983e9b5b463c";
                resize_factor =3;
                break;
            case "429b8d572b9944dd288fb74b6c5fdc38e99ba70c":
                Distribute.message("DEVICE: iPad_mini_2");
                deviceName = "iPad";
                platformVersion = "10.1.1";
                udid = "429b8d572b9944dd288fb74b6c5fdc38e99ba70c";
                resize_factor =3;
                break;
            case "a0e05949c1df0fa2c54789ea4d9e718ace715d35":
                Distribute.message("DEVICE: iPhone_8");
                deviceName = "iPhone";
                platformVersion = "11.0.3";
                udid = "a0e05949c1df0fa2c54789ea4d9e718ace715d35";
                resize_factor =2;
                break;
            case "88725d5114a53d54c2bc8e1da939a413c821f6cf":
                Distribute.message("DEVICE: iPhone_6S");
                deviceName = "iPhone";
                platformVersion = "11.2";
                udid = "88725d5114a53d54c2bc8e1da939a413c821f6cf";
                resize_factor =3;
                break;
            case "00d6fa432ea037d2768c947a7c8f1ef4c2f5333a":
                Distribute.message("DEVICE: iPhone_X");
                deviceName = "iPhone";
                platformVersion = "11.1.2";
                udid = "00d6fa432ea037d2768c947a7c8f1ef4c2f5333a";
                resize_factor =3;
        }
        capabilities.setCapability("deviceName",deviceName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("udid",udid);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("bundleId", "com.movavi.clips");
        capabilities.setCapability("xcodeOrgId", "U8KD48QDBK");
        capabilities.setCapability("automationName","XCUITest");
        capabilities.setCapability("newCommandTimeout", "180");
    return capabilities;
    }


    public static void downloadArtifact(BuildWithDetails buildWithDetails, List<Artifact> art,String toFile) throws IOException, URISyntaxException {
        InputStream in = buildWithDetails.downloadArtifact(art.get(0));

        File targetFile = new File(toFile);
        java.nio.file.Files.copy(
                in,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(in);
    }
}
