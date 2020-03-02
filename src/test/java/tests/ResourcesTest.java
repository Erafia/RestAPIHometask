package tests;

import adapters.ResourcesAdapter;
import models.resource.ResourceList;
import models.resource.SingleResource;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.assertEquals;

public class ResourcesTest extends BaseTest{
    int resourceDoesNotExist = 500;

    @Test
    public void validateGetListOfResources() throws FileNotFoundException {
        ResourceList expectedList = gson.fromJson(new FileReader("src/test/java/resources/expectedResourceList.json"), ResourceList.class);
        ResourceList list = new ResourcesAdapter().get();
        assertEquals(list, expectedList, "Resources list per page does not match the expected list");
    }

    @Test
    public void getExistingResource() throws FileNotFoundException{
        SingleResource expectedResource = gson.fromJson(new FileReader("src/test/java/resources/expectedResource.json"), SingleResource.class);
        SingleResource actualResource = new ResourcesAdapter().getResourceById(1);
        int actualStatusCode = new ResourcesAdapter().getResourceByIdStatusCode(1);
        assertEquals(actualStatusCode, 200, "Incorrect status code is returned: " + actualStatusCode);
        assertEquals(actualResource, expectedResource, "Resource`s data does not match the expected");
    }

    @Test
    public void getNotExistingUser() {
        int actualStatusCode = new ResourcesAdapter().getResourceByIdStatusCode(resourceDoesNotExist);
        String actualBody = new ResourcesAdapter().getResourceByIdResponseBody(resourceDoesNotExist);
        assertEquals(actualStatusCode, 404, "Incorrect status code is returned: " + actualStatusCode);
        assertEquals(actualBody, "{}", "Body is not empty for the not existing resource");
    }

}
