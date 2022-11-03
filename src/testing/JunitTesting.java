package testing;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JunitTesting {
		
	public Boolean TopWordTest(String word) {
		
		Boolean test = null;

		
		
		try {
			String url ="https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

			Document titleSelect;
			titleSelect = Jsoup.connect(url).get();
			Elements urlTitle = titleSelect.select("h1");
			String urlTitleRemoved = new String(urlTitle.toString().replaceAll("\\<.*?>", ""));
			
			
			System.out.println("test start");

			System.out.println(urlTitleRemoved);
			System.out.println(word);
			System.out.println("test end");



			if(word.equals(urlTitleRemoved)) {
				System.out.println("true run ");

				test= true;

			}else {
				System.out.println("false run ");

				test= false;
			

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			}
		
		
		
		
		return test;
	}
}
