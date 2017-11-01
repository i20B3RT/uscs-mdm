package com.uscold.mdm.test.orders;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ViewExistingOrderPage extends AbstractTestClass {

    @Test
    public void viewExistingOrderPage() throws InterruptedException {
        PageHelper.chooseModule(driver,"Order Maintenance");
        PageHelper.chooseWarehouse(driver,"160");
        WebElement tableBtnSearch = wait.until(ExpectedConditions.elementToBeClickable(By.id("oeBasicSearch")));

    }

}
