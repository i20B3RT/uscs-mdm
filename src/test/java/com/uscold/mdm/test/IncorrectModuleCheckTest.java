package com.uscold.mdm.test;

import com.uscold.mdm.test.util.PageHelper;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.log4testng.Logger.getLogger;


public class IncorrectModuleCheckTest extends AbstractTestClass {

    public static org.testng.log4testng.Logger logger = getLogger(IncorrectModuleCheckTest.class);

    @Test
    public void selectModule() throws IOException, InterruptedException {

        PageHelper.chooseModule(driver, "_Zone Map_");

    }


}
