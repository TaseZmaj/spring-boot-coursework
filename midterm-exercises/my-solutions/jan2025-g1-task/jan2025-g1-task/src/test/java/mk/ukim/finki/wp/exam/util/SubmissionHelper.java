package mk.ukim.finki.wp.exam.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public class SubmissionHelper {
    private static Long examId = 29987L;
    public static String index;
    public static String exam;
    public static Long sum = 0l;
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static ArrayList<String> log = new ArrayList<>();
    public static ArrayList<Exception> errors = new ArrayList<>();
    public static boolean hasError = false;
    public static String test;
    public static int testPoints = 0;

    public static void submitSource(Map<String, String> content) throws JsonProcessingException {
        if (!isValidIndex(index)) {
            System.out.println("ERROR!!! NOT A VALID INDEX!!!");
            return;
        }

        String solution = objectMapper.writeValueAsString(content);
        String logString = objectMapper.writeValueAsString(log);
        String key = generateKey();
        System.out.println(key);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("examName", exam);
        map.add("index", index);
        map.add("solution", solution);
        map.add("log", logString);
        map.add("key", key);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://185.153.49.149/submit", request, String.class);

        System.err.println("SUCCESS SUBMIT");
    }

    public static void startTest(String testName, int points) {
        if (!isValidIndex(index)) {
            System.out.println("ERROR!!! NON A VALID INDEX!!!");
            return;
        }

        test = testName;
        testPoints = points;
        hasError = false;
        errors.clear();
        log.add(String.format("S;%s;Started", testName));
    }

    public static void endTest() {
        log.add(String.format("E;%s;%s", test, hasError ? "FAILED" : "PASSED"));
        if (!hasError) {
            sum += testPoints;
        }

        showTestLog();
        test = null;
        testPoints = 0;

        if (hasError) {
            logErrors();
            throw new ExamAssertionException(test + " failed", "PASSED", "FAILED");
        }
    }

    public static void submitGrade() {
        String studentIndex = System.getenv("student_index");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String auth = System.getenv("WP_USER") + ":" + System.getenv("WP_PASS");
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grade", sum + "");
        map.add("student_index", studentIndex);
        map.add("exam_id", examId + "");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        System.err.println(studentIndex);
        System.err.println(sum);
        ResponseEntity<String> response = restTemplate.postForEntity("http://wp-grading/submit", request, String.class);

        System.err.println("SUCCESS SUBMIT");
    }


    public static void submitSuccessAssert(String message, Object expected, Object actual) {
        log.add(String.format("O;%s;%s", test, message));
    }

    public static void submitFailedAssert(String message, Object expected, Object actual) {
        log.add(String.format("X;%s;%s:\texpected: <%s>\tactual:\t<%s>", test, message, expected.toString(), actual.toString()));
        errors.add(new ExamAssertionException(message, expected, actual));
        hasError = true;
    }

    public static void logErrors() {
        for (Exception error : errors) {
            error.printStackTrace();
        }
    }

    private static void showTestLog() {
        for (String s : SubmissionHelper.log) {
            if (!s.contains(test)) continue;
            if (s.startsWith("X")) {
                System.err.println("----" + s);
            } else if (s.startsWith("S")) {
                System.err.println("\n====================================\n" + s);
            } else {
                System.err.println("    " + s);
            }
        }
        System.err.println("\n====================================\n\n");
    }

    public static boolean isValidIndex(String index) {
        if (index == null || index.isEmpty() || index.equals("TODO")) {
            return false;
        }
        return index.matches("\\d{6}");
    }

    private static String generateKey() {
        try {
            ArrayList<String> macAddresses = getAllMacAddresses();
            Collections.sort(macAddresses);
            String systemIdentifier = System.getProperty("os.name") + "_" + System.getProperty("os.arch");

            StringBuilder combined = new StringBuilder();
            for (String mac : macAddresses) {
                combined.append(mac).append("_");
            }
            combined.append(systemIdentifier);

            return hashString(combined.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR: default key";
    }

    private static ArrayList<String> getAllMacAddresses() throws Exception {
        ArrayList<String> macAddresses = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface network = interfaces.nextElement();
            if (network.isLoopback() || network.isVirtual() || !network.isUp()) {
                continue;
            }
            byte[] mac = network.getHardwareAddress();
            if (mac != null) {
                StringBuilder macAddress = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                macAddresses.add(macAddress.toString());
            }
        }
        Collections.sort(macAddresses);
        return macAddresses;
    }

    private static String hashString(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}