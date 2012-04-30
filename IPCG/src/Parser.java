import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.UIManager;

import UI.GameWindow;

public class Parser {
	
	ArrayList<ArrayList<Integer>> playerData;
	ArrayList<ArrayList<ArrayList<Integer>>> globalDeaths;
	ArrayList<ArrayList<ArrayList<Integer>>> globalDifficulty;
	
	ArrayList<Integer> currPlayerData;
	ArrayList<ArrayList<Integer>> levelDeaths;
	ArrayList<ArrayList<Integer>> levelDifficulty;
	
	String dirName = new String("/Users/Tirhakah/Desktop/data");
	
	public static void main(String[] args) {
		new Parser();
	}
	
	Parser() {
		
		playerData = new ArrayList<ArrayList<Integer>>();
		globalDeaths = new ArrayList<ArrayList<ArrayList<Integer>>>();
		globalDifficulty = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		File dir = new File(dirName);

		String[] children = dir.list();
		if (children == null) {
		    // Either dir does not exist or is not a directory
			System.err.println("Invalid directory: " + dir.getAbsolutePath());
			return;
		} else {
//		    for (int i=0; i<children.length; i++) {
//		        // Get filename of file or directory
//		        String filename = children[i];
//		    }
		}

		// It is also possible to filter the list of returned files.
		// This example does not return any files that start with `.'.
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
//		    	System.err.println(name);
		        return !name.startsWith("o") && name.endsWith(".txt");
		    }
		};
//		children = dir.list(filter);


		// The list of files can also be retrieved as File objects
		File[] files = dir.listFiles(filter);
		
//		int stop = 0;
		for (File file : files) {
			try {
				System.out.println("Parsing " + file.getName());
				parseFile(file);
//				if (stop > 7)				break;
//				stop++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			output();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void output() throws IOException {
		
		
		FileWriter fileWrite = new FileWriter(new File(dirName + "/output.txt"));

		//print the player data
//		for (ArrayList<Integer> list : playerData) {
//			for (Integer num : list) {
//				
//				fileWrite.write(num+", ");
//			}
//			fileWrite.write(";\n");
//		}
		
		
		for (int i = 0; i < 4; i++) {
			for (ArrayList<ArrayList<Integer>> levelDeaths : globalDeaths) {
				for (Integer num : levelDeaths.get(i)) {
					fileWrite.write(num + ", ");
				}
				fileWrite.write(";\n");
			}
			fileWrite.write("\n");
		}
		
//		for (int i = 0; i < 4; i++) {
//			boolean noChange = true;
//			for (int j = 0; noChange; j++) {
//				noChange = false;
//				for (ArrayList<ArrayList<Integer>> list : globalDeaths) {
//					try {
//						fileWrite.write(list.get(i).get(j)+", ");
//						noChange = true;
//					}
//					catch (Exception e) { }
//				}
//				fileWrite.write(";\n");
//			}
//		}
		fileWrite.flush();
		fileWrite.close();
		
		
	}
	
	public void parseFile(File file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
				
		levelDeaths = new ArrayList<ArrayList<Integer>>();
		levelDifficulty= new ArrayList<ArrayList<Integer>>();
		currPlayerData = new ArrayList<Integer>();
		
		currPlayerData.add(Integer.parseInt(file.getName().substring(0, file.getName().indexOf('.'))));
		
		ArrayList<String> currLevel = new ArrayList<String>();
		boolean level = false;
		int levelNum = 0;
		int[] ans = new int[6];
		
		String line;

		while ((line = reader.readLine()) != null) {
			switch (line.charAt(0)) {
			case '#':
				break;
			case '-':
				if (level) {
					currPlayerData.add(levelNum);
					parseLevel(currLevel, levelNum);
					currLevel.clear();
					level = false;
					levelNum++;
				}
				break;
			case 'q':
				ans[line.charAt(1) - '0' - 1] = line.charAt(4) - '0';
				break;
			case '0':
			case '1':
			case 't':
			case 'd':
			case 'g':
				level = true;
				currLevel.add(line);
				break;
			default:
				System.err.println("Something unexpected happened: " + line);
			}
		}
		for (int i = 0; i < ans.length; i++) {
			currPlayerData.add(ans[i]);
		}
		reader.close();
		playerData.add(currPlayerData);
		globalDeaths.add(levelDeaths);
		globalDifficulty.add(levelDifficulty);
		for (ArrayList<Integer> i : levelDeaths) {
			for (Integer integer : i) {
				System.err.println(integer);
			}
		}
	}
	
	public void parseLevel(ArrayList<String> level, int levelNum) {
		int end = 0;
		int time = 0;
		int death = 0;
		int successes = 0;
		double velocity;
		
		ArrayList<Integer> difficulty = new ArrayList<Integer>();
		ArrayList<Integer> deaths = new ArrayList<Integer>();
		deaths.add(0);
		
		
		for (String data : level) {
			System.err.println(data);
			switch (data.charAt(0)) {
			case 'd':
				difficulty.add(Integer.parseInt(data.substring(data.lastIndexOf(' ')+1)));
				break;
			case '1':
				deaths.add(new Integer(deaths.get(deaths.size()-1)));	//add a new tick with the same deaths
				successes++;
				break;
			case '0':
				deaths.add(new Integer(deaths.get(deaths.size()-1)+1));	//add a new tick with one more death
				System.err.println(" adding: " + new Integer(deaths.get(deaths.size()-1)+1));
				death++;
				break;
			case 'g':
				end = Integer.parseInt(data.substring(data.lastIndexOf(' ')+1));
				break;
			case 't':
				time = Integer.parseInt(data.substring(data.lastIndexOf(' ')+1));
				break;
			}
		}
		
		velocity = 1000*end/time;
		
		currPlayerData.add(death);
		currPlayerData.add(successes);
		currPlayerData.add((int) velocity);
		levelDeaths.add(deaths);
		levelDifficulty.add(difficulty);
//		for (Integer integer : deaths) {
//			System.err.println(integer);
//		}
		
	}
}