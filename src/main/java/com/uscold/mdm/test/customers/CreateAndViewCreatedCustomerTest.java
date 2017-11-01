package com.uscold.mdm.test.customers;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class CreateAndViewCreatedCustomerTest extends AbstractTestClass {

    final static String corporateName = "AUTO_CUST - ";
    final static String addressLineOne = "1 INFINITE LOOP";
    final static String ciTy = "CUPERTINO";
    private String customerNumber = "";

    private boolean succesfullyCreated;
    @Test
    public void createCustomerTest() {
        PageHelper.chooseModule(driver, "Customer Management");
        PageHelper.chooseWarehouse(driver, 160);

        click(driver.findElement(By.id("createNewBtn")));

        click(chooseValueFromStandardDropDown("type_chosen", "customer"));

        driver.findElement(By.id("corporateName")).sendKeys(corporateName+customerNumber);
        driver.findElement(By.id("txt_ConsigneeName")).sendKeys(corporateName+customerNumber
        );
        driver.findElement(By.id("addressLine1")).sendKeys(addressLineOne);
        driver.findElement(By.id("city")).sendKeys(ciTy);
        click(chooseValueFromStandardDropDown("stateList_chosen", "california"));

        driver.findElement(By.id("zip")).clear();
        driver.findElement(By.id("zip")).sendKeys("95014");

        PageHelper.waitForJSandJQueryToLoad(driver);

        customerNumber = driver.findElement(By.id("txt_ConsigneeNumber")).getAttribute("value");

        click(driver.findElement(By.id("basicDtlSubmit")));
        if (!driver.getCurrentUrl().toLowerCase().contains("customer/basicdetails/2/Next*//*.do")) {
            //if page wasn`t changed then it probably means we should verify address
            PageHelper.waitForJSandJQueryToLoad(driver);
            click(driver.findElement(By.id("addverify_enter")));
            driver.findElement(By.id("uspsCommentsDialog")).clear();
            driver.findElement(By.id("uspsCommentsDialog")).sendKeys("test");
            click(driver.findElement(By.id("saveBtn")));
            click(driver.findElement(By.id("basicDtlSubmit")));
        }

        click(chooseValueFromStandardDropDown("customerClassification_chosen", "meats"));
        click(chooseValueFromStandardDropDown("dtTypCde_chosen", "receipt date"));

/*      //this code snippet choose code date value (if we have to choose it. Not in this case)
        click(driver.findElement(By.id("codePopup")));
        click(driver.findElement(By.xpath("//table[@id='addCodeFormat_popup']*//*//**//*[@name='myRadio']")));
        click(driver.findElement(By.id("btnSaveFormt")));*/

        click(driver.findElement(By.id("UomTempSelection")));
        PageHelper.waitForJSandJQueryToLoad(driver);
        click(driver.findElement(By.id("jqg_list_1")));
        click(driver.findElement(By.id("tempSubmit")));

        click(driver.findElement(By.id("additionalDtlSubmit")));

        WebElement statusMsg = driver.findElement(By.id("reportSuccessMsg"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("added Successfully!"))
            throw new RuntimeException("Failed to create customer");

        succesfullyCreated = true;
    }

    @Test
    public void viewCreatedCustomer() {
        Assert.assertTrue("Skip. Customer wasn't created", succesfullyCreated);
        driver.findElement(By.id("txt_searchNumber")).clear();
        driver.findElement(By.id("txt_searchNumber")).sendKeys(customerNumber);
        click(driver.findElement(By.id("searchOne")));
        PageHelper.waitForJSandJQueryToLoad(driver);
        WebElement table = driver.findElement(By.id("list"));
        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
        WebElement row = rows.get(1);
        click(row.findElement(By.xpath("td[@title='" + customerNumber + "']/a")));

        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));

        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
        String accountType = span.getText();

        span = columnContainer.findElement(By.xpath("div[2]/span"));
        String customerNumber = span.getText();

        span = columnContainer.findElement(By.xpath("div[3]/span"));
        String corporateName = span.getText();

        org.testng.Assert.assertTrue(customerNumber.equals(customerNumber));
        org.testng.Assert.assertTrue(accountType.equals("Customer"));
        org.testng.Assert.assertTrue(corporateName.equals(corporateName));

    }


    private WebElement chooseValueFromStandardDropDown(String id, String value) {
        WebElement accTypeContainer = driver.findElement(By.id(id));
        WebElement aElem = accTypeContainer.findElement(By.cssSelector("a.chosen-single"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", aElem);
        click(aElem);
        List<WebElement> accTypes = accTypeContainer.findElements(By.xpath("div[@class='chosen-drop']/ul/li"));
        return accTypes.stream().filter(el -> el.getAttribute("innerText").trim().equalsIgnoreCase(value)).findFirst().get();

    }
}
