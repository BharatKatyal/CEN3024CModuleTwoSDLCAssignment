
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.cj.xdevapi.Statement;
/*STEP 1: Load html file from URL & Test to confirm you can output file
 *STEP 2: Add words to Array after removing any HTML, Quotes, and requested parameters 
 *STEP 3: Add cleaned up Array to Arraylist
 *STEP 4: Sort list based on values
 *STEP 5: Output List 
 */



/**
 * Poem word counter
 * This program takes in a url
 * Then scrapes the H1 and prompts you to confirm that you have the correct poem
 * Then scrapes all the words in the poem
 * Then makes a hashmap of the word and frequencies
 * then sorts the words and displays in console with the number of occurrences  
 * @author Bharat Katyal
 *
 */
public class Main {
	public static void main(String[] args) {
		// Step 1: Get get the html document
		JFrame f = new JFrame();
		String url = "";
		boolean carryOn = true;

		
		/**
		 * Function to keep prompting the user to input a URL and output the H1 from the
		 * 	 URL provided. The user is then asked to confirm if its correct otherwise the
		 * loop starts again.
		 * @param carryOn
		 * @return carryOn & runs remaining program
		 */
		while (carryOn)

			try {
				url = JOptionPane.showInputDialog("Please Enter A URL you would like to scrape");

				Document titleSelect;
				titleSelect = Jsoup.connect(url).get();
				Elements urlTitle = titleSelect.select("h1");
				String urlTitleRemoved = new String(urlTitle.toString().replaceAll("\\<.*?>", ""));

				System.out.println(urlTitleRemoved);
				int a = JOptionPane.showConfirmDialog(f, "Is this the title of the URL? : " + urlTitleRemoved);
				if (a == JOptionPane.YES_OPTION) {
					carryOn = false;
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else {
					carryOn = true;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

//		final String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

		try {
			final Document document = Jsoup.connect(url).get();
			Elements poem = document.select("p");

			// Step two : add the words to array after removing the html, quotes ect

			String poemRemovedHTML = new String(poem.toString().replaceAll("\\<.*?>", ""));
//            System.out.println("Removed HTML " + poemRemovedHTML);
			String removedQuotes = poemRemovedHTML.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r")
					.replace("\n", "\\n");// eliminate all the HTML tags
			String[] array = removedQuotes.split("[ ,]+"); // remove any brackets
			System.out.println();

			// Step three add cleaned up words to an arraylist
			ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
//        System.out.println(list.toString());

			HashMap<String, Integer> frequencyMap = new HashMap<>(); // create hashmap to store the frequencies of the
																		// word
			for (String s : list) {
				Integer count = frequencyMap.get(s);
				if (count == null) {
					count = 0;
				}
				frequencyMap.put(s, count + 1);
			}

			// Step four : sorting the HashMap based on values
			Map<String, Integer> map = sortByValue(frequencyMap);

			// For the Test Check that First word is "The" and has "56" occurances
//	        Map.Entry<String,Integer> firstEntry = null, firstEntryWord = null;
//			  firstEntry = map.entrySet().stream().findFirst().get();
//			  firstEntryWord = map.entrySet().stream().findFirst().get();
//
//			  System.out.println("this is first entry "+ firstEntryWord);
//			  
//			  TopWordTest.test(firstEntryWord);
			
			
			


			
			
			
			
		
	

			// Step five : output.
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String insertWord = "";
				insertWord = entry.getKey();
				
				//DB CONNECT
				 java.sql.Statement stmt = null;
				 ResultSet rs = null;
				  try{
					   String driver = "com.mysql.cj.jdbc.Driver";
					   
					   String urldb ="jdbc:mysql://localhost:3306/poemDb";
					   String username = "root";
					   String password = "Bharat@Katyal";
					   Class.forName(driver);
					   
					   Connection conn = DriverManager.getConnection(urldb,username,password);
					   stmt = conn.createStatement();
					   
					   stmt.executeUpdate("INSERT INTO wordOccurrences  (word)" + 
				                "VALUES ('"+insertWord+"')");
					   System.out.println(insertWord + " : Has been inserted into DB");

//						rs = stmt.executeQuery("Select word from wordOccurrences");

						 
						
//						  while(rs.next()){
////						         String str = rs.getString("word");//here rs will be having multiple methods for multiple data types, EMP_NAME is the column name
////						         System.out.println(str);
//						    }
		//
//						    rs.close();           

					   
					  } catch(Exception e){System.out.println(e);}
					  
					  

				
				
				// DB CONNECT END
				
				
				
			

				System.out.println(entry.getKey() + ": " + entry.getValue());
//				System.out.println(entry.getKey());
//				   stmt.executeUpdate("INSERT INTO wordOccurrences  (word)" + 
//			                "VALUES ('"+insertWord+"')");
				
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	/**
	 * This compares the top word and word count to a static string containing word and word count
	 * 	 * 
	 * @param word
	 * @return endTime
	 */
	public boolean TopWordTest(String word) {
		String topWod = "the=56";
		Boolean test = false;
		if (word == topWod) {
			test = true;
		} else {
			test = false;

		}

		return test;
	}

	//

	
	/**
	 *  This function is to sort hashmap based on values
	 * 	 * 
	 * @param Hashmap - String & int
	 * @return sorted Hashmap
	 */
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
		// Creating a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sorting the list using Collections.sort() method
		// using Comparator
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> object1, Map.Entry<String, Integer> object2) {
				return (object2.getValue()).compareTo(object1.getValue());
			}
		});

		// putting the data from sorted list back to hashmap
		HashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> me : list) {
			result.put(me.getKey(), me.getValue());
		}

		// returning the sorted HashMap
		return result;
	}
	
