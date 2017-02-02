package DistRestSample.Client;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final int ClientThreadCount = 30;
    	final int RunsClient = 10000;
    	
    	List<Exception> errors = new ArrayList<Exception>();
    	try{
    		List<Thread> threads = new ArrayList<Thread>();
    		try{
    			try{
		    		for(int i = 0; i < ClientThreadCount; i++) {
			    		ClientCrudActionRun run = new ClientCrudActionRun(errors);
			    		run.SetIdRange(i * RunsClient, i * RunsClient + RunsClient - 1);
			    		Thread th = new Thread(run);
			    		threads.add(th);
		    		}
    			} catch(Exception e) {
    				threads.clear();
    				throw new Exception(e.getMessage(), e);
    			}
	    		for(Thread t : threads) {
	    			t.start();
	    		}
    		} finally {
    			for(Thread t : threads) {
    				t.join();
    			}
    		}
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace(System.out);
    	} finally {
    		for(Exception e : errors) {
        		System.out.println(e.getMessage());
        		e.printStackTrace(System.out);
    		}
    		if(errors.size() == 0) {
    			System.out.println("No errors, client exiting.");
    		} else {
    			System.out.println("Error count = " + errors.size());
    		}
    	}
    	
    	/*ClientAction action = new ClientAction();
    	
    	URL address = new URL("http://www.sarahkaylor.com");
    	String result = action.Get(address, HttpContentType.TEXT);
    	
    	System.out.println(result);*/
    	
    }
}
