# ChatApp - PROG5121 POE Part 2

A console-based Java messaging application that handles user registration, login, and message sending.

## Features
- User registration with username, password, and South African cell number validation.
- Login authentication.
- QuickChat menu with options to send messages, view recent messages, or quit.
- Messages are validated, hashed, and stored in a JSON file.

## How to Run
1. Open the project in NetBeans..
2. Run `ChatApp.java`.
3. Follow the prompts to register, log in, and send messages.

## Testing
JUnit tests are in `LoginTest.java` and `MessageTest.java`. All tests pass.

## Automated Tests
GitHub Actions runs the tests on every push via `.github/workflows/test.yml`.

## References
- GeeksforGeeks (2023). How to validate a phone number using regex.
  https://www.geeksforgeeks.org/how-to-validate-a-phone-number-using-regex/
- json-simple. 2012. A simple Java toolkit for JSON.
  https://github.com/fangyidong/json-simple
