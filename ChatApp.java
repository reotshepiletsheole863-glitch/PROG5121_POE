/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/*
 * Main class for the ChatApp - handles registration, login and the QuickChat menu.
 */
package chatapp;

import java.util.Scanner;

public class ChatApp {

    private static String userName;
    private static String password;
    private static String cellPhoneNumber;
    private static String firstName;
    private static String lastName;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("*Registration*");

        // First name
        System.out.print("Please enter your first name: ");
        firstName = input.nextLine();

        while (firstName.isEmpty()) {
            System.out.println("First name cannot be empty");
            System.out.print("Please enter your first name: ");
            firstName = input.nextLine();
        }

        // Last name
        System.out.print("Please enter your last name: ");
        lastName = input.nextLine();

        while (lastName.isEmpty()) {
            System.out.println("Last name cannot be empty");
            System.out.print("Please enter your last name: ");
            lastName = input.nextLine();
        }

        Login login = new Login(firstName, lastName);

        // Username
        System.out.print("Enter your username (less than 5 characters and must contain an underscore): ");
        userName = input.nextLine();

        while (!login.checkUserName(userName)) {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Enter your username (less than 5 characters and must contain an underscore): ");
            userName = input.nextLine();
        }

        // Password
        System.out.print("Create a password (at least 8 characters, 1 capital letter, 1 number, 1 special character): ");
        password = input.nextLine();

        while (!login.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            System.out.print("Create a password (at least 8 characters, 1 capital letter, 1 number, 1 special character): ");
            password = input.nextLine();
        }

        // Cell phone
        System.out.print("Please enter cellphone number with international country code: ");
        cellPhoneNumber = input.nextLine();

        while (!login.checkCellPhoneNumber(cellPhoneNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code, please correct the number and try again");
            System.out.print("Please enter cellphone number with international country code: ");
            cellPhoneNumber = input.nextLine();
        }

        // Register
        String regStatus = login.registerUser(userName, password, cellPhoneNumber, firstName, lastName);
        System.out.println(regStatus);

        // Login
        if (regStatus.equals("Registration successful.")) {
            System.out.println("*Login*");

            boolean loggedIn = false;

            while (!loggedIn) {
                System.out.print("Enter the username you created: ");
                String loginUser = input.nextLine();

                System.out.print("Enter the password you created: ");
                String loginPass = input.nextLine();

                String loginStatus = login.returnLoginStatus(loginUser, loginPass);
                System.out.println(loginStatus);

                if (loginStatus.contains("Welcome")) {
                    loggedIn = true;
                } else {
                    System.out.println("Login failed, please try again");
                }
            }

            // ----- QuickChat Menu -----
            System.out.println("\nWelcome to QuickChat.");

            boolean running = true;
            int messageCounter = 0;
            Message lastMessage = null;

            while (running) {
                System.out.println("\n1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(input.nextLine());

                switch (option) {
                    case 1:
                        System.out.print("How many messages do you want to send? ");
                        int totalMessages = Integer.parseInt(input.nextLine());

                        for (int i = 0; i < totalMessages; i++) {
                            messageCounter++;

                            System.out.print("Enter recipient cell number (with international code): ");
                            String recipient = input.nextLine();

                            System.out.print("Enter message (max 250 characters): ");
                            String text = input.nextLine();

                            Message msg = new Message(messageCounter, recipient, text);
                            lastMessage = msg;

                            String lengthCheck = msg.checkMessageLength();
                            System.out.println(lengthCheck);

                            if (lengthCheck.startsWith("Message exceeds")) {
                                continue;
                            }

                            System.out.println(msg.checkRecipientCell());

                            System.out.println("\n1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message to send later");
                            System.out.print("Choose: ");
                            int choice = Integer.parseInt(input.nextLine());

                            System.out.println(msg.SentMessage(choice));
                            System.out.println("\n" + msg.printMessages());
                        }

                        if (lastMessage != null) {
                            System.out.println("\nTotal messages: " + lastMessage.returnTotalMessages());
                        }
                        break;

                    case 2:
                        System.out.println("Coming Soon.");
                        break;

                    case 3:
                        System.out.println("Goodbye.");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            }
        }

        input.close();
    }
}