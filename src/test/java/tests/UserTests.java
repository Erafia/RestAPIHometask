package tests;

import adapters.JobUserAdapter;
import adapters.UsersAdapter;
import models.*;
import models.jobUser.JobUserCreationResponse;
import models.jobUser.UserNameJob;
import models.jobUser.JobUserUpdateResponse;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class UserTests extends BaseTest{
    int userNotExists = 500;

    @Test
    public void validateGetListOfUsers() throws FileNotFoundException {
        UsersList expectedList = gson.fromJson(new FileReader("src/test/java/resources/expectedList.json"), UsersList.class);
        UsersList list = new UsersAdapter().get(1);
        assertEquals(list, expectedList, "User list per page does not match the expected list");
    }

    @Test
    public void validateGetListOfUsersWithDelay() throws FileNotFoundException {
        UsersList expectedList = gson.fromJson(new FileReader("src/test/java/resources/expectedList.json"), UsersList.class);
        UsersList list = new UsersAdapter().getWithDelay();
        assertEquals(list, expectedList, "User list per page does not match the expected list");
    }

    @Test
    public void getExistingUser() throws FileNotFoundException{
        SingleUser expectedUser = gson.fromJson(new FileReader("src/test/java/resources/expectedUser.json"), SingleUser.class);
        SingleUser actualUser = new UsersAdapter().getUserById(1);
        int actualStatusCode = new UsersAdapter().getUserByIdStatusCode(1);
        assertEquals(actualStatusCode, 200, "Incorrect status code is returned: " + actualStatusCode);
        assertEquals(actualUser, expectedUser, "User`s data does not match the expected");
    }

    @Test
    public void getNotExistingUser() {
        int actualStatusCode = new UsersAdapter().getUserByIdStatusCode(userNotExists);
        String actualBody = new UsersAdapter().getUserByIdResponseBody(userNotExists);
        assertEquals(actualStatusCode, 404, "Incorrect status code is returned: " + actualStatusCode);
        assertEquals(actualBody, "{}", "Body is not empty for the not existing user");
    }
    
    @Test(priority = 0)
    public void createUser() throws FileNotFoundException {
        //кейс с сериализацией и десериализацией в Expose работает не совсем корректно: прокидывает null в значение поля в объекте,
        //и так это может стать error-prone в случае с проверками на null-параметры и unexpected параметры. Или так и надо? ლ(ಠ_ಠ ლ)

        UserNameJob expectedUser = gson.fromJson(new FileReader("src/test/java/resources/expectedJobUser.json"), UserNameJob.class);
        JobUserCreationResponse actualUser = new JobUserAdapter()
                .post(gson.fromJson(new FileReader("src/test/java/resources/expectedJobUser.json"), UserNameJob.class));
        String expectedTime = java.time.Clock.systemUTC().instant().toString().substring(0,10);

        assertEquals(expectedUser.getName(), actualUser.getName(),
                "User`s actual name " + actualUser.getName() + " does not match the input name: " + expectedUser.getName());
        assertEquals(expectedUser.getJob(), actualUser.getJob(),
                "User`s actual job " + actualUser.getJob() + " does not match the input job: " + expectedUser.getJob());
        assertEquals(expectedTime, actualUser.getCreatedAt().substring(0,10),
                "User creation time" + expectedTime + " does not match the actual time.");
        assertFalse(actualUser.getId().isEmpty(), "ID пользователя пуст"); //т.к. у сервиса динамически изменяется id, тут только такая проверка ╮(˘､˘)╭
    }

    @Test (priority = 1)
    public void patchUser() throws FileNotFoundException {
        UserNameJob expectedUser = gson.fromJson(new FileReader("src/test/java/resources/expectedJobUserUpdate.json"), UserNameJob.class);
        JobUserUpdateResponse actualUser = new JobUserAdapter()
                .patch(gson.fromJson(new FileReader("src/test/java/resources/expectedJobUserUpdate.json"), UserNameJob.class), 2);
        String expectedTime = java.time.Clock.systemUTC().instant().toString().substring(0,10);

        assertEquals(expectedUser.getName(), actualUser.getName(),
                "User`s actual name " + actualUser.getName() + " does not match the input name: " + expectedUser.getName());
        assertEquals(expectedUser.getJob(), actualUser.getJob(),
                "User`s actual job " + actualUser.getJob() + " does not match the input job: " + expectedUser.getJob());
        assertEquals(expectedTime, actualUser.getUpdatedAt().substring(0,10),
                "User creation time" + expectedTime + " does not match the actual time.");
        System.out.println(actualUser);
        System.out.println(expectedUser);
    }

    @Test (priority = 2)
    public void updateUserViaPut() throws FileNotFoundException {
        UserNameJob expectedUser = gson.fromJson(new FileReader("src/test/java/resources/expectedJobUserUpdate.json"), UserNameJob.class);
        JobUserUpdateResponse actualUser = new JobUserAdapter()
                .put(gson.fromJson(new FileReader("src/test/java/resources/expectedJobUserUpdate.json"), UserNameJob.class), 2);
        String expectedTime = java.time.Clock.systemUTC().instant().toString().substring(0,10);

        assertEquals(expectedUser.getName(), actualUser.getName(),
                "User`s actual name " + actualUser.getName() + " does not match the input name: " + expectedUser.getName());
        assertEquals(expectedUser.getJob(), actualUser.getJob(),
                "User`s actual job " + actualUser.getJob() + " does not match the input job: " + expectedUser.getJob());
        assertEquals(expectedTime, actualUser.getUpdatedAt().substring(0,10),
                "User creation time" + expectedTime + " does not match the actual time.");
        System.out.println(actualUser);
        System.out.println(expectedUser);
    }

    @Test (priority = 3)
    public void deleteUser(){
        int actualStatusCode = new JobUserAdapter().deleteJobUserByIdStatusCode(2);
        assertEquals(actualStatusCode, 204, "Incorrect status code is returned: " + actualStatusCode);
    }
}
