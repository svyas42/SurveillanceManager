package edu.ncsu.csc316.surveillance.manager;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests the report manager class
 * @author sachivyas
 */
class ReportManagerTest {
	/** Creating an instance of the report manager */
	private ReportManager r2;
	/** Creating an instance of the report manager */
	private ReportManager r;
	/** Creating an instance of the report manager */
	private ReportManager r4;
	
//	private ReportManager r5;
	/**
	 * Setting up the files for testing
	 * @throws FileNotFoundException if the file that we are looking for is not available
	 */
	@BeforeEach
	void setUp() throws FileNotFoundException {
		String personFile2 = "test-files/people2.txt";
		String callFile2 = "test-files/call2.txt";
		r2 = new ReportManager(personFile2, callFile2);
		
		String personFile = "test-files/people3.txt";
		String callFile = "test-files/call3.txt";
		r = new ReportManager(personFile, callFile);
		
		String personFile4 = "test-files/people5.txt";
		String callFile4 = "test-files/call5.txt";
		r4 = new ReportManager(personFile4, callFile4);
		
//		String personFile5 = "test-files/people.txt";
//		String callFile5 = "test-files/calls.txt";
//		r5 = new ReportManager(personFile5, callFile5);
	}
	/**
	 * Tests if the person and phone numbers are being printed out in order
	 */
	@Test
	final void test() {		
		
		String actualOutput = r2.getCallsByPerson().toString();
		
		String e = "Calls involving 134-530-7421728 (Roseanna Herman) [\n"
				
	            + "   9/2/2019 at 10:11:43 AM involving 1 other number(s):\n"
	            + "      541-777-4740981 (Brett Mueller)\n"
	            + "   9/9/2019 at 11:10:34 AM involving 1 other number(s):\n"
	            + "      289-378-3996038 (Daniel Walker)\n"
	            + "]\n"
	            + "Calls involving 289-378-3996038 (Daniel Walker) [\n"
	            + "   9/22/2014 at 14:03:37 PM involving 1 other number(s):\n"
	            + "      881-633-0099232 (Enoch Quitzon)\n"
	            + "   9/9/2019 at 11:10:34 AM involving 1 other number(s):\n"
	            + "      134-530-7421728 (Roseanna Herman)\n"
	            + "]\n"
	            + "Calls involving 358-721-0140950 (Tereasa Kuphal) [\n"
	            + "   9/29/2014 at 14:03:37 PM involving 1 other number(s):\n"
	            + "      541-777-4740981 (Brett Mueller)\n"
	            + "   4/22/2020 at 14:56:45 PM involving 1 other number(s):\n"
	            + "      663-879-6377778 (Rudolph Buckridge)\n"
	            + "]\n"
	            + "Calls involving 442-000-9865092 (Albertina Braun) [\n"
	            + "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
	            + "      663-879-6377778 (Rudolph Buckridge)\n"
	            + "      903-282-4112077 (Tomas Nguyen)\n"
	            + "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"

	            //added
	            + "      682-824-0550970 (Mariela Jacobs)\n"
	            + "      959-904-1274843 (Marty Hansen)\n"
	            + "]\n"
	            + "Calls involving 541-777-4740981 (Brett Mueller) [\n"
	            + "   9/29/2014 at 14:03:37 PM involving 1 other number(s):\n"
	            + "      358-721-0140950 (Tereasa Kuphal)\n"
	            + "   5/14/2017 at 10:52:47 AM involving 1 other number(s):\n"
	            + "      663-879-6377778 (Rudolph Buckridge)\n"
	            + "   9/2/2019 at 10:11:43 AM involving 1 other number(s):\n"
	            + "      134-530-7421728 (Roseanna Herman)\n"
	            + "]\n"
	            + "Calls involving 663-879-6377778 (Rudolph Buckridge) [\n"
	            + "   5/14/2017 at 10:52:47 AM involving 1 other number(s):\n"
	            + "      541-777-4740981 (Brett Mueller)\n"
	            + "   4/22/2020 at 14:56:45 PM involving 1 other number(s):\n"
	            + "      358-721-0140950 (Tereasa Kuphal)\n"
	            + "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
	            + "      442-000-9865092 (Albertina Braun)\n"
	            + "      903-282-4112077 (Tomas Nguyen)\n"
	           
	            + "]\n"
	            + "Calls involving 682-824-0550970 (Mariela Jacobs) [\n"
	            + "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
	            + "      442-000-9865092 (Albertina Braun)\n"
	            + "      959-904-1274843 (Marty Hansen)\n"
	            + "]\n"
	            + "Calls involving 853-257-0109509 (Sarai Rodriguez) [\n"
	            + "   (none)\n"
	            + "]\n"
	            + "Calls involving 881-633-0099232 (Enoch Quitzon) [\n"
	            + "   9/22/2014 at 14:03:37 PM involving 1 other number(s):\n"
	            + "      289-378-3996038 (Daniel Walker)\n"
	            + "]\n"
	            + "Calls involving 903-282-4112077 (Tomas Nguyen) [\n"
	            + "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
	            + "      442-000-9865092 (Albertina Braun)\n"
	            + "      663-879-6377778 (Rudolph Buckridge)\n"
	           
	            + "]\n"
	            + "Calls involving 959-904-1274843 (Marty Hansen) [\n"
	            + "   4/29/2020 at 19:26:35 PM involving 2 other number(s):\n"
	            + "      442-000-9865092 (Albertina Braun)\n"
	            + "      682-824-0550970 (Mariela Jacobs)\n"
	            + "]\n";

				
		assertEquals(e, actualOutput);
		String actualOutput2 = r.getCallsByPerson().toString();	
		String e2 = "Calls involving 365-271-1141940 (Devin Shah) [\n" +
					"   12/16/2019 at 15:09:24 PM involving 2 other number(s):\n" +
					"      849-096-4379885 (Kendrick Stokes)\n" +
					"      904-865-2427019 (Shelby Oberbrunner)\n" +
					"]\n" +
					"Calls involving 849-096-4379885 (Kendrick Stokes) [\n" +
					"   12/16/2019 at 15:09:24 PM involving 2 other number(s):\n" +
					"      365-271-1141940 (Devin Shah)\n" +
					"      904-865-2427019 (Shelby Oberbrunner)\n" +
					"]\n" +
					"Calls involving 904-865-2427019 (Shelby Oberbrunner) [\n" +
					"   12/16/2019 at 15:09:24 PM involving 2 other number(s):\n" +
					"      365-271-1141940 (Devin Shah)\n" +
					"      849-096-4379885 (Kendrick Stokes)\n" +
					"]\n";
		//System.out.println(actualOutput2);		
		assertEquals(e2, actualOutput2);
	}
			
	
	/**
	 * Tests the getPersonByWarrant method
	 */
	@Test
	final void testPersonWarrant() {
		String w = r2.getPeopleCoveredByWarrant(2, "358-721-0140950");		
		String expectedOutput = "Phone numbers covered by a 2-hop warrant originating from 358-721-0140950 (Tereasa Kuphal) [\n" +
								"   1-hop: 663-879-6377778 (Rudolph Buckridge)\n" + 
								"   1-hop: 541-777-4740981 (Brett Mueller)\n" +
								"   2-hop: 442-000-9865092 (Albertina Braun)\n" +
								
								"   2-hop: 134-530-7421728 (Roseanna Herman)\n" + 
								
								"   2-hop: 903-282-4112077 (Tomas Nguyen)\n" + 
								"]\n";		
		assertEquals(expectedOutput, w);
		
		String w2 = r2.getPeopleCoveredByWarrant(4, "358-721-0140950");
		String expectedOutput2 = "Phone numbers covered by a 4-hop warrant originating from 358-721-0140950 (Tereasa Kuphal) [\n" +
				"   1-hop: 663-879-6377778 (Rudolph Buckridge)\n" +
				"   1-hop: 541-777-4740981 (Brett Mueller)\n" +
				"   2-hop: 442-000-9865092 (Albertina Braun)\n" + 
				"   2-hop: 134-530-7421728 (Roseanna Herman)\n" + 
				"   2-hop: 903-282-4112077 (Tomas Nguyen)\n" + 
				"   3-hop: 959-904-1274843 (Marty Hansen)\n" +
				"   3-hop: 682-824-0550970 (Mariela Jacobs)\n" +
				"   3-hop: 289-378-3996038 (Daniel Walker)\n" + 
				"   4-hop: 881-633-0099232 (Enoch Quitzon)\n" 
				+ "]\n";	
		assertEquals(expectedOutput2, w2);
	}
	/**
	 * Tests if the person names are being compared by the last name if the they are the same number of hops away 
	 * from a person
	 */
	@Test
	final void testLastName() {
		String w = r4.getPeopleCoveredByWarrant(1, "453-096-4379885");
		String expectedOutput = "Phone numbers covered by a 1-hop warrant originating from 453-096-4379885 (Robert John) [\n"
				+ "   1-hop: 811-096-4379885 (Kendrick Stokes)\n" 
				+ "   1-hop: 611-034-4379885 (Shelby Stokes)\n"
				+ "]\n";
		assertEquals(expectedOutput, w);
	}
	
//	@Test
//	final void testSystemTestPlan() {
//		String actualOutput = r5.getCallsByPerson().toString();
//		System.out.println(actualOutput);
//		String w = r5.getPeopleCoveredByWarrant(-1, "890-478-7411718");
//		System.out.println(w);
//	}
	

}
