package com.uscold.mdm.test.user;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.List;

public class CreateUserTest extends AbstractTestClass {

    private static final Logger LOGGER = Logger.getLogger(CreateUserTest.class);

    @Test
    public void createUserTest() throws InterruptedException {
        PageHelper.chooseModule(driver,"User Management");
        //choose warehouse
        PageHelper.chooseWarehouse(driver, 160);
        //div id successMsg ; is successfully created
        click(driver.findElement(By.id("basicSearch")));
        click(driver.findElement(By.id("addNewUser")));

        driver.findElement(By.id("fName")).clear();
        driver.findElement(By.id("fName")).sendKeys("autotest");
        driver.findElement(By.id("lName")).clear();
        driver.findElement(By.id("lName")).sendKeys("autotest");
        driver.findElement(By.id("securityLevel")).clear();
        driver.findElement(By.id("securityLevel")).sendKeys("9");


        WebElement primaryWhSelect = driver.findElement(By.id("primaryWarehouse_chosen"));
        click(primaryWhSelect.findElement(By.cssSelector("a.chosen-single")));
        List<WebElement> warehouses = primaryWhSelect.findElements(By.cssSelector("div.chosen-drop > ul > li"));
        WebElement choseWh = warehouses.stream().filter(wh -> wh.getText().contains("160")).findFirst().get();
        click(choseWh);

        //LOGGER.warn(recordTimeForOperation(() -> wait.until(webDriver -> !driver.findElement(By.id("userIdGen")).getAttribute("value").isEmpty())));

        click(driver.findElement(By.id("btn_Submit")));

        click(driver.findElement(By.id("chk_all1")));
        click(driver.findElement(By.id("410")));
        click(driver.findElement(By.id("assign")));
        click(driver.findElement(By.id("userSecSubmit")));

        WebElement statusMsg = driver.findElement(By.id("reportSuccessMsg"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("successfully created"))
            throw new RuntimeException("Failed to create user");

    }
}
