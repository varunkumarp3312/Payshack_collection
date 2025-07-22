//package payshack;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.Cipher;
//import javax.crypto.Mac;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.codec.binary.Hex;
//import org.json.JSONObject;
//
//public class IntegratedAPI {
//
//    // API credentials - replace with your actual credentials
//    private static final String CLIENT_ID = "42491eb1-ecff-44c6-96d7-c35533bf176d";
//    private static final String SECRET_KEY = "5dd40e7a-2ddf-4803-a180-602fe48df7f9";
//    private static final String API_URL = "https://api.payshack.in/indigate-payin-svc/api/v1/payin/ext/txn/status";
//
//    public static void main(String[] args) {
//        // Input payload
//        JSONObject payload = new JSONObject();
//        payload.put("orderId", "samff23456t00101");
//
//        String encryptionKey = "9e9d11b51cc1429b6dabdc64d636a7bb14bc8c99e57a689da7e3b9856bb2256d";
//
//        // 1. Convert payload to string
//        String payloadStr = payload.toString();
//        
//        // 2. Encrypt payload
//        String encryptedBody = systemEncryption(payloadStr, encryptionKey);
//
//        // 3. Generate checksum (based on encryptedBody)
//        String checksum = getChecksum(encryptedBody, encryptionKey);
//
//        // Output
//        System.out.println("encryptedBody: " + encryptedBody);
//        System.out.println("checksum: " + checksum);
//
//        // 4. Call Payshack API with the encrypted data and checksum
//        callPayshackApi(encryptedBody, checksum);
//    }
//
//    public static String deriveKey(String key) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
//            String hexHash = Hex.encodeHexString(hash);
//            return hexHash.substring(0, 32); // Take first 32 characters
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("SHA-256 algorithm not found", e);
//        }
//    }
//
//    public static String systemEncryption(String stringToEncrypt, String key) {
//        try {
//            String iv = "1234567890123456";
//            String aesKey = deriveKey(key);
//
//            // Prepare key and IV
//            SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
//            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
//
//            // Initialize cipher
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
//
//            // Encrypt
//            byte[] encryptedBytes = cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8));
//
//            // Return Base64 encoded string
//            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedBytes), StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            System.err.println("Encryption failed: " + e.getMessage());
//            return "Encryption Failed";
//        }
//    }
//
//    public static String getChecksum(String data, String key) {
//        try {
//            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//            sha256_HMAC.init(secret_key);
//
//            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
//            return Hex.encodeHexString(hash);
//        } catch (Exception e) {
//            throw new RuntimeException("Error calculating checksum", e);
//        }
//    }
//
//    public static void callPayshackApi(String encryptedBody, String checksum) {
//        try {
//            // Create request payload
//            JSONObject apiPayload = new JSONObject();
//            apiPayload.put("checksum", checksum);
//            apiPayload.put("encryptedBody", encryptedBody);
//
//            // Create connection
//            URL url = new URL(API_URL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("client-id", CLIENT_ID);
//            connection.setRequestProperty("secret-key", SECRET_KEY);
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setDoOutput(true);
//
//            // Write payload to connection
//            try(OutputStream os = connection.getOutputStream()) {
//                byte[] input = apiPayload.toString().getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // Get response code
//            int responseCode = connection.getResponseCode();
//            System.out.println("API Response Code: " + responseCode);
//
//            // Read successful response
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                try(BufferedReader br = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder response = new StringBuilder();
//                    String responseLine;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    System.out.println("API Response: " + response.toString());
//                }
//            } else {
//                // Read error response
//                try(BufferedReader br = new BufferedReader(
//                    new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder errorResponse = new StringBuilder();
//                    String errorLine;
//                    while ((errorLine = br.readLine()) != null) {
//                        errorResponse.append(errorLine.trim());
//                    }
//                    System.out.println("API Error Response: " + errorResponse.toString());
//                }
//            }
//
//            connection.disconnect();
//        } catch (Exception e) {
//            System.err.println("API call failed: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}



package payshack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class Status_check_api {

    // API credentials - replace with your actual credentials
    private static final String CLIENT_ID = "42491eb1-ecff-44c6-96d7-c35533bf176d";
    private static final String SECRET_KEY = "5dd40e7a-2ddf-4803-a180-602fe48df7f9";
    private static final String API_URL = "https://api.payshack.in/indigate-payin-svc/api/v1/payin/ext/txn/status";
    private static final String ENCRYPTION_KEY = "9e9d11b51cc1429b6dabdc64d636a7bb14bc8c99e57a689da7e3b9856bb2256d";

    public static void main(String[] args) {
        // Input payload
        JSONObject payload = new JSONObject();
        payload.put("orderId", "samff23456t00101");

        // 1. Convert payload to string
        String payloadStr = payload.toString();
        
        // 2. Encrypt payload
        String encryptedBody = systemEncryption(payloadStr, ENCRYPTION_KEY);

        // 3. Generate checksum (based on encryptedBody)
        String checksum = getChecksum(encryptedBody, ENCRYPTION_KEY);

        // Output
        System.out.println("Encrypted Request Body: " + encryptedBody);
        System.out.println("Checksum: " + checksum);

        // 4. Call Payshack API with the encrypted data and checksum
        String apiResponse = callPayshackApi(encryptedBody, checksum);
        
        // 5. Process and decrypt the API response
        if (apiResponse != null) {
            processApiResponse(apiResponse);
        }
    }

    public static String deriveKey(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
            String hexHash = Hex.encodeHexString(hash);
            return hexHash.substring(0, 32); // Take first 32 characters
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static String systemEncryption(String stringToEncrypt, String key) {
        try {
            String iv = "1234567890123456";
            String aesKey = deriveKey(key);

            // Prepare key and IV
            SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

            // Initialize cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            // Encrypt
            byte[] encryptedBytes = cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8));

            // Return Base64 encoded string
            return Base64.encodeBase64String(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Encryption failed: " + e.getMessage());
            return "Encryption Failed";
        }
    }

    public static String systemDecryption(String stringToDecrypt, String key) {
        try {
            String iv = "1234567890123456";
            String aesKey = deriveKey(key);

            // Prepare key and IV
            SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

            // Initialize cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            // Decode Base64 and decrypt
            byte[] encryptedBytes = Base64.decodeBase64(stringToDecrypt);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Return decrypted string
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Decryption failed: " + e.getMessage());
            return "Decryption Failed";
        }
    }

    public static String getChecksum(String data, String key) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating checksum", e);
        }
    }

    public static String callPayshackApi(String encryptedBody, String checksum) {
        try {
            // Create request payload
            JSONObject apiPayload = new JSONObject();
            apiPayload.put("checksum", checksum);
            apiPayload.put("encryptedBody", encryptedBody);

            // Create connection
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("client-id", CLIENT_ID);
            connection.setRequestProperty("secret-key", SECRET_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Write payload to connection
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = apiPayload.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("API Response Code: " + responseCode);

            // Read response
            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? 
                    connection.getInputStream() : 
                    connection.getErrorStream(), 
                    StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            connection.disconnect();
            return response.toString();
        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void processApiResponse(String apiResponse) {
        try {
            JSONObject jsonResponse = new JSONObject(apiResponse);
            System.out.println("\nRaw API Response: " + jsonResponse.toString(2));
            
            if (jsonResponse.getBoolean("success")) {
                String encryptedData = jsonResponse.getString("data");
                System.out.println("\nEncrypted Response Data: " + encryptedData);
                
                // Decrypt the data using the same encryption key
                String decryptedData = systemDecryption(encryptedData, ENCRYPTION_KEY);
                System.out.println("\nDecrypted Response Data: " + decryptedData);
                
                // Parse the decrypted JSON
                JSONObject dataJson = new JSONObject(decryptedData);
                System.out.println("\nParsed Transaction Data:");
                System.out.println("Order ID: " + dataJson.optString("orderId", "N/A"));
                System.out.println("Status: " + dataJson.optString("status", "N/A"));
                System.out.println("Amount: " + dataJson.optString("amount", "N/A"));
            } else {
                System.out.println("API request was not successful: " + jsonResponse.getString("message"));
            }
        } catch (Exception e) {
            System.err.println("Error processing API response: " + e.getMessage());
            e.printStackTrace();
        }
    }
}