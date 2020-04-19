
package test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.net.Socket;
import java.io.*;
import net.sf.json.*;

public class LatchTest {
    // please edit config here
    protected static int ThreadNumber = 100;
    protected static String url = "localhost";
    protected static int port = 2333;

    public static void main(String[] args) throws InterruptedException {
        Runnable taskTemp = new Runnable() {

            @Override
            public void run() {
                JSONObject json = new JSONObject();
                Random random=new Random();
                ArrayList<Long> randomCourseID = new ArrayList<Long>();
                randomCourseID.add((long) 1111111111L);
                randomCourseID.add((long) 2222222222L);
                randomCourseID.add((long) 3333333333L);
                
                long num=random.nextInt(500000)+100000; //学号在00100000~00500000之间
                json.put("method", 0);
                json.put("classes", "Select");
                json.put("sID",num);
                //json.put("cID",randomCourseID.get(random.nextInt(3)));//随机选课
                json.put("cID",1111111111L);
                	
                try {
                    // 发起请求
                	for(int i = 0; i<10; i++) {
	                    Socket socket = new Socket(url, port);
	                    socket.setKeepAlive(true);
	                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                    out.println(json.toString());
	                    System.out.println(Thread.currentThread().getName() + ":"+json.toString());
	                    try {
	                        Thread.sleep(200);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                    String response = in.readLine();
	                    System.out.println(Thread.currentThread().getName() + ":"+response);
	                    socket.close();
                	}
                } catch (Exception e) {

                }
            }
        };

        LatchTest latchTest = new LatchTest();
        latchTest.startTaskAllInOnce(ThreadNumber, taskTemp);
    }

    public long startTaskAllInOnce(int threadNums, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for (int i = 0; i < threadNums*15; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long startTime = System.nanoTime();
        System.out.println("All thread is ready, concurrent going...");
        startGate.countDown();
        endGate.await();
        long endTime = System.nanoTime();
        System.out.println("All thread is completed.");
        return endTime - startTime;
    }
}