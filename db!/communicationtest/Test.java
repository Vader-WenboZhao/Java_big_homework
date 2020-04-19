package communicationtest;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		Random random = new Random();
		int count = 0;
		while(count++<100) {
			System.out.println(random.nextInt(2));
		}
	}
}
