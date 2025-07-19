package phonepelogin;

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

public class UpdatedAPI {

    // API credentials - replace with your actual credentials
    private static final String CLIENT_ID = "42491eb1-ecff-44c6-96d7-c35533bf176d";
    private static final String SECRET_KEY = "5dd40e7a-2ddf-4803-a180-602fe48df7f9";
    private static final String API_URL = "https://api.payshack.in/payshack-payin/api/v1/payin/ext/txn/initiate-intent";
    private static final String ENCRYPTION_KEY = "9e9d11b51cc1429b6dabdc64d636a7bb14bc8c99e57a689da7e3b9856bb2256d";

    public static void main(String[] args) {
        // Input payload
        JSONObject payload = new JSONObject();
        payload.put("orderId", "1hgyssert0sd001");
        payload.put("amount", 300.00);
        payload.put("firstName", "test");
        payload.put("lastName", "test01");
        payload.put("email", "testvsol@gmail.com");
        payload.put("phone", "6360710083");
        payload.put("userIp", "10.0.0.0");
        payload.put("userId", "abc-123-456-789");

        // 1. Convert payload to string
        String payloadStr = payload.toString();
        
        // 2. Encrypt payload
        String encryptedBody = systemEncryption(payloadStr, ENCRYPTION_KEY);

        // 3. Generate checksum (based on encryptedBody)
        String checksum = getChecksum(encryptedBody, ENCRYPTION_KEY);

        // Output
        System.out.println("Request encryptedBody: " + encryptedBody);
        System.out.println("Request checksum: " + checksum);

        // 4. Call Payshack API with the encrypted data and checksum
        String apiResponse = callPayshackApi(encryptedBody, checksum);
        
        // 5. Process and decrypt the API response
        if (apiResponse != null) {
            processApiResponse(apiResponse);
        }
    }

    public static void processApiResponse(String apiResponse) {
        try {
            JSONObject responseJson = new JSONObject(apiResponse);
            System.out.println("\nRaw API Response:");
            System.out.println(responseJson.toString(2)); // Pretty print
            
            if (responseJson.has("data")) {
                String encryptedData = responseJson.getString("data");
                System.out.println("\nEncrypted Response Data:");
                System.out.println(encryptedData);
                
                String decryptedData = systemDecryption(encryptedData, ENCRYPTION_KEY);
                System.out.println("\nDecrypted Response Data:");
                System.out.println(decryptedData);
                
                // If the decrypted data is JSON, parse and display it
                try {
                    JSONObject decryptedJson = new JSONObject(decryptedData);
                    System.out.println("\nFormatted Decrypted Data:");
                    System.out.println(decryptedJson.toString(2));
                } catch (Exception e) {
                    System.out.println("Decrypted data is not JSON format");
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing API response: " + e.getMessage());
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

            // Decrypt
            byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(stringToDecrypt));
            
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Decryption failed: " + e.getMessage());
            return "Decryption Failed";
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
            return new String(Base64.encodeBase64(encryptedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Encryption failed: " + e.getMessage());
            return "Encryption Failed";
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
            System.out.println("\nAPI Response Code: " + responseCode);

            // Read response
            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    (responseCode == HttpURLConnection.HTTP_OK) ? 
                    connection.getInputStream() : connection.getErrorStream(), 
                    StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            String responseString = response.toString();
            System.out.println("API Raw Response: " + responseString);
            
            connection.disconnect();
            return responseString;
        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}