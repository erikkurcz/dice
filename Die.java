import java.util.*;
import java.io.*;

public class Die {

	Random rand = new Random();

	// a list to hold history of rolls of all dice
	public static List<String> allrolls = new ArrayList<String>();
	String name = "";
	int face;
	int rollct = 0;
	List<Integer> rollarr = new ArrayList<Integer>();

	public Die(String givenname){
		name = givenname;
	}

	public void roll(){
		// Random rand = new Random();
		face = rand.nextInt(6) + 1;
		rollarr.add(face);
		rollct++;

		// update allrolls
		allrolls.add("Die "+name+" - Roll #"+rollct+": "+face);
	}

	public void printRollHistory(){
		for (int i = 0;i<rollarr.size();i++){
			System.out.println("Roll #"+i+": "+rollarr.get(i));
		}
	}

	public int getRollCount(){
		return rollct;
	}

	public int getFace(){
		return face;
	}

	public static void printAllRollHistory(){
		for (String x: allrolls){
			System.out.println(x);
		}
	}

	public static void main(String[] args){

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter how many attempts you'd like to get doubles: ");
		Integer tryForDoubles = scan.nextInt();

		System.out.println("Let's see how many doubles we can get in "+tryForDoubles+" rolls of both dice...");

		// init dice
		Die die = new Die("A");
		Die die2 = new Die("B");

		List<String> doubles = new ArrayList<String>();

		for (int i=0;i<tryForDoubles;i++){
			die.roll();
			die2.roll();

			if (die.getFace() == die2.getFace()){
				doubles.add("Doubles rolled at attempt #"+i+"\t Face value: "+die.getFace());
			}
		}

		// print out doubles
		System.out.println("Total of "+doubles.size()+" doubles in "+tryForDoubles+" attempts.");

		if (doubles.size()>tryForDoubles%10){
			System.out.println("There are a significant number of doubles...would you still like to print them? (y/n)");
			char resp = scan.next().charAt(0);
			String stringresp = Character.toString(resp);
			if (stringresp.equalsIgnoreCase("y")){
				for (String s: doubles){
					System.out.println(s);
				}
			}
		} else {
			for (String s: doubles){
				System.out.println(s);
			}
		}

		HashMap<String,Integer> freq = faceFrequency(doubles);
		printHashmap(freq);
	}

	public static HashMap<String,Integer> faceFrequency(List<String> doubles){
		// takes in ArrayList of strings composed in main function
		// puts out hashmap of String:Integer of each face and frequency of each
		// goal is to determine whether 'random' is really random

		// get list of all numbers in doubles rolled
		HashMap<String,Integer> freq = new HashMap<String,Integer>();

		String key = "";
		Integer value = null;
		
		// enter known values:
		for (Integer i=0;i<6;i++){
			key = Integer.toString(i+1);
			freq.put(key,value);
		}

		// now loop through doubles and increment values where necessary
		for (String s: doubles){
			key = Character.toString(s.charAt(s.length()-1)); // gets freq number off end of string
			value = freq.get(key); // gets current value

			// test for null
			if (value == null) {
				freq.put(key,1);
			} else {
				value++; // if this is creating a pointer, is it necessary to use freq.put()?
				freq.put(key,value);
			}
		}

		return freq;

	}

	public static void printHashmap(HashMap<String,Integer> hmap){

		System.out.println("\n****************************************\nFrequency Map of Rolled Doubles\n****************************************\n");

		// iterating using for loop because hashmap order is not guraranteed
		for (Map.Entry<String,Integer> entry: hmap.entrySet()){
			System.out.println("Face value '"+entry.getKey()+"': "+entry.getValue());
		}
	}

}