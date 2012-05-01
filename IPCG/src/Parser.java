import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	ArrayList<ArrayList<Integer>> playerData;
	ArrayList<ArrayList<ArrayList<Integer>>> globalDeaths;
	ArrayList<ArrayList<ArrayList<Integer>>> globalDesDiff;
	ArrayList<ArrayList<ArrayList<Integer>>> globalGenDiff;
	
	ArrayList<Integer> currPlayerData;
	ArrayList<ArrayList<Integer>> levelDeaths;
	ArrayList<ArrayList<Integer>> levelDesDiff;
	ArrayList<ArrayList<Integer>> levelGenDiff;
	
	String dirName = new String("/Users/Tirhakah/Desktop/data");
	
	public static void main(String[] args) {
		new Parser();
	}
	
	Parser() {
		
		playerData = new ArrayList<ArrayList<Integer>>();
		globalDeaths = new ArrayList<ArrayList<ArrayList<Integer>>>();
		globalDesDiff = new ArrayList<ArrayList<ArrayList<Integer>>>();
		globalGenDiff = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		File dir = new File(dirName);

		String[] children = dir.list();
		if (children == null) {
		    // Either dir does not exist or is not a directory
			System.err.println("Invalid directory: " + dir.getAbsolutePath());
			return;
		}

		//create a filter to remove all files that start with 'o' or don't end '.txt'
		//not fully specific, but good enough for our purposes
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return !name.startsWith("o") && name.endsWith(".txt");
		    }
		};


		File[] files = dir.listFiles(filter);
		
		//parse each file
		for (File file : files) {
			try {
				System.out.println("Parsing " + file.getName());
				parseFile(file);
			} catch (Exception e) {
				System.err.println("Parsing " + file.getName() + " failed.");
				e.printStackTrace();
			}
		}
		
		try {
			output();
		} catch (IOException e) {
			System.err.println("Writing output file failed.");
			e.printStackTrace();
		}
		
	}
	
	public void output() throws IOException {
		
		//create a new output file in the same directory
		FileWriter fileWrite = new FileWriter(new File(dirName + "/output.txt"));

		//print the player data
		for (ArrayList<Integer> list : playerData) {
			for (Integer num : list) {
				
				fileWrite.write(num+" ");
			}
			fileWrite.write("\n");
		}
		fileWrite.write("\n\n");
		
		//print the deaths by level
		for (int i = 0; i < 4; i++) {
			for (ArrayList<ArrayList<Integer>> levelDeaths : globalDeaths) {
				for (Integer num : levelDeaths.get(i)) {
					fileWrite.write(num + " ");
				}
				fileWrite.write("\n");
			}
			fileWrite.write("\n");
		}
		fileWrite.write("\n");
		
		//print the desired difficulty by level
		for (int i = 0; i < 4; i++) {
			for (ArrayList<ArrayList<Integer>> levelDesDiff : globalDesDiff) {
				for (Integer num : levelDesDiff.get(i)) {
					fileWrite.write(num + " ");
				}
				fileWrite.write("\n");
			}
			fileWrite.write("\n");
		}
		fileWrite.write("\n");
		
		//print the generated difficulty by level
		for (int i = 0; i < 4; i++) {
			for (ArrayList<ArrayList<Integer>> levelGenDiff : globalGenDiff) {
				for (Integer num : levelGenDiff.get(i)) {
					fileWrite.write(num + " ");
				}
				fileWrite.write("\n");
			}
			fileWrite.write("\n");
		}
		
		fileWrite.flush();
		fileWrite.close();
		
		
	}
	
	public void parseFile(File file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
				
		levelDeaths = new ArrayList<ArrayList<Integer>>();
		levelDesDiff= new ArrayList<ArrayList<Integer>>();
		levelGenDiff= new ArrayList<ArrayList<Integer>>();
		currPlayerData = new ArrayList<Integer>();
		
		//add this file id to the beginning of the playerdata 
		currPlayerData.add(Integer.parseInt(file.getName().substring(0, file.getName().indexOf('.'))));
		
		ArrayList<String> currLevel = new ArrayList<String>();
		boolean level = false;
		int levelNum = 0;
		int[] ans = new int[6];
		
		String line;

		//for each line
		while ((line = reader.readLine()) != null) {
			switch (line.charAt(0)) {
			case '#':	//ignore comments
				break;	
			case '-':	//ignore breaks, unless they are at the end of a level segment
				if (level) {		//in which case, parse the level and prepare for the next one
					currPlayerData.add(levelNum);
					parseLevel(currLevel, levelNum);
					currLevel.clear();
					level = false;
					levelNum++;
				}
				break;
			case 'q':	//store the answers to questions
				ans[line.charAt(1) - '0' - 1] = line.charAt(4) - '0';
				break;
			case '0':
			case '1':
			case 't':
			case 'd':
			case 'g':
				level = true;		//queue level-specific data to be parsed once fully collected
				currLevel.add(line);
				break;
			default:
				System.err.println("Something unexpected happened: " + line);
			}
		}
		reader.close();
		//copy the question onswers into the end of player data, after level data
		for (int i = 0; i < ans.length; i++) {
			currPlayerData.add(ans[i]);
		}
		//add data from this file to the global lists
		playerData.add(currPlayerData);
		globalDeaths.add(levelDeaths);
		globalDesDiff.add(levelDesDiff);
		globalGenDiff.add(levelGenDiff);
		
	}
	
	public void parseLevel(ArrayList<String> level, int levelNum) {
		int end = 0;
		int time = 0;
		int death = 0;
		int successes = 0;
		double velocity;
		
		ArrayList<Integer> dDiff = new ArrayList<Integer>();
		ArrayList<Integer> gDiff = new ArrayList<Integer>();
		ArrayList<Integer> deaths = new ArrayList<Integer>();
		deaths.add(0);
		
		
		for (String data : level) {
//			System.err.println(data);
			switch (data.charAt(0)) {
			case 'd':
				dDiff.add(Integer.parseInt(data.substring(data.lastIndexOf(' ')+1)));	//record the desired difficulty
				break;
			case '1':
				gDiff.add(Integer.parseInt(data.substring(data.lastIndexOf(' ')+1)));	//record the actual difficulty
				deaths.add(new Integer(death));	//add a new tick with the deaths
				successes++;
				break;
			case '0':
				death++;
				break;
			case 'g':
				end = Integer.parseInt(data.substring(data.lastIndexOf(' ')+1));	//store the location of the end of the level
				break;
			case 't':
				time = Integer.parseInt(data.substring(data.lastIndexOf(' ')+1));	//store the level end time
				break;
			}
		}
		
		velocity = 1000*end/time;
		
		currPlayerData.add(death);
		currPlayerData.add(successes);
		currPlayerData.add((int) velocity);
		levelDeaths.add(deaths);
		levelDesDiff.add(dDiff);
		levelGenDiff.add(gDiff);
		
	}
}