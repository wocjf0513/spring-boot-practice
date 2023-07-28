import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyMathTest {

	@Test
	void test() {
		MyMath math=new MyMath();
		int result=math.calculateSum(new int[] {1,2,3});
		System.out.println(result);
		assertEquals(result, 5);
	}
	

}
