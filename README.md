# ChatApp - PROG5121 Final POE

A console-based Java messaging application built for the PROG5121 final POE.

## Features
- **Part 1:** User Registration and Login validation (Username, Password, Cell Phone).
- **Part 2:** QuickChat Menu to send, store, and disregard messages (saved to `messages.json`).
- **Part 3:** Message Management menu to search by ID, search by recipient, find the longest message, delete by hash, and view a full report.

## How to Run
1. Open the project in NetBeans.
2. Add `json-simple-1.1.jar` to the project libraries.
3. Run `ChatApp.java`.
4. Follow the prompts to register, log in, and manage your messages.

## Testing
JUnit tests are in `LoginTest`, `MessageTest`, and `StoredMessagesTest`. All tests pass.

## Automated Tests
GitHub Actions runs the tests on every push via `.github/workflows/test.yml`.

## References
- GeeksforGeeks (2023). How to validate a phone number using regex.
- json-simple. 2012. A simple Java toolkit for JSON.
