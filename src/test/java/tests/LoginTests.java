package tests;

import adapters.AuthorizationAdapter;
import models.loginNregister.LoginUserRequest;
import models.loginNregister.LoginUserResponse;
import models.loginNregister.UnsuccessLoginUserResponse;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.*;

public class LoginTests extends BaseTest {

    @Test
    public void testRegisterWithValidUser() throws FileNotFoundException {
        LoginUserResponse response = new AuthorizationAdapter()
                .post(gson.fromJson(new FileReader("src/test/java/resources/successLoginUser.json"), LoginUserRequest.class));
        assertTrue(response.getId() > 0, "Registered ID is not returned");
        assertFalse(response.getToken().isEmpty(), "Registration token is empty");
    }

    @Test
    public void testRegisterWithEmailOnly() throws FileNotFoundException {
        UnsuccessLoginUserResponse response = new AuthorizationAdapter()
                .postInvalid(gson.fromJson(new FileReader("src/test/java/resources/unsuccessLoginUser.json"), LoginUserRequest.class));
        assertEquals(response.getError(), "Missing password", "Error message is not received or incorrect message received");
    }

    @Test
    public void testLoginWithValidUser() throws FileNotFoundException {
        LoginUserResponse response = new AuthorizationAdapter()
                .postLogin(gson.fromJson(new FileReader("src/test/java/resources/successLoginUser.json"), LoginUserRequest.class));
        assertFalse(response.getToken().isEmpty(), "Registration token is empty");
    }

    @Test
    public void testLoginWithEmailOnly() throws FileNotFoundException {
        UnsuccessLoginUserResponse response = new AuthorizationAdapter()
                .postInvalidLogin(gson.fromJson(new FileReader("src/test/java/resources/unsuccessLoginUser.json"), LoginUserRequest.class));
        assertEquals(response.getError(), "Missing password", "Error message is not received or incorrect message received");
    }

}
