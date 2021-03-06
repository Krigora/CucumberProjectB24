package com.cybertek.step_definitions;

import com.cybertek.pages.AddSpartansPage;
import com.cybertek.pages.SpartanConfirmationPage;
import com.cybertek.pages.SpartanHomePage;
import com.cybertek.pages.SpartansDataTablePage;
import com.cybertek.utilities.ConfigurationReader;
import com.cybertek.utilities.DBUtils;
import com.cybertek.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class SpartanStepDefs {

    Map<String, String> spartanMap = new HashMap<>();

    @Given("User is on spartan home page")
    public void user_is_on_spartan_home_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("spartan.url"));

    }

    @When("User goes to Web Data page")
    public void user_goes_to_web_data_page() {
        SpartanHomePage homePage = new SpartanHomePage();
                homePage.webDataLink.click();

    }

    @When("clicks on add spartan")
    public void clicks_on_add_spartan() {
        SpartansDataTablePage dataTablePage = new SpartansDataTablePage();
        dataTablePage.addSpartanBtn.click();

    }
    @When("enters following data and submits:")
    public void enters_following_data_and_submits(Map<String, String> spartanInfo) {

       spartanMap.putAll(spartanInfo); //copy value of param

        AddSpartansPage addSpartanPage = new AddSpartansPage();
        addSpartanPage.name.sendKeys(spartanInfo.get("name"));
        addSpartanPage.selectGender(spartanInfo.get("gender"));
        addSpartanPage.phone.sendKeys(spartanInfo.get("phone"));
        addSpartanPage.submitBtn.click();

    }
    @Then("success message should be displayed")
    public void success_message_should_be_displayed() {
        SpartanConfirmationPage confirmationPage = new SpartanConfirmationPage();
        Assert.assertEquals("Successfully Added new Data!", confirmationPage.alertMessage.getText());

    }


    @Then("data on confirmation  page must be same")
    public void dataOnConfirmationPageMustBeSame() {

        SpartanConfirmationPage spartanConfirmationPage = new SpartanConfirmationPage();
        Assert.assertEquals(spartanMap.get("name"), spartanConfirmationPage.name.getAttribute("value"));
        Assert.assertEquals(spartanMap.get("gender"), spartanConfirmationPage.gender.getAttribute("value"));
        Assert.assertEquals(spartanMap.get("phone"), spartanConfirmationPage.phone.getAttribute("value"));

    }

    @And("data in database  must be mutch")
    public void dataInDatabaseMustBeMutch() {
      //  Map<String, Object> dbMap = DBUtils.getRowMap("SELECT * FROM spartans WHERE name = 'Wooden Tester'");
     //   Assert.assertEquals(spartanMap.get("name"), dbMap.get("NAME"));
     //   Assert.assertEquals(spartanMap.get("gender"), dbMap.get("GENDER"));
       // Assert.assertEquals(spartanMap.get("phone"), dbMap.get("PHONE"));

        Map<String, Object> dbMap = DBUtils.getRowMap("SELECT * FROM spartans WHERE name = 'krigora'");
        Assert.assertEquals(spartanMap.get("name") , dbMap.get("NAME"));
        Assert.assertEquals(spartanMap.get("gender") , dbMap.get("GENDER"));
        Assert.assertEquals(spartanMap.get("phone") , dbMap.get("PHONE"));

        //delete the spartan data after verification
        DBUtils.executeQuery("DELETE FROM spartans WHERE name = 'krigora'");

    }
}
