package phonepelogin;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

public class EncryptionUtils {

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

        String key = "fc4f10e85d844d48430ba0c53c901be20df4eff4fc04f4967006b6e35a6f1d22";

        // 1. Convert payload to string
        String payloadStr = payload.toString();
        
        // 2. Encrypt payload
        String encryptedBody = systemEncryption(payloadStr, key);

        // 3. Generate checksum (based on encryptedBody)
        String checksum = getChecksum(encryptedBody, key);

        // Output
        System.out.println("encryptedBody: " + encryptedBody);
        System.out.println("checksum: " + checksum);
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
}

