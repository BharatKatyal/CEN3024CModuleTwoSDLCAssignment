
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


/*STEP 1: Load html file from URL & Test to confirm you can output file
 *STEP 2: Add words to Array after removing any HTML, Quotes, and requested parameters 
 *STEP 3: Add cleaned up Array to Arraylist
 *STEP 4: Sort list based on values
 *STEP 5: Output List 
 */





public class Main {
	public static void main(String[] args) {
		// Step 1: Get get the html document

		final String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		try {
			final Document document = Jsoup.connect(url).get();
			Elements poem = document.select("p");
			// Step two : add the words to array after removing the html, quotes ect

			String poemRemovedHTML = new String(poem.toString().replaceAll("\\<.*?>", ""));
//            System.out.println("Removed HTML " + poemRemovedHTML);
			String removedQuotes = poemRemovedHTML.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r")
					.replace("\n", "\\n");
			String[] array = removedQuotes.split("[ ,]+");
			System.out.println();

			// Step three add cleaned up words to an arraylist
			ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
//        System.out.println(list.toString());

			HashMap<String, Integer> frequencyMap = new HashMap<>();
			for (String s : list) {
				Integer count = frequencyMap.get(s);
				if (count == null) {
					count = 0;
				}
				frequencyMap.put(s, count + 1);
			}

			// Step four : sorting the HashMap based on values
			Map<String, Integer> map = sortByValue(frequencyMap);

			// Step five : output.
			for (Map.Entry<String, Integer> entry : map.entrySet()) {

				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	// function to sort hashmap based on values
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Creating a list from elements of HashMap
        List<Map.Entry<String, Integer> > list
            = new LinkedList<Map.Entry<String, Integer> >(
                hm.entrySet());
 
        // Sorting the list using Collections.sort() method
        // using Comparator
        Collections.sort(
            list,
            new Comparator<Map.Entry<String, Integer> >() {
                public int compare(
                    Map.Entry<String, Integer> object1,
                    Map.Entry<String, Integer> object2)
                {
                    return (object2.getValue())
                        .compareTo(object1.getValue());
                }
            });
 
        // putting the  data from sorted list back to hashmap
        HashMap<String, Integer> result
            = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> me : list) {
            result.put(me.getKey(), me.getValue());
        }
 
        // returning the sorted HashMap
        return result;
    }
    

}
