package edu.ncsu.csc316.surveillance.manager;

import java.io.FileNotFoundException;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.queue.Queue;
import edu.ncsu.csc316.surveillance.data.Call;
import edu.ncsu.csc316.surveillance.data.Person;
import edu.ncsu.csc316.surveillance.dsa.Algorithm;
import edu.ncsu.csc316.surveillance.dsa.DSAFactory;
import edu.ncsu.csc316.surveillance.dsa.DataStructure;
import edu.ncsu.csc316.surveillance.io.InputReader;
/**
 * Helps us map phone numbers to the people called and the number of hops
 * @author sachivyas
 */
public class SurveillanceManager {

    // Add private fields, as necessary
	/** List of people */
	private List<Person> list;
	/** List of calls */
	private List<Call> list2;
	/** Map of phone number and people */
	private Map<String, Person> mapPersons;
	/** Map of phone number and people */
	private Map<String, Person> mapPersons2;
	/** Map of phone number and call records */
	private Map<String, List<Call>> mapCallRecords;
	/** Map of phone number and call records */
	private Map<String, List<Call>> mapCallRecords2;
	/** Map of phone number and number of hops away */
	private Map<String, Integer> mapWithHops;
	/**
	 * Constructor without the data structure parameter
	 * @param peopleFile the name of the file that contains data about people
	 * @param callFile the name of the file that contains data about calls
	 * @throws FileNotFoundException if the two files are not found
	 */
    public SurveillanceManager(String peopleFile, String callFile) throws FileNotFoundException {
    	//calls the other constructor
        this(peopleFile, callFile, DataStructure.SKIPLIST);
    }
    /**
	 * Constructor without the data structure parameter
	 * @param peopleFile the name of the file that contains data about people
	 * @param callFile the name of the file that contains data about calls
	 * @param mapType the data structure to use
	 * @throws FileNotFoundException if the two files are not found
	 */
    public SurveillanceManager(String peopleFile, String callFile, DataStructure mapType)
            throws FileNotFoundException {
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
        DSAFactory.setMapType(mapType);
              
        //Comparator<String> s = Comparator.naturalOrder();
        mapPersons = DSAFactory.getMap(null);        
        mapCallRecords = DSAFactory.getMap(null);
        mapWithHops = DSAFactory.getMap(null);
        
        mapPersons2 = DSAFactory.getMap(null);        
        mapCallRecords2 = DSAFactory.getMap(null);
        
        list = InputReader.readPersonData(peopleFile);
        list2 = InputReader.readCallData(callFile);              
    }
    
    /**
     * Returns a map with the person and the people associated with it
     * @return map of phone number and person
     */
    public Map<String, Person> getPeople() {
    	if (list.isEmpty()) {
    		return mapPersons2;
    	}

    	for (Person p1 : list) {
    		String phoneNum = p1.getPhoneNumber();
    		
    		if (phoneNum != null && p1 != null) {
    			mapPersons.put(phoneNum, p1);
    		}
    		    		   
    	}
    	
		return mapPersons;
		
    }
    
    /**
     * Return a map of Phone Number, List of Calls
     * @return phone number and list of calls
     */
    public Map<String, List<Call>> getCallsByPerson() {

    	if (list2.isEmpty() || list.isEmpty()) {
    		return mapCallRecords2;
    	}
    	
    	for (Call c : list2) {
    		
    		String[] phoneNumbers = c.getPhoneNumbers();
    		
    		if (phoneNumbers.length != 0) {
	    		for (String caller : phoneNumbers) {   		
	    			
	    			if (mapCallRecords.get(caller) == null) {
	    					    				
	                    mapCallRecords.put(caller, DSAFactory.getIndexedList());
	    			}    			
	    				
	    			mapCallRecords.get(caller).addLast(c);
	    		}
    		}
    	}        
    	return mapCallRecords;
    }
    
    /**
     * Return a map of Phone number, number of hops
     * @param originPhoneNumber the phone to check the hops from
     * @return the phone number and the number of hops
     */
    public Map<String, Integer> getPeopleByHop(String originPhoneNumber) {
    	
         Queue<String> q = DSAFactory.getQueue();
         
         if (mapCallRecords.isEmpty()) {
             return mapWithHops;
         }
         q.enqueue(originPhoneNumber);
         mapWithHops.put(originPhoneNumber, 0);
         
         while (!q.isEmpty()) {
        	 String firstPhoneNumber = q.dequeue();
        	 int currentHop = mapWithHops.get(firstPhoneNumber);
             
        	 List<Call> listCallRecords = mapCallRecords.get(firstPhoneNumber);
        	 
        	 if (listCallRecords != null) {
        		 
	        	 for (Call c1 : listCallRecords) {
	        		 
	        		 String[] contactNumbers = c1.getPhoneNumbers();
	        
	        		 for (String number : contactNumbers) {
	        			 if (mapWithHops.get(number) == null && !number.equals(firstPhoneNumber) && 
	        					 firstPhoneNumber != null) {  				 
	        				 mapWithHops.put(number, currentHop + 1);
	        				 q.enqueue(number);
	        				 	
	        			 }
	        		 }
	        	 }
        	 }
         }
     	mapWithHops.remove(originPhoneNumber);
     	

         return mapWithHops;
    }
    
}