package edu.ncsu.csc316.surveillance.manager;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.surveillance.data.Call;
import edu.ncsu.csc316.surveillance.data.Person;
import edu.ncsu.csc316.surveillance.dsa.Algorithm;
import edu.ncsu.csc316.surveillance.dsa.DSAFactory;
import edu.ncsu.csc316.surveillance.dsa.DataStructure;
/**
 * Returns the string representations for the 
 * @author sachivyas
 */
public class ReportManager {
	/** Creating a private instance of the surveillance manager */
	private SurveillanceManager manager;
	/** Formats the date outputted from the txt file */
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
	/** Formats the time outputted from the txt file */
	private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm:ss a");
	/** Creating a people map */
	private Map<String, Person> peopleMap;
	/** Creating a call record map */
	private Map<String, List<Call>> callRecordMap;
	/** Creating a hop record map */
	private Map<String, Integer> hopsMap;
	/** Creating a list of record map for people records */
	private List<Map.Entry<String, Person>> peopleSort;

	/**
	 * Creating a private time stamp comparator to sort the call by time stamp or by ID if two time
	 * stamps are the same
	 */
	private class TimeStampComparator implements Comparator<Call> {
		/**
		 * Compare the two time stamps
		 * @param o1 the first object to compare the time stamp of
		 * @param o2 the second object to compare the time stamp of
		 * @return int based on the comparison of the two items
		 */
		@Override
		public int compare(Call o1, Call o2) {
			if (o1.getTimestamp().compareTo(o2.getTimestamp()) == 0) {
				return o1.getId().compareTo(o2.getId());
			}
			//compare by time stamps first and if equal then compare by ID
			return o1.getTimestamp().compareTo(o2.getTimestamp());
		}
		
	}
	/**
	 * Private helper hop class that implements the comparable interface, Helps us compare the hops of people along with
	 * their names and phone numbers
	 */
	private class Hop implements Comparable<Hop> {
		/** Private instance of the person class */
		private Person p;
		/** Private integer to store the number of hops */
		private int hops;
		/**
		 * Constructor for the hop class
		 * @param p the instance of the person object
		 * @param hops the number of hops the person is away from the given start point
		 */
		private Hop(Person p, int hops) {
			this.p = p;
			this.hops = hops;
		}
		/**
		 * Performs comparison of the hop object
		 * @param o the hop object to perform the comparisons on
		 * @return int based on the comparisons
		 */
		@Override
		public int compareTo(Hop o) {
			int hops2 = Integer.compare(this.hops, o.hops);
			if (hops2 != 0) {
				return hops2;
			}
			else {
				int last = this.p.getLast().compareTo(o.p.getLast());
				if (last != 0) {
					return last;
				}
				int first = this.p.getFirst().compareTo(o.p.getFirst());
				if (first != 0) {
					return first;
				}
				return this.p.getPhoneNumber().compareTo(o.p.getPhoneNumber());
			}	
		}	
	}	
	
