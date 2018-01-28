import java.io.*;
import java.util.*;

public class SharedMemTera implements Runnable {

	static Thread[] threads = null;
	//Initializing threads with null
	static ArrayList<File> filesCreated = new ArrayList<File>();
	//Array List to save all the intermediate sorted files created
	
	HashMap<String, String> hashlist = null;

	SharedMemTera (){
	};

		SharedMemTera(HashMap<String, String> inhashlist) {
		hashlist = new HashMap<String, String>();
		Set set = inhashlist.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
		Map.Entry me = (Map.Entry) iterator.next();
		hashlist.put((String) me.getKey(), (String) me.getValue());
		}

	}
	//Parameterized constructor to initialize variables while running thread

	public void run()

	{
		try {

		Map<String, String> map = new TreeMap<String, String>(hashlist);
		//The hashmap will be sorted on the basis of 10 Bytes key value 
		File temporaryFile = File.createTempFile("PartitionSorting", "GensortValues");
		//Creating Temporary file to store the sorted hashmap values
		temporaryFile.deleteOnExit();
		//The temporary file will be deleted once the virtual machine terminates
		BufferedWriter bufferwriter = new BufferedWriter(new FileWriter(temporaryFile));
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
		Map.Entry me = (Map.Entry) iterator.next();
		bufferwriter.write(me.getKey() + "  " + me.getValue());
		bufferwriter.write("\n");
			}
		//The sorted hashmap values will be stored in temporary files
			filesCreated.add(temporaryFile);
		//The temporary files will be stored into array List containing all the files
			hashlist.clear();
		//hashmap will be empty
			bufferwriter.close();
		} catch (Exception e)

		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	public static void  partitionSorting(File file, int numofthread) throws Exception {

		BufferedReader filereader = new BufferedReader(new FileReader(file));
		threads = new Thread[numofthread];
		//Initialize number of threads to be created 
		long memoryavailable = Runtime.getRuntime().freeMemory();
		//Obtains the amount of free memory available in JVM
		System.out.println("Size of Input File: " + file.length() + " bytes");
		System.out.println("JVM Free Memory Available : " + memoryavailable + " bytes");
		long cblock = memoryavailable / 2;
		//Chunks will be created of half the size of available memory in JVM
		System.out.println("Size of Block Created :     " + cblock + " bytes");
		System.out.println("-------- Sorting Phase Started ------------------\n");
		int m = -1;
		try {
			HashMap<String, String> hashlist = new HashMap<String, String>();
			//Hashmap is splitted such that we generate 10 byte of key for sorting 
			String newline = "";
			int count = 0;
			int j = -1;
				while (newline != null) 
				{
				long curr = 0;
				while ((curr < cblock) && ((newline = filereader.readLine()) != null)) {
				/*The loop will execute till current block size is greater than alloted block size
				  and newline is not null  */
					String key = newline.substring(0, 10);
					String value = newline.substring(13, 98);
					value = value + newline.substring(newline.length() - 1);
					//input record is splitted into key value pair so that sorting can be done on first 10 bytes
					hashlist.put(key, value);
					curr += newline.length();
				}
				count++;
				j++;
				m++;

				SharedMemTera sm = new SharedMemTera();
				sm.multithreadedcreate(hashlist, j);
				//Threads will invoked by using multithreaded create function
				if (j == numofthread - 1) {
					j = -1;
					m = -1;
					sm.multithreadedjoin();
				//Threads will be joined 
				}
				hashlist.clear();
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			for (int i = 0; i <= m; i++) {
				try {
					threads[i].join();
					//All the running threads will be joined  
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				filereader.close();
			}
		}
	
	}

	public void multithreadedcreate(HashMap<String, String> hashlist, int j) {
		SharedMemTera ext = new SharedMemTera(hashlist);
		threads[j] = new Thread(ext);
		threads[j].start();

	}
	//Function is used for thread creation

	public void multithreadedjoin() throws InterruptedException {

		for (Thread thread : threads) {
			thread.join();

		}

	}//Function is used for joining threads after execution

	
	// merge phase to get lowest value from each file and write to single file using Binary file buffer
	public static void mergePartionedFiles(File outFile) throws IOException  {

	      try {
	         BufferedReader[] bufreader = new BufferedReader[filesCreated.size()];
	         //initializing buffer reader
	         PrintWriter wr = new PrintWriter(outFile);
	         //initializing print writer
	         TreeMap<String, Integer> map = new TreeMap<String, Integer>();
	         // creating a tree map for sorting hashmap values
	         		for (int i = 0; i < filesCreated.size(); i++) {
	        	 	bufreader[i] = new BufferedReader(new FileReader(filesCreated.get(i)));
	        	 	String lineread = bufreader[i].readLine();
	        	 		map.put(lineread, Integer.valueOf(i));}
	         		// obtaining the first record of each sorted file and placing them in tree map
	         			while (!map.isEmpty())
	         			{
	        	 		Map.Entry<String, Integer> outelement = map.pollFirstEntry();
	        	 		//obtains the key value mapping associated with the least key in the map
	        	 		int fileid = outelement.getValue().intValue();
	        	 		wr.println(outelement.getKey());
	        	 		// writing in the output file the value obtained in outelement variable
	        	 		String line = bufreader[fileid].readLine();
	        	 		// obtaining next line from the file whose element is written in output file
	        	 		if (line != null)
	        	 		map.put(line, Integer.valueOf(fileid));
	        	 		// keeping the newly obtained line in tree map
	         }
	      }
	      catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	      
	   }

	public static void main(String[] args) {
		try {
			System.out.println("Number of Threads " + args[0]);
			String numoFThreads = args[0];
			int numofthread = Integer.parseInt(numoFThreads);
			if (numofthread>8 || numofthread<0)
			{
				System.out.println("Please enter number of threads between 1 and 8");
				System.exit(0);
				
			}
			long startTime = System.currentTimeMillis();
			//initializing start time
			String inputfile = args[1];

			String outputfile = "output.txt";

			partitionSorting(new File(inputfile), numofthread);
			//calling method partition sorting
			System.out.println("-------- Sorting Phase Completed ------------------\n");
			System.out.println("-------- Merge Phase Started ------------------\n");
			mergePartionedFiles(new File(outputfile));
			//invoking merge partition files
			System.out.println("-------- Merge Phase Completed ------------------\n");

			long endTime = System.currentTimeMillis();
			// initializing end time
			long timeneeded = endTime - startTime;
			//obtaining the time needed to carry out experiment 
			float timetaken = (float) (timeneeded / 1000.0);
			float minutes = timetaken / 60;

		  System.out.println("Time Taken- " + minutes + " Minutes ");
		} catch (Exception e)

		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

}

