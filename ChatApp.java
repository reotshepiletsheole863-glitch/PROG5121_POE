package chatapp;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("       WELCOME TO CHAT APP       ");
        System.out.println("=================================");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your South African cell phone number: ");
        String cellPhone = scanner.nextLine();

        Login login = new Login(username, password, firstName, lastName);

        System.out.println();

        System.out.println(login.checkUserName(username)
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");

        System.out.println(login.checkPasswordComplexity(password)
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");

        System.out.println(login.checkCellPhoneNumber(cellPhone)
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international code.");

        String registrationMessage =
                login.registerUser(username, password, cellPhone);

        System.out.println();
        System.out.println(registrationMessage);

        if (login.checkUserName(username)
                && login.checkPasswordComplexity(password)
                && login.checkCellPhoneNumber(cellPhone)) {

            System.out.println();
            System.out.println("----------- LOGIN -----------");

            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();

            System.out.println(
                    login.returnLoginStatus(loginUsername, loginPassword)
            );
        }

        scanner.close();
    }
}