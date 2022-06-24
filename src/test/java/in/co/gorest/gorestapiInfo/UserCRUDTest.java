package in.co.gorest.gorestapiInfo;


import in.co.gorest.testbase.TestBase;
import in.co.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {
    static String name = "Tenali Ramakrishna" + TestUtils.getRandomValue();
    static String gender = "female";
    static String email = "tenali.ramakrishna" + TestUtils.getRandomValue() + "@email.com";
    static String status = "active";
    static int userID;

    @Steps
    UsersSteps usersSteps;

    @Title("This will create a new User")
    @Test
    public void test001() {
        ValidatableResponse response = usersSteps.createUser(name, gender, email, status);
        response.log().all().statusCode(201);
        userID = response.log().all().extract().path("id");
        System.out.println(userID);
    }

    @Title("This will Fetch user by ID")
    @Test
    public void test002() {
        ValidatableResponse response = usersSteps.getUserByID(userID);
        response.log().all().statusCode(200);
        HashMap<?, ?> userMap = response.log().all().extract().path("");
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("This will Fetch user by ID")
    @Test
    public void test003() {
        name = name + "_updated";
        email = "tenali.ramakrishna" + TestUtils.getRandomValue() + "@email.com";
        ValidatableResponse response = usersSteps.updateUser(userID, name, gender, email, status);
        response.log().all().statusCode(200);
        HashMap<?, ?> userMap = response.log().all().extract().path("");
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("This will Delete user by ID")
    @Test
    public void test004() {
        ValidatableResponse response = usersSteps.deleteUser(userID);
        response.log().all().statusCode(204);
        ValidatableResponse response1 = usersSteps.getUserByID(userID);
        response1.log().all().statusCode(404);

    }



}
