package chatapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    private Login login;

    @Before
    public void setUp() {
        login = new Login("Kyle", "Smith");
    }

    @Test
    public void testCheckUserName() {
        assertTrue("Valid username should return true", login.checkUserName("kyl_1"));
        assertFalse("Invalid username should return false", login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity() {
        assertTrue("Valid password should return true", login.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertFalse("Invalid password should return false", login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber() {
        assertTrue("Valid phone should return true", login.checkCellPhoneNumber("+27838968976"));
        assertFalse("Invalid phone should return false", login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUser() {
        String success = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("Registration successful.", success);

        String invalidUser = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", invalidUser);

        String invalidPass = login.registerUser("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", invalidPass);

        String invalidCell = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", invalidCell);
    }

    @Test
    public void testLoginUser() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        assertTrue("Correct credentials should return true", login.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertFalse("Incorrect credentials should return false", login.loginUser("wrong", "wrongpass"));
    }

    @Test
    public void testReturnLoginStatus() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        String success = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle Smith it is great to see you again.", success);

        String fail = login.returnLoginStatus("wrong", "wrongpass");
        assertEquals("Username or password incorrect, please try again.", fail);
    }
}