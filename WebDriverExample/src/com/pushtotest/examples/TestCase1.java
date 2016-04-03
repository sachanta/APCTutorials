package com.pushtotest.examples;

/*
 * Shows how to write Selenium 2 WebDriver Test Case and Run In PushToTest TestMaker
 * (c) 2012 PushToTest Inc.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
//import com.pushtotest.tool.PTTStepListener;
import com.appvance.enterprise.tool.APCStepListener;
import java.util.HashMap;

public class TestCase1 {
    
    public void runTest() throws Exception
    {

        APCStepListener.startStep("Initializing FirefoxDriver");
        WebDriver d1 = (WebDriver) new FirefoxDriver();
        APCStepListener.endStep();
        
        APCStepListener.startStep("Opening login.html page");
        d1.get("http://localhost:8080/BrewBizWeb/login.html");
        APCStepListener.endStep();

        APCStepListener.startStep("Filling form values");
        d1.findElement(By.name("id")).sendKeys("bert");
        d1.findElement(By.name("Password")).sendKeys("biz");
        APCStepListener.endStep();

        APCStepListener.startStep("Login form submit");
        d1.findElement(By.name("dologin")).click();
        APCStepListener.endStep();

        APCStepListener.startStep("Checkpoint log-in complete");

        if ( d1.getTitle() != null )
        {
            if (d1.getTitle().toLowerCase().contains("search") )
            {
                APCStepListener.endStep();
                APCStepListener.startStep("Closing FirefoxDriver");
                System.out.println("Results Window Title: " + d1.getTitle() );
                d1.close();
                APCStepListener.endStep();
            }
            else
            {
                APCStepListener.endStep();
                APCStepListener.startStep("Closing FirefoxDriver");
                d1.close();
                APCStepListener.endStep();
                throw new Exception("Result Window Title does not contain: Search" );
            }
        }        
    }
    
    public void testMethodWithArgs( String theURL ) throws Exception
    {
        APCStepListener.startStep("testMethodwithArgs called");
        System.out.println("testMethodWithArgs called with theURL value of " + theURL );
        APCStepListener.endStep();      
    }
    
    public void testMethodWithDPL( Object DPLValues ) throws Exception
    {
        APCStepListener.startStep("testMethodwithArgs called");
        System.out.println("testMethodWithArgs called with theURL value of " +
              (String) ( (HashMap) DPLValues ).get( "myValue" )
        );
        APCStepListener.endStep();      
    }
    
    
    
    
}
