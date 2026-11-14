/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StoredMessagesTest {

    private Message msg1, msg2, msg3;

    @Before
    public void setUp() {
        msg1 = new Message(1, "+27834557896", "Did you get the cake?", "Developer");
        msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Developer");
        msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.", "Developer");
    }

    @Test
    public void testMessageHashGeneration() {
        String hash = msg1.createMessageHash();
        assertTrue("Hash should end with :1:DIDCAKE", hash.endsWith(":1:DIDCAKE"));
    }

    @Test
    public void testLongestMessage() {
        String[] messages = {msg1.getMessageText(), msg2.getMessageText(), msg3.getMessageText()};
        String longest = "";
        for (String text : messages) {
            if (text.length() > longest.length()) {
                longest = text;
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByRecipient() {
        String targetRecipient = "+27838884567";
        int foundCount = 0;
        Message[] messages = {msg1, msg2, msg3};
        for (Message m : messages) {
            if (m.getRecipient().equals(targetRecipient)) {
                foundCount++;
            }
        }
        assertEquals(1, foundCount);
        assertEquals("Where are you? You are late! I have asked you to be on time.", msg2.getMessageText());
    }
}