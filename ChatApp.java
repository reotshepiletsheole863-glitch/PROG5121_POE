/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/*
 * Main class for the ChatApp.
 */
package chatapp;

import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ChatApp {

    private static String userName;
    private static String password;
    private static String cellPhoneNumber;
    private static String firstName;
    private static String lastName;

    // ARRAYS
    static ArrayList<String> sentMessages = new ArrayList<>();
    static ArrayList<String> disregardedMessages = new ArrayList<>();
    static ArrayList<String> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Message.loadStoredMessages();

        System.out.println("=====================================");
        System.out.println("       WELCOME TO CHATAPP            ");
        System.out.println("=====================================");

        System.out.print("Enter first name: ");
        firstName = input.nextLine();
        while (firstName.isEmpty()) {
            System.out.print("First name cannot be empty. Try again: ");
            firstName = input.nextLine();
        }

        System.out.print("Enter last name: ");
        lastName = input.nextLine();
        while (lastName.isEmpty()) {
            System.out.print("Last name cannot be empty. Try again: ");
            lastName = input.nextLine();
        }

        Login login = new Login(firstName, lastName);

        System.out.print("Create username (max 5 chars, must contain '_'): ");
        userName = input.nextLine();
        while (!login.checkUserName(userName)) {
            System.out.print("Invalid username. Try again: ");
            userName = input.nextLine();
        }

        System.out.print("Create password (8+ chars, 1 capital, 1 number, 1 special): ");
        password = input.nextLine();
        while (!login.checkPasswordComplexity(password)) {
            System.out.print("Invalid password. Try again: ");
            password = input.nextLine();
        }

        System.out.print("Enter cell number (+27...): ");
        cellPhoneNumber = input.nextLine();
        while (!login.checkCellPhoneNumber(cellPhoneNumber)) {
            System.out.print("Invalid cell number. Try again: ");
            cellPhoneNumber = input.nextLine();
        }

        String regStatus = login.registerUser(userName, password, cellPhoneNumber, firstName, lastName);
        System.out.println(regStatus);

        if (regStatus.equals("Registration successful.")) {
            System.out.println("\n--- LOGIN ---");
            boolean loggedIn = false;

            while (!loggedIn) {
                System.out.print("Username: ");
                String loginUser = input.nextLine();
                System.out.print("Password: ");
                String loginPass = input.nextLine();

                String loginStatus = login.returnLoginStatus(loginUser, loginPass);
                System.out.println(loginStatus);

                if (loginStatus.contains("Welcome")) {
                    loggedIn = true;
                } else {
                    System.out.println("Login failed. Try again.\n");
                }
            }

            System.out.println("\nWelcome to QuickChat.");
            boolean running = true;
            int messageCounter = 0;

            while (running) {
                // Clean text menu
                System.out.println("\n--- CHAT MENU ---");
                System.out.println("1. Send a Message");
                System.out.println("2. View Sent Messages");
                System.out.println("3. Quit ChatApp");
                System.out.println("4. Message Management (Stored)");
                System.out.print("Select an option: ");
                int option = Integer.parseInt(input.nextLine());

                switch (option) {
                    case 1:
                        System.out.print("How many messages? ");
                        int totalMessages = Integer.parseInt(input.nextLine());

                        for (int i = 0; i < totalMessages; i++) {
                            messageCounter++;
                            System.out.print("Recipient (+code): ");
                            String recipient = input.nextLine();
                            System.out.print("Message (max 250 chars): ");
                            String text = input.nextLine();

                            Message msg = new Message(messageCounter, recipient, text, userName);
                            
                            if (msg.checkMessageLength().startsWith("Message exceeds")) {
                                System.out.println(msg.checkMessageLength());
                                continue;
                            }

                            System.out.println("\n1. Send  2. Disregard  3. Store");
                            System.out.print("Choose action: ");
                            int choice = Integer.parseInt(input.nextLine());

                            System.out.println(msg.SentMessage(choice));

                            if (choice == 1) {
                                sentMessages.add(msg.getMessageText());
                                messageHashes.add(msg.getMessageHash());
                                messageIDs.add(msg.getMessageID());
                            } else if (choice == 2) {
                                disregardedMessages.add(msg.getMessageText());
                            } else if (choice == 3) {
                                storedMessages.add(msg.getMessageText());
                                messageHashes.add(msg.getMessageHash());
                                messageIDs.add(msg.getMessageID());
                            }

                            System.out.println("\n" + msg.printMessages());
                        }
                        System.out.println("Total messages in session: " + new Message(0, "", "", "").returnTotalMessages());
                        break;

                    case 2:
                        System.out.println("\n--- SENT MESSAGES ---");
                        if (sentMessages.isEmpty()) {
                            System.out.println("Inbox is empty.");
                        } else {
                            for (int i = 0; i < sentMessages.size(); i++) {
                                System.out.println((i + 1) + ". " + sentMessages.get(i));
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Logging out... Goodbye!");
                        running = false;
                        break;

                    case 4:
                        JSONArray storedJSON = Message.getStoredMessagesJSON();
                        if (storedJSON.isEmpty()) {
                            System.out.println("No stored messages in database.");
                            break;
                        }

                        // Distinct Sub-menu for ChatApp
                        System.out.println("\n--- MESSAGE MANAGEMENT ---");
                        System.out.println("A. View Senders & Recipients");
                        System.out.println("B. Find Longest Message");
                        System.out.println("C. Search by Message ID");
                        System.out.println("D. Search by Recipient");
                        System.out.println("E. Delete by Hash");
                        System.out.println("F. Generate Full Report");
                        System.out.print("Enter selection: ");
                        String subOption = input.nextLine();

                        switch (subOption.toUpperCase()) { 
                            case "A":
                                for (Object obj : storedJSON) {
                                    JSONObject m = (JSONObject) obj;
                                    System.out.println("From: " + m.get("sender") + " | To: " + m.get("recipient"));
                                }
                                break;
                            case "B":
                                String longest = "";
                                for (Object obj : storedJSON) {
                                    JSONObject m = (JSONObject) obj;
                                    String text = (String) m.get("message");
                                    if (text.length() > longest.length()) {
                                        longest = text;
                                    }
                                }
                                System.out.println("Longest text found: " + longest);
                                break;
                            case "C":
                                System.out.print("Enter ID: ");
                                String searchID = input.nextLine();
                                boolean foundID = false;
                                for (Object obj : storedJSON) {
                                    JSONObject m = (JSONObject) obj;
                                    if (m.get("messageID").equals(searchID)) {
                                        System.out.println("To: " + m.get("recipient") + "\nText: " + m.get("message"));
                                        foundID = true;
                                        break;
                                    }
                                }
                                if (!foundID) System.out.println("ID not found.");
                                break;
                            case "D":
                                System.out.print("Enter Recipient Number: ");
                                String searchRecipient = input.nextLine();
                                boolean foundRecipient = false;
                                for (Object obj : storedJSON) {
                                    JSONObject m = (JSONObject) obj;
                                    if (m.get("recipient").equals(searchRecipient)) {
                                        System.out.println("Text: " + m.get("message"));
                                        foundRecipient = true;
                                    }
                                }
                                if (!foundRecipient) System.out.println("No messages for this number.");
                                break;
                            case "E":
                                System.out.print("Enter Hash to delete: ");
                                String searchHash = input.nextLine();
                                boolean deleted = false;
                                for (int i = 0; i < storedJSON.size(); i++) {
                                    JSONObject m = (JSONObject) storedJSON.get(i);
                                    if (m.get("messageHash").equals(searchHash)) {
                                        storedJSON.remove(i);
                                        try (java.io.FileWriter file = new java.io.FileWriter("messages.json")) {
                                            file.write(storedJSON.toJSONString());
                                        } catch (Exception e) {
                                            System.out.println("Error saving file.");
                                        }
                                        System.out.println("Deleted message: \"" + m.get("message") + "\"");
                                        deleted = true;
                                        break;
                                    }
                                }
                                if (!deleted) System.out.println("Hash not found.");
                                break;
                            case "F":
                                System.out.println("\n--- FULL REPORT ---");
                                for (Object obj : storedJSON) {
                                    JSONObject m = (JSONObject) obj;
                                    System.out.println("Hash: " + m.get("messageHash") + 
                                                       "\nTo: " + m.get("recipient") + 
                                                       "\nText: " + m.get("message") + "\n-------------------");
                                }
                                break;
                            default:
                                System.out.println("Invalid selection.");
                        }
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            }
        }

        input.close();
    }
}