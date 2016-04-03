package com.pushtotest.mailercheck;

/**
 * Use a Web contact form to send a message to the system administrator
 */

import com.pushtotest.selenium.SeleniumHtmlunit;
import com.pushtotest.tool.dpl.provided.TheLocker;

public class SendEmail
{
    /* SeleniumHTMLUnit uses the HTMLUnit head-less browser to operate Selenium commands. */
    SeleniumHtmlunit selenium;

    /* RandomGUID creates geniuine unique ID values */
    RandomGUID myguid = new RandomGUID(false);
    
    /* Get the singleton instance of TheLocker. The TestScenario that called
       this class provide the instance. The instance is named myLocker and
       will be shared with CheckEmail.java. */
    TheLocker loc = new TheLocker();

    public void setUp(){}

    public void runTest( String send_url, String send_path, String send_name, String send_adr )
    throws Exception
    {
        String myGuidValue = "";

        selenium = new SeleniumHtmlunit();
		selenium.setLogLevel("INFO");

        /* Get a GUID value and store it in TheLocker */
        myGuidValue = myguid.toShortString();
        loc.put( "GUID", myGuidValue );

        System.out.println( "SendEmail: runTest, myGuidValue = " + myGuidValue );

        /* Disabled so that this can be run without a live Internet connection
        selenium.setBaseUrl( send_url );
        selenium.open( send_path );
        selenium.type("replyto", send_adr );
        selenium.type("topic", "MailerCheck " + myGuidValue );
        selenium.type("comments","From a special place in my heart.");
        selenium.click("form_submit");
        selenium.waitForPageToLoad("30000");
        selenium.assertTextPresent("Thank You");
        */

        new Utils().sleepRandom();
}

    public void tearDown() {}

    public static void main(String [] args) throws Exception{
        new SendEmail().runTest("http://www.pushtotest.com",
                "/docs/example-form", "Jeremy Tester", "jeremy@pushtotest.com");
        Thread.sleep(30000);
        TheLocker k = new TheLocker();
        new CheckEmail().runTest(k.getData(), "http://www.mail2web.com/",
                "caroltester@live.com", "seecarolrun");
    }

}
