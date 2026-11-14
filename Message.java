/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Handles message creation, hashing, storing and printing.
 */
package chatapp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String sender;

    private static JSONArray storedMessagesJSON = new JSONArray();

    public Message(int messageNumber, String recipient, String messageText, String sender) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.sender = sender;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long number = 1000000000L + (long) (rand.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getSender() { return sender; }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        String regex = "^\\+\\d{11,13}$";
        if (recipient.matches(regex)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                storeMessage();
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public static void loadStoredMessages() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("messages.json")) {
            Object obj = parser.parse(reader);
            storedMessagesJSON = (JSONArray) obj;
        } catch (Exception e) {
            storedMessagesJSON = new JSONArray();
        }
    }

    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageID", messageID);
        json.put("messageNumber", messageNumber);
        json.put("sender", sender);
        json.put("recipient", recipient);
        json.put("message", messageText);
        json.put("messageHash", messageHash);

        storedMessagesJSON.add(json);

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(storedMessagesJSON.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Could not save message: " + e.getMessage());
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public int returnTotalMessages() {
        return storedMessagesJSON.size();
    }
    
    public static JSONArray getStoredMessagesJSON() {
        return storedMessagesJSON;
    }
}