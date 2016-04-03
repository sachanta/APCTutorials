package com.pushtotest.mailercheck;

/**
 * Utils - a fun and easy package of Data Production Library (DPL) utility functions
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Utils {

    Random randomGenerator = new Random( System.currentTimeMillis() );

    public void printHashDPL( HashMap map )
    {
        Iterator it = map.keySet().iterator();
        while( it.hasNext() )
        {
            String key = (String) it.next();
            String val = (String) map.get( key );

            System.out.println( "print: key=" + key + ", value=" + val );
        }
    }

    /**
     * Simple echo-to-output function
     * @param message String value to echo to output panel
     */
    public void print( String [] message )
    {
        String myval = "";
        boolean first = false;

        for ( String msg:message )
        {
            if ( first ) { myval += ","; }
            first = true;
            myval += msg;
        }
        System.out.println( "print: " + myval );
    }

    public void sleep( String seconds ) throws java.lang.InterruptedException
    {
        if ( seconds != null )
        {
            Thread.sleep( ( Integer.parseInt( seconds ) ) * 1000 );
        }
    }

    public void sleepRandom() throws java.lang.InterruptedException
    {
        sleep( randomGenerator.nextInt(10) + "" );
    }

}