	/**
	 * Constructor without the data structure parameter
	 * @param peopleFile the name of the file that contains data about people
	 * @param callFile the name of the file that contains data about calls
	 * @throws FileNotFoundException if the two files are not found
	 */
	public ReportManager(String peopleFile, String callFile) throws FileNotFoundException {
		this(peopleFile, callFile, DataStructure.SKIPLIST);
	}
	/**
	 * Constructor without the data structure parameter
	 * @param peopleFile the name of the file that contains data about people
	 * @param callFile the name of the file that contains data about calls
	 * @param mapType the data structure to use
	 * @throws FileNotFoundException if the two files are not found
	 */
	public ReportManager(String peopleFile, String callFile, DataStructure mapType) throws FileNotFoundException {
		manager = new SurveillanceManager(peopleFile, callFile, mapType);
		DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
		
		DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);
		DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
		DSAFactory.setMapType(mapType);
	}
	/**
	 * Returns a string representation of the person and the calls made by them
	 * @return string representation of the person and the calls made by them
	 */
	public String getCallsByPerson() {
		callRecordMap = manager.getCallsByPerson();
		peopleMap = manager.getPeople();
		if (peopleMap == null || peopleMap.isEmpty()) {
        	return  "No people information was provided.";
        }
		if (callRecordMap == null || callRecordMap.isEmpty()) {
        	return "No calls exist in the call logs.";
        }
        
        peopleSort = DSAFactory.getIndexedList();
       
        
        for (Entry<String, Person> e : peopleMap.entrySet()) {
        	peopleSort.addLast(e);           
        }
       
        for (int i = 0; i < peopleSort.size(); i++) {
            for (int j = i + 1; j < peopleSort.size(); j++) {
                if (peopleSort.get(i).getKey().compareTo(peopleSort.get(j).getKey()) > 0) {
                    Map.Entry<String, Person> temp = peopleSort.get(i);
                    peopleSort.set(i, peopleSort.get(j));
                    peopleSort.set(j, temp);
                }
            }
        }
        
        StringBuilder s = new StringBuilder();
        
        for (Entry<String, Person> e : peopleSort) {
            String phoneNum = e.getKey();
            Person p = e.getValue();
            
            List<Call> c = callRecordMap.get(phoneNum);
           
            if (c == null || c.isEmpty()) {
                s.append("Calls involving ").append(phoneNum).append(" (").append(p.getFirst()).append(" ").
                append(p.getLast()).append(") [\n   (none)\n]\n");
            } 
            
            else {
                s.append("Calls involving ").append(phoneNum).append(" (").append(p.getFirst()).append(" ").
                append(p.getLast()).append(") [\n");
                            	
                c = sortCalls(c);
                
                for (Call callRecord : c) {
                	
                	s.append("   ").append(callRecord.getTimestamp().format(dateFormat)).append(" at ").
                	append(callRecord.getTimestamp().format(timeFormat)).append(" involving ").
                	append(callRecord.getPhoneNumbers().length - 1).append(" other number(s):\n");
                	
                	
                	String[] sorting = callRecord.getPhoneNumbers();
                	Sorter<String> phoneNumberSorter = DSAFactory.getComparisonSorter(null);
                	phoneNumberSorter.sort(sorting);
                	
                    for (String contactNum : sorting) {
                    	if (!contactNum.equals(phoneNum)) {
                    		Person peopleContacted = peopleMap.get(contactNum);
                    		if (peopleContacted != null){
                    			s.append("      ").append(contactNum).append(" (").append(peopleContacted.getFirst()).
                    			append(" ").append(peopleContacted.getLast()).append(")\n");
                    			                         
                            }
                    	}                       
                    }
                }
                
                s.append("]\n");
            }
        }
        

        return s.toString();
	}
	/**
	 * Returns the string representation of the people covered by the warrant 
	 * @param hops the number of hops away from the start point
	 * @param originPhoneNumber the start point to check for the number of hops
	 * @return string representation of the people covered by the warrant
	 */
	public String getPeopleCoveredByWarrant(int hops, String originPhoneNumber) {
		
		peopleMap = manager.getPeople();
		callRecordMap = manager.getCallsByPerson();
		hopsMap = manager.getPeopleByHop(originPhoneNumber);
		if (peopleMap == null || peopleMap.isEmpty()) {
        	return "No people information was provided.";
        }
		if (callRecordMap == null || callRecordMap.isEmpty()) {
        	return "No calls exist in the call logs.";
        }
        
        
        if (peopleMap.get(originPhoneNumber) == null) {
        	return "Phone number " + originPhoneNumber + " does not exist.";
        }
        
        if (hops < 1) {
        	return "Number of hops must be greater than 0.";
        }
        
        StringBuilder s = new StringBuilder();
        
        s.append("Phone numbers covered by a ").append(hops).append("-hop warrant originating from ").
        append(originPhoneNumber).append(" (").append(peopleMap.get(originPhoneNumber).getFirst()).
        append(" ").append(peopleMap.get(originPhoneNumber).getLast()).append(") [\n");
        		
        
	
        List<Hop> hopsSort = DSAFactory.getIndexedList();
        for (Entry<String, Integer> e : hopsMap.entrySet()) {
        	hopsSort.addLast(new Hop(peopleMap.get(e.getKey()), e.getValue()));
        	
        }
        List<Hop> h = hopsSorter(hopsSort);        
        
        for (Hop e : h) {
        	
        	
        	String phone = e.p.getPhoneNumber();
        	int numOfHops = e.hops;
        	
        	if (numOfHops <= hops && !phone.equals(originPhoneNumber)) {
        		Person person = peopleMap.get(phone);
            	s.append("   ").append(numOfHops).append("-hop: ").append(phone).append(" (").
            	append(person.getFirst()).append(" ").append(person.getLast()).append(")\n");             	            	
        	}

        }
        s.append("]\n");
		return s.toString();
	}
	
	/**
	 * Helper method to sort the calls by time stamp
	 * @param l the list of calls to sort
	 * @return call2 the sorted call list
	 */
	private List<Call> sortCalls(List<Call> l) {		
		Sorter<Call> sorter = DSAFactory.getComparisonSorter(new TimeStampComparator());
		//iterate through the array l
		Call[] calls = new Call[l.size()];
		int i = 0;
		for (Call c : l) {
			calls[i] = c;
			i++;
		}
		
		sorter.sort(calls);
		
		List<Call> calls2 = DSAFactory.getIndexedList();
		for (Call a : calls) {
			calls2.addLast(a);
		}
		return calls2;
	}
	
	/**
	 * Helper method to help sort the calls by the number of hops
	 * @param hopsSort the map of phone number and the number of hops associated with the phone number
	 * @return sorted list of the type hop
	 */
	private List<Hop> hopsSorter(List<Hop> hopsSort) {       	
		Sorter<Hop> sorter = DSAFactory.getComparisonSorter(null);
		Hop[] h = new Hop[hopsSort.size()];
		int i = 0;
		for (Hop hop : hopsSort) {
			h[i] = hop;
			i++;
		}
		sorter.sort(h);
		List<Hop> hopSorted = DSAFactory.getIndexedList();
		for (Hop hop : h) {
			hopSorted.addLast(hop);
		}
		return hopSorted;				
	}
}