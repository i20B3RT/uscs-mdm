package com.uscold.mdm.test.orders;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ShipOrderInquiryTest extends AbstractTestClass {

    @Test(priority = 1)
    public void validateInputsForDifferentEntries()throws Exception {
        PageHelper.chooseModule(driver,"Order Maintenance");
        PageHelper.chooseWarehouse(driver, 160);

        WebElement textTicket = driver.findElement(By.id("textTicket"));
        WebElement dropdown = driver.findElement(By.xpath("//div[@id='dropdownOrderEntry_chosen']"));
        WebElement aInsideDropDown = dropdown.findElement(By.tagName("a"));
        click(aInsideDropDown);
        List<WebElement> options = dropdown.findElements(By.xpath("div/ul/li"));

        click(options.stream().filter(el -> el.getText().toLowerCase().contains("appointment")).findFirst().get());
        validateInput(textTicket, 10);

        click(aInsideDropDown);
        options = dropdown.findElements(By.xpath("div/ul/li"));
        click(options.stream().filter(el -> el.getText().toLowerCase().contains("load")).findFirst().get());
        validateInput(textTicket, 25);

        click(aInsideDropDown);
        options = dropdown.findElements(By.xpath("div/ul/li"));
        click(options.stream().filter(el -> el.getText().toLowerCase().contains("order")).findFirst().get());
        validateInput(textTicket, 25);


        click(aInsideDropDown);
        options = dropdown.findElements(By.xpath("div/ul/li"));
        click(options.stream().filter(el -> el.getText().toLowerCase().contains("po #")).findFirst().get());
        validateInput(textTicket, 25);


        click(aInsideDropDown);
        options = dropdown.findElements(By.xpath("div/ul/li"));
        click(options.stream().filter(el -> el.getText().toLowerCase().contains("pool")).findFirst().get());
        validateInput(textTicket, 12);

        click(aInsideDropDown);
        options = dropdown.findElements(By.xpath("div/ul/li"));
        click(options.stream().filter(el -> el.getText().toLowerCase().contains("status")).findFirst().get());
        click(driver.findElement(By.xpath("//*[@id=\"dropTicketPanel\"]/div/button")));
        List<WebElement> statusTypes = driver.findElements(By.xpath("//*[@id=\"dropTicketPanel\"]/div/div/ul/li"));
        List<String> optionTexts = statusTypes.stream().map(el -> el.getText()).collect(Collectors.toList());
        Assert.assertTrue(optionTexts.contains("All Pending"));
        Assert.assertTrue(optionTexts.contains("Open"));
        Assert.assertTrue(optionTexts.contains("PIP"));
        Assert.assertTrue(optionTexts.contains("BOL Printed"));
        Assert.assertTrue(optionTexts.contains("Advisement"));
        Assert.assertTrue(optionTexts.contains("Exception"));
        Assert.assertTrue(optionTexts.contains("Posted"));
        Assert.assertTrue(optionTexts.contains("Cancelled"));
        click(driver.findElement(By.xpath("//*[@id=\"dropTicketPanel\"]/div/button")));

    }
    @Test(priority = 2)
    public void verifyShipDateRange() throws Exception {
        click(driver.findElement(By.xpath("//*[@id=\"expShipDateRange1\"]")));
        WebElement datePicker = driver.findElement(By.xpath("//div[@class='date-picker-wrapper']"));
        WebElement table = driver.findElement(By.xpath("//table[@class='month1']"));

        WebElement monthsSelect = table.findElement(By.xpath(".//select[@class='rangeMonth']"));
        click(monthsSelect);
        click(monthsSelect.findElement(By.xpath("option[9]")));

        WebElement yearsSelect = table.findElement(By.xpath(".//select[@class='rangeYear']"));
        click(yearsSelect);
        click(yearsSelect.findElement(By.xpath("option[@value='2017']")));

        //looking for 18th day. Some dates can occur twice in single table
        WebElement el = table.findElements(By.xpath("tbody/tr/td/div[text()='18']")).get(0);
        click(el);

        WebElement timeDiv = datePicker.findElement(By.xpath(".//div[@class='time']/div[@class='time1']"));
        WebElement hourSelect = timeDiv.findElement(By.xpath("div[@class='hour']/select"));
        click(hourSelect);
        click(hourSelect.findElement(By.xpath("option[@value='12']")));

        WebElement minuteSelect = timeDiv.findElement(By.xpath("div[@class='minute']/select"));
        click(minuteSelect);
        click(minuteSelect.findElement(By.xpath("option[@value='30']")));

        String generatedFromTxt = datePicker.findElement(By.xpath("div[@class='footer']//b[@class='start-day']")).getText();
        Assert.assertEquals("09/18/2017", generatedFromTxt.trim().toLowerCase().substring(0, 10));
        Assert.assertEquals("12:30", generatedFromTxt.substring(10).trim());

        //'To' side
        table = driver.findElement(By.xpath("//table[@class='month2']"));

        monthsSelect = table.findElement(By.xpath(".//select[@class='rangeMonth']"));
        click(monthsSelect);
        click(monthsSelect.findElement(By.xpath("option[9]")));

        yearsSelect = table.findElement(By.xpath(".//select[@class='rangeYear']"));
        click(yearsSelect);
        click(yearsSelect.findElement(By.xpath("option[@value='2017']")));

        //looking for 18th day. Some dates can occur twice in single table
        el = table.findElements(By.xpath("tbody/tr/td/div[text()='23']")).get(0);
        click(el);

        timeDiv = datePicker.findElement(By.xpath(".//div[@class='time']/div[@class='time2']"));
        hourSelect = timeDiv.findElement(By.xpath("div[@class='hour']/select"));
        click(hourSelect);
        click(hourSelect.findElement(By.xpath("option[@value='23']")));

        minuteSelect = timeDiv.findElement(By.xpath("div[@class='minute']/select"));
        click(minuteSelect);
        click(minuteSelect.findElement(By.xpath("option[@value='59']")));

        generatedFromTxt = datePicker.findElement(By.xpath("div[@class='footer']//b[@class='end-day']")).getText();
        Assert.assertEquals("09/23/2017", generatedFromTxt.trim().toLowerCase().substring(0, 10));
        Assert.assertEquals("23:59", generatedFromTxt.substring(10).trim());

        //try to choose different end date - problem will raise.

        monthsSelect = table.findElement(By.xpath(".//select[@class='rangeMonth']"));
        click(monthsSelect);
        click(monthsSelect.findElement(By.xpath("option[9]")));

        yearsSelect = table.findElement(By.xpath(".//select[@class='rangeYear']"));
        click(yearsSelect);
        click(yearsSelect.findElement(By.xpath("option[@value='2017']")));

        //looking for 18th day. Some dates can occur twice in single table
        el = table.findElements(By.xpath("tbody/tr/td/div[text()='18']")).get(0);
        click(el);

        timeDiv = datePicker.findElement(By.xpath(".//div[@class='time']/div[@class='time2']"));
        hourSelect = timeDiv.findElement(By.xpath("div[@class='hour']/select"));
        click(hourSelect);
        click(hourSelect.findElement(By.xpath("option[@value='0']")));

        minuteSelect = timeDiv.findElement(By.xpath("div[@class='minute']/select"));
        click(minuteSelect);
        click(minuteSelect.findElement(By.xpath("option[@value='0']")));

        generatedFromTxt = datePicker.findElement(By.xpath("div[@class='footer']//b[@class='end-day']")).getText();
        Assert.assertEquals("09/18/2017", generatedFromTxt.trim().toLowerCase().substring(0, 10));
        Assert.assertEquals("00:00", generatedFromTxt.substring(10).trim());

    }


    private void validateInput(WebElement inputField, int maxLength) {
        StringBuilder bld = new StringBuilder();
        for (int i = 1; i < 100; i++)
            bld.append(i % 10);
        String buff = bld.toString();
        inputField.clear();
        inputField.sendKeys(buff.substring(maxLength + 1));
        Assert.assertTrue(inputField.getAttribute("value").length() == maxLength);
    }

    private void validateNumeric(WebElement inputField) {
        inputField.clear();
        inputField.sendKeys("1a2b3c4d");
        Assert.assertTrue(inputField.getAttribute("value").equals("1234"));
    }
}
