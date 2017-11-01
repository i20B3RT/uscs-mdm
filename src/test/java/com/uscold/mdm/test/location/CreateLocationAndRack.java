package com.uscold.mdm.test.location;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class CreateLocationAndRack extends AbstractTestClass {

    static final String ROOM_NUMBER = "10";
    static final String AISLE_NUMBER = "20";
    static final String RACK_NUMBER = "30";

    @Test
    public void createLocation() {
        PageHelper.chooseModule(driver, "Locations & Racks Setup");
        PageHelper.chooseWarehouse(driver, 160);

        click(driver.findElement(By.id("createLocations")));
        //driver.findElement(By.id("locationAisle")).click();
        click(driver.findElement(By.id("locationRoom")));
        PageHelper.waitForJSandJQueryToLoad(driver);
        //driver.findElement(By.cssSelector("label")).click();
        driver.findElement(By.id("fromRoom")).clear();
        driver.findElement(By.id("fromRoom")).sendKeys(ROOM_NUMBER);
        driver.findElement(By.id("toRoom")).clear();
        driver.findElement(By.id("toRoom")).sendKeys(ROOM_NUMBER);
        click(chooseValueFromStandardDropDown("type_chosen", "dry"));
        click(chooseValueFromStandardDropDown("dock_chosen", "1"));
        click(driver.findElement(By.id("roomCreateSubmit")));
        click(driver.findElement(By.id("btn_yes")));

        //aisle
        click(driver.findElement(By.id("createLocations")));
        click(driver.findElement(By.id("locationAisle")));
        click(chooseValueFromStandardDropDown("roomDDInAisle_chosen", ROOM_NUMBER));
        PageHelper.waitForJSandJQueryToLoad(driver);
        driver.findElement(By.id("fromAisle")).clear();
        driver.findElement(By.id("fromAisle")).sendKeys(AISLE_NUMBER);
        driver.findElement(By.id("toAisle")).clear();
        driver.findElement(By.id("toAisle")).sendKeys(AISLE_NUMBER);
        click(chooseValueFromStandardDropDown("aisleType_chosen", "dry"));
        click(driver.findElement(By.id("aisleCreateSubmit")));
        click(driver.findElement(By.id("btn_yes")));
        //rack
        click(driver.findElement(By.id("createLocations")));
        click(driver.findElement(By.id("locationRack")));
        click(chooseValueFromStandardDropDown("roomDDInRack_chosen", ROOM_NUMBER));
        PageHelper.waitForJSandJQueryToLoad(driver);
        click(chooseValueFromStandardDropDown("aisleDDInRack_chosen", AISLE_NUMBER));
        PageHelper.waitForJSandJQueryToLoad(driver);

        driver.findElement(By.id("fromRack")).clear();
        driver.findElement(By.id("fromRack")).sendKeys(RACK_NUMBER);
        driver.findElement(By.id("toRack")).clear();
        driver.findElement(By.id("toRack")).sendKeys(RACK_NUMBER);

        driver.findElement(By.id("numOfLevel")).clear();
        driver.findElement(By.id("numOfLevel")).sendKeys("4");
        click(driver.findElement(By.id("racksCreateSubmitBtn")));
        click(driver.findElement(By.id("btn_yes")));
    }

    private WebElement chooseValueFromStandardDropDown(String id, String value) {
        WebElement container = driver.findElement(By.id(id));
        WebElement aElem = container.findElement(By.cssSelector("a.chosen-single"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", aElem);
        click(aElem);
        List<WebElement> values = container.findElements(By.xpath("div[@class='chosen-drop']/ul/li"));
        return values.stream().filter(el -> el.getAttribute("innerText").trim().equalsIgnoreCase(value)).findFirst().get();

    }
}
