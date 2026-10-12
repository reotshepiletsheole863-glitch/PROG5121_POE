/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Handles message creation, hashing, storing and printing.
 */
package chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static JSONArray storedMessages = new JSONArray();

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Randomly generates a ten-digit number as the message ID
    private String generateMessageID() {
        Random rand = new Random();
        long number = 1000000000L + (long) (rand.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    // Checks that the ID is not more than ten characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks that the recipient cell number is valid
    public String checkRecipientCell() {
        String regex = "^\\+\\d{11,13}$";
        if (recipient.matches(regex)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Creates the message hash: first two digits of ID : message number : FIRST+LAST word, uppercase
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Checks that the message is no more than 250 characters
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // Allows the user to send, store or disregard the message
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

    // JSON handling adapted from:
    // json-simple. 2012. json-simple - A simple Java toolkit for JSON. [Source code].
    // Available at: https://github.com/fangyidong/json-simple
    // [Accessed 12 October 2026].
    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageID", messageID);
        json.put("messageNumber", messageNumber);
        json.put("recipient", recipient);
        json.put("message", messageText);
        json.put("messageHash", messageHash);

        storedMessages.add(json);

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(storedMessages.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Could not save message: " + e.getMessage());
        }
    }

    // Prints the details of this message
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Returns how many messages are stored
    public int returnTotalMessages() {
        return storedMessages.size();
    }
}
