package com.iceberg;

import com.kumkee.userAgent.UserAgent;
import com.kumkee.userAgent.UserAgentParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentLocal {
    public static void main( String[] args ) throws Exception {
//        String path = "/home/vaderwang/Github/Hadoop/data/100_access.log";
        String path = "/home/vaderwang/Videos/SparkSQL/data/access.20161111.log";

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));

        String line = "";

        UserAgentParser userAgentParser = new UserAgentParser();
        HashMap<String, Integer> map = new HashMap<>();
        while (line != null) {
            line = reader.readLine();
            System.out.println(line);
            if (line != null && !line.equals("")) {
                String source = line.substring(getCharacterPosition(line, "\"", 7) + 1);
                System.out.println(source);
                UserAgent agent = userAgentParser.parse(source);
                String browser = agent.getBrowser();
                String engine = agent.getEngine();
                String engineVersion = agent.getEngineVersion();
                String os = agent.getOs();
                String platform = agent.getPlatform();
                boolean mobile = agent.isMobile();

                map.put(browser, map.getOrDefault(browser, 0) + 1);
//                System.out.println(browser + " " + engine + " " + engineVersion + " " + os + " " + platform + " " + mobile);
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }

    private static int getCharacterPosition(String value, String operator, int index) {
        Matcher matcher = Pattern.compile(operator).matcher(value);
        int matchIndex = 0;
        while (matcher.find()) {
            matchIndex ++;
            if (matchIndex == index) {
                break;
            }
        }
        return matcher.start();
    }
}
