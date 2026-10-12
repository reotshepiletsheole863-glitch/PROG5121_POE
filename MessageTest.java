package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    private Message message;

    @Before
    public void setUp() {
        // Test data from Part 2 brief, Message 1
        message = new Message(0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
    }

    @Test
    public void testCheckMessageID() {
        assertTrue("Message ID should be 10 characters or less",
                message.checkMessageID());
    }

    @Test
    public void testCheckRecipientCellValid() {
        assertEquals("Cell phone number successfully captured.",
                message.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCellInvalid() {
        Message invalid = new Message(0, "08575975889",
                "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                invalid.checkRecipientCell());
    }

    @Test
    public void testCheckMessageLengthValid() {
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    public void testCheckMessageLengthInvalid() {
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longText.append("a");
        }
        Message tooLong = new Message(0, "+27718693002", longText.toString());
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                tooLong.checkMessageLength());
    }

    @Test
    public void testCreateMessageHash() {
        String hash = message.createMessageHash();
        assertTrue("Hash should end with :0:HITONIGHT",
                hash.endsWith(":0:HITONIGHT"));
    }

    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.", message.SentMessage(1));
    }

    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", message.SentMessage(2));
    }

    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", message.SentMessage(3));
    }

    @Test
    public void testReturnTotalMessages() {
        int before = message.returnTotalMessages();
        message.SentMessage(1);
        assertEquals(before + 1, message.returnTotalMessages());
    }
}