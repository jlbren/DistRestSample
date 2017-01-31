package DistRestSample.Client;


import DistRestSample.Contracts.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		ClientCrudActionRun run = new ClientCrudActionRun();
		CustomerObject customer = new CustomerObject(10, "first", "last", "bday");
		run.Reset(customer);
    	try{
    		run.run();
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace(System.out);
    	} finally {
    		for(Exception e : run.getErrors()) {
        		System.out.println(e.getMessage());
        		e.printStackTrace(System.out);
    		}
    	}
    	
    	/*ClientAction action = new ClientAction();
    	
    	URL address = new URL("http://www.sarahkaylor.com");
    	String result = action.Get(address, HttpContentType.TEXT);
    	
    	System.out.println(result);*/
    	
    }
}
