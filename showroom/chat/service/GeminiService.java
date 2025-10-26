package showroom.chat.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

/**
 * Handles communication with the Google Gemini API for generating chatbot responses.
 */
public class GeminiService {

    private static final String CONFIG_FILE = ".env";
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private final String apiKey;

    public GeminiService() {
        this.apiKey = loadApiKey();
    }

    /**
     * Loads the API key from the .env file.
     */
    private String loadApiKey() {
        try {
            // 1) Prefer OS environment variables
            String envUpper = System.getenv("GEMINI_API_KEY");
            if (envUpper != null && !envUpper.trim().isEmpty()) return envUpper.trim();

            String envLower = System.getenv("gemini_api_key");
            if (envLower != null && !envLower.trim().isEmpty()) return envLower.trim();

            String googleApiKey = System.getenv("GOOGLE_API_KEY");
            if (googleApiKey != null && !googleApiKey.trim().isEmpty()) return googleApiKey.trim();

            // 2) Fallback to .env file in working directory or classpath
            Properties props = new Properties();

            File file = new File(CONFIG_FILE);
            if (file.exists()) {
                props.load(new FileInputStream(file));
            } else {
                var inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
                if (inputStream != null) props.load(inputStream);
                else throw new IOException(".env file not found in project directory or resources folder.");
            }

            String key = props.getProperty("gemini_api_key");
            if (key == null || key.isEmpty())
                throw new IOException("Missing 'gemini_api_key' in .env file.");
            return key.trim();
        } catch (IOException e) {
            throw new RuntimeException("Error loading API key. Set GEMINI_API_KEY/GOOGLE_API_KEY env var or add gemini_api_key to .env", e);
        }
    }

    /**
     * Sends a prompt to the Gemini API and returns the AI-generated response.
     */
    public String generateResponse(String prompt) {
        try {
            URL url = java.net.URI.create(API_URL + "?key=" + apiKey).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String requestBody = createRequestBody(prompt);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                String errorResponse = "Unknown error";
                if (conn.getErrorStream() != null) {
                    try (Scanner scanner = new Scanner(conn.getErrorStream(), StandardCharsets.UTF_8)) {
                        errorResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "No error details.";
                    }
                }
                System.err.println("Gemini API Error: " + errorResponse);
                return "⚠️ I'm having trouble reaching the server. Please check your API key or internet connection.";
            }

            StringBuilder response = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                response.append(scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "");
            }

            return parseResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ An error occurred while processing your request. Please try again later.";
        }
    }

    /**
     * Creates the request body JSON sent to the Gemini API.
     */
    private String createRequestBody(String userPrompt) {
        String formattedPrompt = String.format(
                "You are BikeBot, an expert in motorcycles. " +
                "Provide detailed and friendly answers about bike brands, specs, maintenance, and comparisons. " +
                "Be clear, concise, and informative.\\n\\nUser: %s",
                userPrompt
        );

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"contents\":[{");
        sb.append("\"role\":\"user\",");
        sb.append("\"parts\":[{");
        sb.append("\"text\":\"").append(escapeJson(formattedPrompt)).append("\"");
        sb.append("}]");
        sb.append("}]");
        sb.append(",\"generationConfig\":{");
        sb.append("\"temperature\":0.7,");
        sb.append("\"topK\":40,");
        sb.append("\"topP\":0.95,");
        sb.append("\"maxOutputTokens\":1024");
        sb.append("}");
        sb.append("}");
        return sb.toString();
    }

    private String escapeJson(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': out.append("\\\""); break;
                case '\\': out.append("\\\\"); break;
                case '\b': out.append("\\b"); break;
                case '\f': out.append("\\f"); break;
                case '\n': out.append("\\n"); break;
                case '\r': out.append("\\r"); break;
                case '\t': out.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        String hex = Integer.toHexString(c);
                        out.append("\\u");
                        for (int j = hex.length(); j < 4; j++) out.append('0');
                        out.append(hex);
                    } else {
                        out.append(c);
                    }
            }
        }
        return out.toString();
    }

    /**
     * Parses the Gemini API JSON response and extracts the generated text.
     */
    private String parseResponse(String response) {
        try {
            // Quick path: extract first "text": "..." occurrence
            java.util.regex.Matcher m = java.util.regex.Pattern
                    .compile("\\\"text\\\"\\s*:\\s*\\\"(.*?)\\\"", java.util.regex.Pattern.DOTALL)
                    .matcher(response);
            if (m.find()) {
                String raw = m.group(1);
                return unescapeJson(raw).trim();
            }

            // Try to surface server error message if present
            java.util.regex.Matcher err = java.util.regex.Pattern
                    .compile("\\\"message\\\"\\s*:\\s*\\\"(.*?)\\\"", java.util.regex.Pattern.DOTALL)
                    .matcher(response);
            if (err.find()) {
                return "⚠️ API error: " + unescapeJson(err.group(1));
            }

            return "I couldn't generate a valid response. Please rephrase your question.";
        } catch (Exception e) {
            System.err.println("Error parsing API response: " + e.getMessage());
            return "Unexpected response format from Gemini API.";
        }
    }

    private String unescapeJson(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char n = s.charAt(++i);
                switch (n) {
                    case '"': out.append('"'); break;
                    case '\\': out.append('\\'); break;
                    case '/': out.append('/'); break;
                    case 'b': out.append('\b'); break;
                    case 'f': out.append('\f'); break;
                    case 'n': out.append('\n'); break;
                    case 'r': out.append('\r'); break;
                    case 't': out.append('\t'); break;
                    case 'u':
                        if (i + 4 < s.length()) {
                            String hex = s.substring(i + 1, i + 5);
                            try {
                                out.append((char) Integer.parseInt(hex, 16));
                                i += 4;
                            } catch (NumberFormatException ex) {
                                out.append("\\u").append(hex);
                                i += 4;
                            }
                        } else {
                            out.append('u');
                        }
                        break;
                    default: out.append(n); break;
                }
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
