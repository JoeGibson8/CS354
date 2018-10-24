// This class provides a stubbed-out environment.
// You are expected to implement the methods.
// Accessing an undefined variable should throw an exception.

// Hint!
// Use the Java API to implement your Environment.
// Browse:
//   https://docs.oracle.com/javase/tutorial/tutorialLearningPaths.html
// Read about Collections.
// Focus on the Map interface and HashMap implementation.
// Also:
//   https://www.tutorialspoint.com/java/java_map_interface.htm
//   http://www.javatpoint.com/java-map
// and elsewhere.

// This class provides a stubbed-out environment.
// You are expected to implement the methods.
// Accessing an undefined variable should throw an exception.
import java.util.Map;
import java.util.HashMap;


public class Environment {
//logic implemented to store variables across multiple statements 
	Map<String,Double> map = new HashMap<String, Double>();

	public double put(String s, double num){
		map.put(s,num);
		return num; 
	}

    public double get(int index, String s) throws EvalException{ 
    	return map.get(s);
	}
    
}