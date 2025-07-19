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

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class IntegratedAPI {

    // API credentials - replace with your actual credentials
    private static final String CLIENT_ID = "ef680338-d03a-4f18-a6ef-bf234b366313";
    private static final String SECRET_KEY = "a9bd4b3d-d068-48b7-9386-2e60b9fb401c";
    private static final String API_URL = "https://api.payshack.in/payshack-payin/api/v1/payin/ext/txn/initiate-intent";

    public static void main(String[] args) {
        // Input payload
        JSONObject payload = new JSONObject();
        payload.put("orderId", "12345ert0sd001");
        payload.put("amount", 300.00);
        payload.put("firstName", "test");
        payload.put("lastName", "test01");
        payload.put("email", "testvsol@gmail.com");
        payload.put("phone", "6360710083");
        payload.put("userIp", "10.0.0.0");
        payload.put("userId", "abc-123-456-789");

        String encryptionKey = "fc4f10e85d844d48430ba0c53c901be20df4eff4fc04f4967006b6e35a6f1d22";

        // 1. Convert payload to string
        String payloadStr = payload.toString();
        
        // 2. Encrypt payload
        String encryptedBody = systemEncryption(payloadStr, encryptionKey);

        // 3. Generate checksum (based on encryptedBody)
        String checksum = getChecksum(encryptedBody, encryptionKey);

        // Output
        System.out.println("encryptedBody: " + encryptedBody);
        System.out.println("checksum: " + checksum);

        // 4. Call Payshack API with the encrypted data and checksum
        callPayshackApi(encryptedBody, checksum);
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
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedBytes), StandardCharsets.UTF_8);
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

    public static void callPayshackApi(String encryptedBody, String checksum) {
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

            // Read successful response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("API Response: " + response.toString());
                }
            } else {
                // Read error response
                try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    System.out.println("API Error Response: " + errorResponse.toString());
                }
            }

            connection.disconnect();
        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}