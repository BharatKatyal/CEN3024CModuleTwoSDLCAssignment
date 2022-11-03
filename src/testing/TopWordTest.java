package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TopWordTest {

	@Test
	void test() {
		JunitTesting test = new JunitTesting();
		Boolean output = test.TopWordTest("The Poem");
		System.out.println(output); 
		assertEquals(true, output);
	}

}
