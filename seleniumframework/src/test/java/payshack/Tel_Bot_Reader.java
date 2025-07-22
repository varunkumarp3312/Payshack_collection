//package payshack;
//
//import okhttp3.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.IOException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//public class Tel_Bot_Reader {
//    // Use the correct token from your original sender bot
//    private static final String BOT_TOKEN = "7593774104:AAFZ1NzlYZLmIjAL72h5N9MbIVNbfqMQ2I8";
//    private static final String CHAT_ID = "-1002874195441";
//    private static long lastUpdateId = 0;
//
//    public static void main(String[] args) {
//        System.out.println("Starting Telegram message reader with token: " + BOT_TOKEN);
//        System.out.println("Monitoring chat: " + CHAT_ID);
//        System.out.println("Will check for new messages every 10 seconds");
//        
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        
//        scheduler.scheduleAtFixedRate(() -> {
//            try {
//                System.out.println("\n" + getCurrentTimestamp() + " - Checking for new messages...");
//                readNewMessages();
//            } catch (Exception e) {
//                System.err.println(getCurrentTimestamp() + " - Error: " + e.getMessage());
//            }
//        }, 0, 10, TimeUnit.SECONDS);
//    }
//
//    private static void readNewMessages() throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/getUpdates?offset=" + (lastUpdateId + 1);
//        
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String responseBody = response.body().string();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            
//            if (!jsonResponse.getBoolean("ok")) {
//                System.err.println(getCurrentTimestamp() + " - API Error: " + responseBody);
//                return;
//            }
//
//            JSONArray updates = jsonResponse.getJSONArray("result");
//            System.out.println(getCurrentTimestamp() + " - Available updates: " + updates.length());
//            
//            for (int i = 0; i < updates.length(); i++) {
//                JSONObject update = updates.getJSONObject(i);
//                lastUpdateId = update.getLong("update_id");
//                
//                if (update.has("message")) {
//                    JSONObject message = update.getJSONObject("message");
//                    long chatId = message.getJSONObject("chat").getLong("id");
//                    
//                    if (Long.toString(chatId).equals(CHAT_ID)) {
//                        processMessage(message);
//                    } else {
//                        System.out.println(getCurrentTimestamp() + " - Message from other chat: " + chatId);
//                    }
//                } else if (update.has("channel_post")) {
//                    System.out.println(getCurrentTimestamp() + " - Channel post detected");
//                } else {
//                    System.out.println(getCurrentTimestamp() + " - Unknown update type");
//                }
//            }
//        }
//    }
//
//    private static void processMessage(JSONObject message) {
//        try {
//            String sender = "Unknown";
//            if (message.getJSONObject("from").has("first_name")) {
//                sender = message.getJSONObject("from").getString("first_name");
//            }
//
//            if (message.has("text")) {
//                String text = message.getString("text");
//                System.out.println(getCurrentTimestamp() + " - NEW MESSAGE - From: " + sender + " | Text: " + text);
//            } else if (message.has("photo")) {
//                System.out.println(getCurrentTimestamp() + " - PHOTO MESSAGE - From: " + sender);
//            } else if (message.has("document")) {
//                System.out.println(getCurrentTimestamp() + " - FILE MESSAGE - From: " + sender);
//            } else {
//                System.out.println(getCurrentTimestamp() + " - UNSUPPORTED MESSAGE TYPE - From: " + sender);
//            }
//        } catch (Exception e) {
//            System.err.println(getCurrentTimestamp() + " - Error processing message: " + e.getMessage());
//        }
//    }
//
//    private static String getCurrentTimestamp() {
//        return java.time.LocalDateTime.now().toString();
//    }
//}

package payshack;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tel_Bot_Reader {
    private static final String BOT_TOKEN = "7593774104:AAFZ1NzlYZLmIjAL72h5N9MbIVNbfqMQ2I8";
    private static final String CHAT_ID = "-1002874195441";
    private static final Pattern TRANSACTION_CODE_PATTERN = 
        Pattern.compile("[A-Za-z0-9]{20,}"); // Pattern for alphanumeric codes >20 chars
    private static long lastUpdateId = 0;

    public static void main(String[] args) {
        System.out.println("Starting Transaction Code Extractor...");
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("\n" + getCurrentTimestamp() + " - Checking for new messages...");
                checkForTransactionCodes();
            } catch (Exception e) {
                System.err.println(getCurrentTimestamp() + " - Error: " + e.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private static void checkForTransactionCodes() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/getUpdates?offset=" + (lastUpdateId + 1);
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            
            if (!jsonResponse.getBoolean("ok")) {
                System.err.println(getCurrentTimestamp() + " - API Error: " + responseBody);
                return;
            }

            JSONArray updates = jsonResponse.getJSONArray("result");
            System.out.println(getCurrentTimestamp() + " - Available updates: " + updates.length());
            
            for (int i = 0; i < updates.length(); i++) {
                JSONObject update = updates.getJSONObject(i);
                lastUpdateId = update.getLong("update_id");
                
                if (update.has("message")) {
                    processMessage(update.getJSONObject("message"));
                }
            }
        }
    }

    private static void processMessage(JSONObject message) {
        try {
            // Verify it's from our target chat
            long chatId = message.getJSONObject("chat").getLong("id");
            if (!Long.toString(chatId).equals(CHAT_ID)) return;

            // Get message text (could be in text or caption for photos)
            String text = "";
            if (message.has("text")) {
                text = message.getString("text");
            } else if (message.has("caption")) {
                text = message.getString("caption");
            }

            if (!text.isEmpty()) {
                extractTransactionCode(text);
            }
        } catch (Exception e) {
            System.err.println(getCurrentTimestamp() + " - Error processing message: " + e.getMessage());
        }
    }

    private static void extractTransactionCode(String text) {
        Matcher matcher = TRANSACTION_CODE_PATTERN.matcher(text);
        while (matcher.find()) {
            String potentialCode = matcher.group();
            // Additional validation for transaction codes
            if (potentialCode.length() >= 20 && potentialCode.length() <= 40) {
                System.out.println(getCurrentTimestamp() + " - FOUND TRANSACTION CODE: " + potentialCode);
                
                // Here you can add code to store/process the found code
                // For example: sendToDatabase(potentialCode);
            }
        }
    }

    private static String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString();
    }
}


