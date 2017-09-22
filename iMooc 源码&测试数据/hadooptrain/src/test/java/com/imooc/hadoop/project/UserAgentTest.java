package com.imooc.hadoop.project;

import com.kumkee.userAgent.UserAgent;
import com.kumkee.userAgent.UserAgentParser;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UserAgent测试类
 */
public class UserAgentTest {

    @Test
    public void testReadFile() throws Exception{

        String path = "/Users/rocky/data/imooc/10000_access.log";

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(path)))
        );

        String line = "";
        int i = 0;

        Map<String, Integer> browserMap = new HashMap<String, Integer>();

        UserAgentParser userAgentParser = new UserAgentParser();
        while(line != null) {
            line = reader.readLine(); //一次读入一行数据
            i++;
            if (StringUtils.isNotBlank(line)) {
                String source = line.substring(getCharacterPosition(line, "\"",7)) + 1;
                UserAgent agent = userAgentParser.parse(source);
                String browser = agent.getBrowser();
                String engine = agent.getEngine();
                String engineVersion = agent.getEngineVersion();
                String os = agent.getOs();
                String platform = agent.getPlatform();
                boolean isMoblie = agent.isMobile();

                Integer browserValue = browserMap.get(browser);
                if (browserValue != null) {
                    browserMap.put(browser, browserValue +1);
                } else {
                    browserMap.put(browser, 1);
                }


//                System.out.println(browser + " , " + engine + " , " + engineVersion
//                        + " , " + os + " , " + platform + " , " + isMoblie);
            }
        }

        System.out.println("UserAgentTest.testReadFile, records count: " + i);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");

        for (Map.Entry<String, Integer>  entry : browserMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }


    /**
     * 测试自定义方式
     */
    @Test
    public void testGetCharacterPosition() {
        String value = "117.35.88.11 - - [10/Nov/2016:00:01:02 +0800] \"GET /article/ajaxcourserecommends?id=124 HTTP/1.1\" 200 2345 \"www.imooc.com\" \"http://www.imooc.com/code/1852\" - \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36\" \"-\" 10.100.136.65:80 200 0.616 0.616";
        //String value = "183.162.52.7 - - [10/Nov/2016:00:01:02 +0800] \"POST /api3/getadv HTTP/1.1\" 200 813 \"www.imooc.com\" \"-\" cid=0&timestamp=1478707261865&uid=2871142&marking=androidbanner&secrect=a6e8e14701ffe9f6063934780d9e2e6d&token=f51e97d1cb1a9caac669ea8acc162b96 \"mukewang/5.0.0 (Android 5.1.1; Xiaomi Redmi 3 Build/LMY47V),Network 2G/3G\" \"-\" 10.100.134.244:80 200 0.027 0.027";
        int index = getCharacterPosition(value,"\"",7);
        System.out.println(index);
    }

    /**
     * 获取指定字符串中指定标识的字符串出现的索引位置
     */
    private int getCharacterPosition(String value, String operator, int index) {
        Matcher slashMatcher = Pattern.compile(operator).matcher(value);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;

            if (mIdx == index) {
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 单元测试：UserAgent工具类的使用
     */
    @Test
    public void testUserAgentParser() {

//    public static void main(String[] args) {

        //String source = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
        String source = "mukewang/5.0.0 (Android 5.1.1; Xiaomi Redmi 3 Build/LMY47V),Network 2G/3G";
        UserAgentParser userAgentParser = new UserAgentParser();
        UserAgent agent = userAgentParser.parse(source);

        String browser = agent.getBrowser();
        String engine = agent.getEngine();
        String engineVersion = agent.getEngineVersion();
        String os = agent.getOs();
        String platform = agent.getPlatform();
        boolean isMoblie = agent.isMobile();


        System.out.println(browser + " , " + engine + " , " + engineVersion
                + " , " + os + " , " + platform + " , " + isMoblie);
//    }
    }
}
