# BikeBot - Smart Motorcycle Chatbot

A Java-based chatbot assistant for motorcycle enthusiasts, powered by Google's Gemini AI. Ask about bike specs, comparisons, maintenance tips, and more!

---

## 📋 Prerequisites

### System Requirements
- **Java**: JDK 11 or higher
- **Internet**: Active internet connection (for Gemini API calls)
- **OS**: Windows, macOS, or Linux

### Verify Java Installation
Open a terminal/PowerShell and run:
```bash
java -version
javac -version
```

If not installed, download from [oracle.com/java](https://www.oracle.com/java/technologies/downloads/)

---

## 🔑 Setup: Get Your API Key

1. **Go to Google AI Studio**: https://aistudio.google.com/app/apikeys
2. **Sign in** with your Google account
3. **Create API Key** or copy an existing one
4. **Copy the key** (format: `AIzaSy...`)

---

## ⚙️ Configuration

### Option 1: Using `.env` File (Recommended)
1. Locate the `.env` file in the project root:
   ```
   BIKESHOWROOMMANAGEMENT/.env
   ```

2. Edit it and add/update:
   ```properties
   gemini_api_key=YOUR_API_KEY_HERE
   ```

   Replace `YOUR_API_KEY_HERE` with your actual key from Google AI Studio.

3. Save the file.

### Option 2: Using Environment Variables
Instead of `.env`, set a system environment variable:

#### Windows (PowerShell)
```powershell
$env:GEMINI_API_KEY = "YOUR_API_KEY_HERE"
```

#### Windows (Command Prompt)
```cmd
setx GEMINI_API_KEY "YOUR_API_KEY_HERE"
```

#### macOS/Linux (Terminal)
```bash
export GEMINI_API_KEY="YOUR_API_KEY_HERE"
```

The service will check environment variables first, then fall back to `.env`.

---

## 🚀 How to Run

### Step 1: Navigate to Project Directory
```powershell
cd "c:\Users\91974\OneDrive\Desktop\BIKESHOWROOMMANAGEMENT"
```

### Step 2: Compile the Code
```powershell
javac -d bin showroom/chat/service/GeminiService.java showroom/chat/model/ChatMessage.java showroom/chat/view/ChatbotPage.java
```

**Note**: If `bin` folder doesn't exist, create it first:
```powershell
mkdir bin
```

### Step 3: Run the Chatbot
```powershell
java -cp bin showroom.chat.view.ChatbotPage
```

A GUI window titled **"BikeBot - Your Smart Bike Assistant"** will appear.

---

## 💬 Using the Chatbot

### What You'll See
- **Welcome Message**: Greeting from BikeBot
- **Chat Area**: Message bubbles (orange = your messages, gray = bot responses)
- **Input Field**: Type your question at the bottom
- **Send Button**: Click or press Enter to send

### Example Questions
- "Tell me about Hero Splendor specifications"
- "Compare Honda CB350 vs Royal Enfield Bullet"
- "What maintenance tips for Bajaj Pulsar?"
- "Best bikes for daily commute?"
- "How to maintain bike chains?"

### Tips
- Be clear and concise with questions
- One question per message works best
- Bot may take a few seconds to respond (normal API latency)
- Timestamps show when each message was sent

---

## 🛠️ Troubleshooting

### ❌ "Could not initialize Gemini Service"
**Cause**: API key not found or incorrectly configured.

**Solutions**:
1. Verify `.env` file exists in project root
2. Check `.env` contains: `gemini_api_key=YOUR_KEY`
3. Ensure no extra spaces around the key
4. Restart terminal/IDE after setting env variables

### ❌ "models/gemini-pro is not found"
**Cause**: Using outdated API model.

**Solution**: Code is already updated to `gemini-2.0-flash`. Recompile:
```powershell
javac -d bin showroom/chat/service/GeminiService.java
```

### ❌ "I'm having trouble reaching the server"
**Cause**: Network issue or API key invalid.

**Solutions**:
1. Check internet connection
2. Verify API key is correct in Google AI Studio
3. Ensure API key has proper permissions
4. Check if quota limit is exceeded (Google dashboard)

### ❌ "Compilation fails with symbol errors"
**Cause**: Files not in correct locations.

**Solutions**:
1. Verify file structure:
   ```
   showroom/
   ├── chat/
   │   ├── model/ChatMessage.java
   │   ├── service/GeminiService.java
   │   └── view/ChatbotPage.java
   ```

2. Run from project root directory

### ❌ "Port already in use" or "Window won't open"
**Cause**: Another instance is running.

**Solution**:
1. Close existing ChatbotPage window
2. Kill Java process:
   ```powershell
   Get-Process java | Stop-Process
   ```
3. Run again

---

## 📁 Project Structure

```
BIKESHOWROOMMANAGEMENT/
├── .env                              # API key configuration
├── CHATBOT_README.md                 # This file
├── bin/                              # Compiled classes (auto-created)
└── showroom/
    └── chat/
        ├── model/
        │   └── ChatMessage.java      # Message data model
        ├── service/
        │   └── GeminiService.java    # API communication
        └── view/
            └── ChatbotPage.java      # GUI interface
```

---

## 🔒 Security Notes

- **Never** commit `.env` with your API key to version control
- Add `.env` to `.gitignore`:
  ```
  .env
  *.env
  ```
- API keys are treated as secrets — rotate them if exposed
- Requests are sent over HTTPS to Google's servers

---

## 📚 File Descriptions

### `GeminiService.java`
- Handles API communication with Google Gemini
- Loads API key from environment or `.env`
- Sends prompts and parses responses
- Includes error handling for network/API failures

### `ChatMessage.java`
- Simple data model for chat messages
- Stores message text, sender (user/bot), and timestamp

### `ChatbotPage.java`
- Swing-based GUI for the chat interface
- Async message sending (UI stays responsive)
- Styled message bubbles with timestamps
- Welcome message on startup

---

## 🐛 Reporting Issues

If the chatbot doesn't work:
1. Check API key validity in Google AI Studio
2. Verify `.env` or environment variable is set correctly
3. Try from project root directory
4. Check console output for detailed error messages
5. Ensure Java version is 11+

---

## 📝 License

This project is part of the Bike Showroom Management system.

---

## 🎯 Future Enhancements

- [ ] Add chat history persistence
- [ ] Support for multiple languages
- [ ] Bike model database integration
- [ ] User authentication
- [ ] Advanced filtering/search

---

## ✅ Quick Start (TL;DR)

```powershell
# 1. Set API key in .env or environment
# 2. Navigate to project
cd "c:\Users\91974\OneDrive\Desktop\BIKESHOWROOMMANAGEMENT"

# 3. Compile
javac -d bin showroom/chat/service/GeminiService.java showroom/chat/model/ChatMessage.java showroom/chat/view/ChatbotPage.java

# 4. Run
java -cp bin showroom.chat.view.ChatbotPage

# 5. Type questions and press Enter!
```

Enjoy chatting with BikeBot! 🏍️💬
