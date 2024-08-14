package edu.ncsu.csc316.surveillance.manager;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.surveillance.data.Call;
import edu.ncsu.csc316.surveillance.data.Person;

/**
 * Tests the surveillance manager class
 * @author sachivyas
 */
class SurveillanceManagerTest {
	/** Creates an instance of the surveillance manager class for testing */
	private SurveillanceManager s;
	/** Creates an instance of the surveillance manager class for testing */
	private SurveillanceManager s2;
	/** Creates an instance of the surveillance manager class for testing */
	private SurveillanceManager s4;
	/** Creates an instance of the surveillance manager class for testing */
	private SurveillanceManager s5;
	
	/**
	 * Setting up the files for testing
	 * @throws FileNotFoundException if the file that we are looking for is not available
	 */
	@BeforeEach
	void setUp() throws FileNotFoundException {
		String personFile = "test-files/people2.txt";
		String callFile = "test-files/call2.txt";
		s = new SurveillanceManager(personFile, callFile);
		
		String personFile6 = "test-files/people6.txt";
		String callFile6 = "test-files/call6.txt";
		s2 = new SurveillanceManager(personFile6, callFile6);
		
		String personEmpty = "test-files/emptyPeople.txt";
		String callFile1 = "test-files/calls.txt";
		s4 = new SurveillanceManager(personEmpty, callFile1);
		
		String person = "test-files/people.txt";
		String emptyCalls = "test-files/emptyCalls.txt";
		s5 = new SurveillanceManager(person, emptyCalls);
		
		
	}
	/**
	 * Tests if the people map is being read correctly
	 */
	@Test
	public void testReadValidPeople() {
		Map<String, Person> peopleMap = s.getPeople();
		Person p1 = peopleMap.get("134-530-7421728");
        assertNotNull(p1);
        assertEquals("Roseanna", p1.getFirst());
        assertEquals("Herman", p1.getLast());
        	       
	    Person p2 = peopleMap.get("853-257-0109509");
	    assertNotNull(p2);
	    assertEquals("Sarai", p2.getFirst());
	    assertEquals("Rodriguez", p2.getLast());	 
	        
	     Person p3 = peopleMap.get("358-721-0140950");
	     assertNotNull(p3);
	     assertEquals("Tereasa", p3.getFirst());
	     assertEquals("Kuphal", p3.getLast());
	    	         
	}

	/**
	 * Check if the calls map returns empty for a number we know exists in the file
	 */
	@Test
	public void testReadValidCalls() {		
	        Map<String, List<Call>> callMap = s.getCallsByPerson();
	        List<Call> c1 = callMap.get("134-530-7421728");
	        assertNotNull(c1);
	        Call c = c1.get(0);
	        assertNotNull(c);	
	        List<Call> c2 = callMap.get("134-900-7421728");
	        assertNull(c2);
	}
	/**
	 * Tests the get people by hop method
	 */
	@Test
	public void testGetPeopleByHop() {
        s.getCallsByPerson();
	    Map<String, Integer> hopMap = s.getPeopleByHop("358-721-0140950");	        
	    //assertEquals(0, hopMap.get("358-721-0140950").intValue());
	    assertEquals(1, hopMap.get("663-879-6377778").intValue());
	    assertEquals(2, hopMap.get("442-000-9865092").intValue());   
	    assertEquals(2, hopMap.get("903-282-4112077").intValue());
	    assertEquals(2, hopMap.get("134-530-7421728").intValue());
	    assertEquals(1, hopMap.get("541-777-4740981").intValue());
	}
	/**
	 * Tests the get people by hop method for 6 hops
	 */
	@Test
	public void testHop() {
       s2.getCallsByPerson();
       Map<String, Integer> hopMap = s2.getPeopleByHop("365-271-1141940");
//       assertEquals(0, hopMap.get("365-271-1141940").intValue());
       assertEquals(1, hopMap.get("849-096-4379885").intValue());
       assertEquals(2, hopMap.get("904-865-2427019").intValue());
       assertEquals(3, hopMap.get("134-530-7421728").intValue());
       assertEquals(4, hopMap.get("663-879-6377778").intValue());
       assertEquals(5, hopMap.get("959-904-1274843").intValue());
	   assertEquals(6, hopMap.get("289-378-3996038").intValue());
	}
	/**
	 * Tests for empty invalid files
	 */
	@Test
	public void emptyFileTest() {
		assertEquals("SkipListMap[]", s5.getCallsByPerson().toString());
		assertEquals("SkipListMap[]", s4.getCallsByPerson().toString());
	}

}