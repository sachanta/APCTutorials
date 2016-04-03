package com.pushtotest.mailercheck;

/**
 * Use a Web mail account to validate the administrator receives
 * the emmitted email message.
 */

import com.pushtotest.selenium.SeleniumHtmlunit;
import java.util.HashMap;

public class CheckEmail {

   /* SeleniumHTMLUnit uses the HTMLUnit head-less browser to operate Selenium commands. */
     SeleniumHtmlunit selenium;
 
    public void setUp() {}

    public void runTest( Object DPLValues, String url, String id, String passwd ) throws Exception
    {
        selenium = new SeleniumHtmlunit();
	selenium.setLogLevel("INFO");

        String myGuidValue = "";

        if ( ( id==null ) || ( passwd == null ) )
        {
            throw new Exception( "Input parameters invalid." );
        }

        if ( DPLValues != null )
        {
    		if ( DPLValues instanceof HashMap )
            {
                myGuidValue = (String) ( (HashMap) DPLValues ).get( "GUID" );
            }
        }

        System.out.println( "CheckEmail: runTest, myGuidValue = " + myGuidValue );

        /* Disabled so that this can be run without a live Internet connection
        selenium.setBaseUrl( url );
        selenium.open("/");
        selenium.type("emailaddress", id);
        selenium.type("password", passwd);
        selenium.clickAndWait("btnG");
        selenium.assertTextPresent( "MailerCheck " + myGuidValue );
        selenium.clickAndWait("//img[@alt='Logoff']");
         */

        new Utils().sleepRandom();

        //For debugging: System.out.println( selenium.getPage().asXml() );
    }
        
    public void tearDown()
    {
    }

}
