package Tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemCalls {

    public static String[] getDeviceID() {

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        String s = null;
        int i = 0;
        ArrayList<String> ar = new ArrayList<>();

        try {
            proc = rt.exec("instruments -s devices");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        try {
            while ((s = stdInput.readLine()) != null) {
                if ((!s.contains("Simulator"))&&(!s.contains("macpc-1203"))) {
                    Pattern pattern = Pattern.compile("(\\[+[a-zA-Z0-9_-]*+\\])");
                    Matcher matcher = pattern.matcher(s);
                    if(matcher.find()) {
                        s = matcher.group(0);
                        s = s.replaceAll("\\[","");
                        s = s.replaceAll("\\]","");
                        ar.add(s);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] stockArr = new String[ar.size()];
        return ar.toArray(stockArr);
    }

}
