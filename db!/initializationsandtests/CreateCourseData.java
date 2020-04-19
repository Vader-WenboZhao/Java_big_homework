package initializationsandtests;

import java.io.*;
import java.util.TreeMap;

import data.*;

/**
 * 产生课程信息
 * @author zhaowenbo
 *
 */

public class CreateCourseData {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		String path = "courseData/course";
		File file = new File(path);
		if(file==null)
			file.createNewFile();
		ObjectOutputStream oos = 
				new ObjectOutputStream(
						new BufferedOutputStream(
								new DataOutputStream( 
										new FileOutputStream(file))));
		CourseData dataStructure = new CourseData(1111111111L, 80, "Data Structure");
		CourseData operatingSystem = new CourseData(2222222222L, 100, "Operating System");
		CourseData discreteMathematics = new CourseData(3333333333L, 120, "Discrete Mathematics");
		//System.out.println(dataStructure.cRemain);
		TreeMap<Long, CourseData> map = new TreeMap<Long, CourseData>();
		map.put(dataStructure.cID, dataStructure);
		map.put(operatingSystem.cID, operatingSystem);
		map.put(discreteMathematics.cID, discreteMathematics);
		oos.writeObject(map);
		oos.flush();
		oos.close();
	}
}
