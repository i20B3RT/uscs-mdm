package com.uscold.mdm.test.location;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;


public class LocationRackTest extends AbstractTestClass {
    protected static final Logger LOGGER = Logger.getLogger(LocationRackTest.class);

    private final static int LEVEL = 2;
    private final static String POSITION_NUMBER = "1";
    private final static String TYPE = "Floor";
    private final static String PALLET_COUNT = "1";
    private static String RACK_NUMBER = CreateLocationAndRack.RACK_NUMBER;

    //Rack always displays as 3-digit number.
    {
        while (RACK_NUMBER.length() < 3) {
            RACK_NUMBER = "0" + RACK_NUMBER;
        }
    }


    @Test(priority = 1)
    public void createLocation() throws Exception {
        PageHelper.chooseModule(driver,"Locations & Racks Setup");
        //choose warehouse
        PageHelper.chooseWarehouse(driver, 160);
        click(driver.findElement(By.id("addNewRecord")));
        //room
        WebElement roomSelect = driver.findElement(By.xpath("//div[@id='new_row1_roomSysid_chosen']"));
        click(roomSelect.findElement(By.cssSelector("a.chosen-single")));
        click(roomSelect.findElement(By.xpath(".//div/ul/li[normalize-space() = '" + CreateLocationAndRack.ROOM_NUMBER + "']")));
        //aisle
        WebElement aisleSelect = driver.findElement(By.xpath("//div[@id='new_row1_aisleNumber_chosen']"));
        click(aisleSelect.findElement(By.cssSelector("a.chosen-single")));
        click(aisleSelect.findElement(By.xpath(".//div/ul/li[normalize-space() = '" + CreateLocationAndRack.AISLE_NUMBER + "']")));
        //rack
        WebElement rackSelect = driver.findElement(By.xpath("//div[@id='new_row1_rackNumber_chosen']"));
        click(rackSelect.findElement(By.cssSelector("a.chosen-single")));
        click(rackSelect.findElement(By.xpath(".//div/ul/li[normalize-space() = '" + RACK_NUMBER + "']")));
        //level
        WebElement levelSelect = driver.findElement(By.xpath("//div[@id='new_row1_levelValue_chosen']"));
        click(levelSelect.findElement(By.cssSelector("a.chosen-single")));
        click(levelSelect.findElement(By.xpath(".//div/ul/li[normalize-space() = '" + LEVEL + "']")));
        //position
        driver.findElement(By.id("new_row1_positnNumber")).clear();
        driver.findElement(By.id("new_row1_positnNumber")).sendKeys(POSITION_NUMBER);
        //blast
        WebElement blastSelect = driver.findElement(By.xpath("//div[@id='new_row1_locTypeDtlCode_chosen']"));
        click(blastSelect.findElement(By.cssSelector("a.chosen-single")));
        click(blastSelect.findElement(By.xpath(".//div/ul/li[normalize-space() = '" + TYPE + "']")));
        //# of Pit
        driver.findElement(By.id("new_row1_palletCount")).clear();
        driver.findElement(By.id("new_row1_palletCount")).sendKeys(PALLET_COUNT);
        //save
        click(driver.findElement(By.id("saveNewRecord")));
        //reportSuccessMsg1 parent div
        WebElement statusMsg = driver.findElement(By.id("msgDisp1"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("successfully created"))
            throw new RuntimeException("Can`t create Location");
    }


    @Test(priority = 2)
    public void updateLocation() throws Exception {
        findEntry();
        //select the first (filters are supposed to define it precisely
        click(driver.findElement(By.cssSelector("#list input[name='radio_list']")));
        click(driver.findElement(By.id("editRecord")));

        driver.findElement(By.xpath("//td/input[contains(@id,'palletCount') and @role='textbox']")).clear();
        driver.findElement(By.xpath("//td/input[contains(@id,'palletCount') and @role='textbox']")).sendKeys("2");
        driver.findElement(By.xpath("//td/input[contains(@id,'footprintCount') and @role='textbox']")).clear();
        driver.findElement(By.xpath("//td/input[contains(@id,'footprintCount') and @role='textbox']")).sendKeys("1");

        click(driver.findElement(By.id("updateRecord")));
        WebElement statusMsg = driver.findElement(By.id("msgDisp1"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("successfully updated"))
            throw new RuntimeException("Can`t update Location");
    }

    @Test(priority = 3)
    public void deleteLocation() throws Exception {
        findEntry();
        //fill filters in
        PageHelper.waitForJSandJQueryToLoad(driver);
        click(By.xpath("//table[@id='list']//input[@name='radio_list']"));
        wait.until(webDriver -> driver.findElement(By.id("deleteRecord")).isEnabled());
        click(driver.findElement(By.id("deleteRecord")));
        click(driver.findElement(By.id("dData")));

        long startedAt = System.currentTimeMillis();
        PageHelper.waitForJSandJQueryToLoad(driver);
        LOGGER.warn("took "+(System.currentTimeMillis() - startedAt) +" to finish JS");
        WebElement statusMsg = driver.findElement(By.id("msgDisp1"));
        if (!statusMsg.isDisplayed() || !statusMsg.getText().toLowerCase().contains("successfully deleted"))
            throw new RuntimeException("Can`t delete Location");
    }

    private void findEntry() {
        PageHelper.chooseModule(driver, "Locations & Racks Setup");
        if(isAlertPresent())
            driver.switchTo().alert().accept();
        driver.findElement(By.id("gs_roomSysid")).sendKeys(CreateLocationAndRack.ROOM_NUMBER);
        driver.findElement(By.id("gs_aisleNumber")).clear();
        driver.findElement(By.id("gs_aisleNumber")).sendKeys(CreateLocationAndRack.AISLE_NUMBER);
        driver.findElement(By.id("gs_rackNumber")).clear();
        driver.findElement(By.id("gs_rackNumber")).sendKeys(RACK_NUMBER);
        driver.findElement(By.id("gs_levelValue")).clear();
        driver.findElement(By.id("gs_levelValue")).sendKeys(String.valueOf(LEVEL));
        driver.findElement(By.id("gs_positnNumber")).clear();
        driver.findElement(By.id("gs_positnNumber")).sendKeys(POSITION_NUMBER);
        driver.findElement(By.id("gs_locTypeDtlCode")).clear();
        driver.findElement(By.id("gs_locTypeDtlCode")).sendKeys(TYPE);
        driver.findElement(By.id("gs_statusDtlCode")).clear();
        driver.findElement(By.id("gs_statusDtlCode")).sendKeys("Inactive");
        //driver.findElement(By.id("gs_locId")).clear();
        //driver.findElement(By.id("gs_locId")).sendKeys("A 02100201");
        driver.findElement(By.id("gs_locTypeDtlCode")).sendKeys(Keys.RETURN);
        WebElement loadingImg = driver.findElement(By.id("load_list"));
        wait.until(ExpectedConditions.invisibilityOf(loadingImg));
        if (driver.findElements(By.cssSelector("#list input.radio_list")).size() > 1)
            throw new RuntimeException("Can`t uniquely select just created row. There are more than 1 results");
    }
}
