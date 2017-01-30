package DistRestSample.Client;

import java.net.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	ClientAction action = new ClientAction();
    	
    	URL address = new URL("http://www.sarahkaylor.com");
    	String result = action.Get(address, HttpContentType.TEXT);
    	
    	System.out.println(result);
    	
    }
}