	public static Connection getConnection() throws Exception{
//		 Connection conn = null;
		 java.sql.Statement stmt = null;
		 ResultSet rs = null;
		  try{
			   String driver = "com.mysql.cj.jdbc.Driver";
			   
			   String url ="jdbc:mysql://localhost:3306/poemDb";
			   String username = "root";
			   String password = "Bharat@Katyal";
			   Class.forName(driver);
			   String insertWord= "heeeeee";
			   
			   Connection conn = DriverManager.getConnection(url,username,password);
			   System.out.println("Connected");
			   stmt = conn.createStatement();
			   
			   stmt.executeUpdate("INSERT INTO wordOccurrences  (word)" + 
		                "VALUES ('"+insertWord+"')");
//				rs = stmt.executeQuery("Select word from wordOccurrences");

				 
				
//				  while(rs.next()){
////				         String str = rs.getString("word");//here rs will be having multiple methods for multiple data types, EMP_NAME is the column name
////				         System.out.println(str);
//				    }
//
//				    rs.close();           

			   
			   return conn;
			  } catch(Exception e){System.out.println(e);}
			  
			  
			  return null;
			 }

}

/*
 * WORKING BACKGUP
 * 
 * 
 * import java.io.IOException; import java.util.ArrayList; import
 * java.util.Arrays; import java.util.Collections; import java.util.Comparator;
 * import java.util.HashMap; import java.util.LinkedHashMap; import
 * java.util.LinkedList; import java.util.List; import java.util.Map; import
 * org.jsoup.Jsoup; import org.jsoup.nodes.Document; import
 * org.jsoup.select.Elements; import javax.swing.*;
 * 
 * 
 * /*STEP 1: Load html file from URL & Test to confirm you can output file STEP
 * 2: Add words to Array after removing any HTML, Quotes, and requested
 * parameters STEP 3: Add cleaned up Array to Arraylist STEP 4: Sort list based
 * on values STEP 5: Output List
 * 
 * 
 * 
 * 
 * 
 * 
 * public class Main { public static void main(String[] args) { // Step 1: Get
 * get the html document
 * 
 * JFrame f = new JFrame();
 * 
 * String url =""; url =JOptionPane.showInputDialog("Please Enter A URL");
 * System.out.println(url);
 * 
 * 
 * 
 * 
 * 
 * try { Document titleSelect; titleSelect = Jsoup.connect(url).get(); Elements
 * urlTitle = titleSelect.select("h1"); String urlTitleRemoved = new
 * String(urlTitle.toString().replaceAll("\\<.*?>", ""));
 * 
 * System.out.println(urlTitleRemoved); int
 * a=JOptionPane.showConfirmDialog(f,"Are you sure?" + urlTitleRemoved);
 * if(a==JOptionPane.YES_OPTION){
 * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); }
 * 
 * 
 * } catch (IOException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * 
 * 
 * 
 * 
 * 
 * // final String url =
 * "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
 * 
 * 
 * try { final Document document = Jsoup.connect(url).get(); Elements poem =
 * document.select("p");
 * 
 * 
 * 
 * // Step two : add the words to array after removing the html, quotes ect
 * 
 * String poemRemovedHTML = new String(poem.toString().replaceAll("\\<.*?>",
 * "")); // System.out.println("Removed HTML " + poemRemovedHTML); String
 * removedQuotes = poemRemovedHTML.replace("\\", "\\\\").replace("\"",
 * "\\\"").replace("\r", "\\r") .replace("\n", "\\n"); String[] array =
 * removedQuotes.split("[ ,]+"); System.out.println();
 * 
 * // Step three add cleaned up words to an arraylist ArrayList<String> list =
 * new ArrayList<>(Arrays.asList(array)); //
 * System.out.println(list.toString());
 * 
 * HashMap<String, Integer> frequencyMap = new HashMap<>(); for (String s :
 * list) { Integer count = frequencyMap.get(s); if (count == null) { count = 0;
 * } frequencyMap.put(s, count + 1); }
 * 
 * // Step four : sorting the HashMap based on values Map<String, Integer> map =
 * sortByValue(frequencyMap);
 * 
 * // Step five : output. for (Map.Entry<String, Integer> entry :
 * map.entrySet()) {
 * 
 * System.out.println(entry.getKey() + ": " + entry.getValue()); } } catch
 * (Exception ex) { ex.printStackTrace(); }
 * 
 * } // function to sort hashmap based on values public static HashMap<String,
 * Integer> sortByValue(HashMap<String, Integer> hm) { // Creating a list from
 * elements of HashMap List<Map.Entry<String, Integer> > list = new
 * LinkedList<Map.Entry<String, Integer> >( hm.entrySet());
 * 
 * // Sorting the list using Collections.sort() method // using Comparator
 * Collections.sort( list, new Comparator<Map.Entry<String, Integer> >() {
 * public int compare( Map.Entry<String, Integer> object1, Map.Entry<String,
 * Integer> object2) { return (object2.getValue())
 * .compareTo(object1.getValue()); } });
 * 
 * // putting the data from sorted list back to hashmap HashMap<String, Integer>
 * result = new LinkedHashMap<String, Integer>(); for (Map.Entry<String,
 * Integer> me : list) { result.put(me.getKey(), me.getValue()); }
 * 
 * // returning the sorted HashMap return result; }
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 */
