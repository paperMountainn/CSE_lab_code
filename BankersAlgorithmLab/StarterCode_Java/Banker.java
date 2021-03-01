import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Banker {
	private int numberOfCustomers;	// the number of customers
	private int numberOfResources;	// the number of resources

	private int[] available; 	// the available amount of each resource
	private int[][] maximum; 	// the maximum demand of each customer
	private int[][] allocation;	// the amount currently allocated
	private int[][] need;		// the remaining needs of each customer

	/**
	 * Constructor for the Banker class.
	 * @param resources          An array of the available count for each resource.
	 * @param numberOfCustomers  The number of customers.
	 */

	 // e.g. resources = 10 5 7 avail, customers = 5 which is n
	public Banker (int[] resources, int numberOfCustomers) {
		// TODO: set the number of resources
		// is this the resource type? Which is m?
		this.numberOfResources = resources.length;

		// TODO: set the number of customers
		this.numberOfCustomers = numberOfCustomers;

		// TODO: set the value of bank resources to available
		this.available = resources;

		// TODO: set the array size for maximum, allocation, and need
		this.maximum = new int[this.numberOfCustomers][this.numberOfResources];
		this.allocation = new int[this.numberOfCustomers][this.numberOfResources];
		this.need = new int[this.numberOfCustomers][this.numberOfResources];

	}

	/**
	 * Sets the maximum number of demand of each resource for a customer.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param maximumDemand  An array of the maximum demanded count for each resource.
	 */
	public void setMaximumDemand(int customerIndex, int[] maximumDemand) {
		// TODO: add customer, update maximum and need
		
		// update max
		int[] demand = maximumDemand;

		// why need to clone?
		this.maximum[customerIndex] = maximumDemand.clone();

		//update need
		this.need[customerIndex] = maximumDemand.clone();
		
	}

	/**
	 * Prints the current state of the bank.
	 */
	public void printState() {
        System.out.println("\nCurrent state:");
        // print available
        System.out.println("Available:");
        System.out.println(Arrays.toString(available));
        System.out.println("");

        // print maximum
        System.out.println("Maximum:");
        for (int[] aMaximum : maximum) {
            System.out.println(Arrays.toString(aMaximum));
        }
        System.out.println("");
        // print allocation
        System.out.println("Allocation:");
        for (int[] anAllocation : allocation) {
            System.out.println(Arrays.toString(anAllocation));
        }
        System.out.println("");
        // print need
        System.out.println("Need:");
        for (int[] aNeed : need) {
            System.out.println(Arrays.toString(aNeed));
        }
        System.out.println("");
	}
	/**
	 * Requests resources for a customer loan.
	 * If the request leave the bank in a safe state, it is carried out.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param request        An array of the requested count for each resource.
	 * @return true if the requested resources can be loaned, else false.
	 */
	public synchronized boolean requestResources(int customerIndex, int[] request) {
		// TODO: print the request
		System.out.println("Customer " + customerIndex + " requesting");
        System.out.println(Arrays.toString(request));

		// TODO: check if request larger than need
		boolean request_larger_than_need = false;

		for (int i = 0; i < numberOfResources; i++){
			if (request[i] <= this.need[customerIndex][i]){
				continue;
			}
			else{
				request_larger_than_need = true;
			}
		}

		
		// TODO: check if request larger than available
		boolean request_larger_than_available = false;

		for (int i = 0; i < numberOfResources; i++){
			if (request[i] <= this.available[i]){
				continue;
			}
			else{
				request_larger_than_available = true;
			}
		}

		if (request_larger_than_available || request_larger_than_need){
			System.out.println("Request rejected!! : ( ");
			return false;

		}		
		
		// TODO: check if the state is safe or not
	
		
		// TODO: if it is safe, allocate the resources to customer customerNumber
		for (int i = 0; i < numberOfResources; i++){

			// avail = avail - req
			this.available[i] = this.available[i] - request[i];

			// alloc = alloc + req
			this.allocation[customerIndex][i] = this.allocation[customerIndex][i] + request[i]; 

			//need = need - req
			this.need[customerIndex][i] = this.need[customerIndex][i] - request[i]; 
			
		}
		return true;
	}

	/**
	 * Releases resources borrowed by a customer. Assume release is valid for simplicity.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param release        An array of the release count for each resource.
	 */
	public synchronized void releaseResources(int customerIndex, int[] release) {
		// TODO: print the release
		System.out.println("Customer " + customerIndex + " releasing");
		System.out.println(Arrays.toString(release));
		
		// TODO: release the resources from customer customerNumber
		

		// opposite of request
		for (int i = 0; i < numberOfResources; i++){

			// avail = avail + rel
			this.available[i] = this.available[i] + release[i];

			// alloc = alloc - rel
			this.allocation[customerIndex][i] = this.allocation[customerIndex][i] - release[i]; 

			// need = need + rel
			this.need[customerIndex][i] = this.need[customerIndex][i] + release[i]; 
			
		}

	}

	/**
	 * Checks if the request will leave the bank in a safe state.
	 * @param customerIndex  The customer's index (0-indexed).
	 * @param request        An array of the requested count for each resource.
	 * @return true if the requested resources will leave the bank in a
	 *         safe state, else false
	 */
	private synchronized boolean checkSafe(int customerIndex, int[] request) {
		// TODO: check if the state is safe
		
		return true;
	}

	/**
	 * Parses and runs the file simulating a series of resource request and releases.
	 * Provided for your convenience.
	 * @param filename  The name of the file.
	 */
	public static void runFile(String filename) {

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(filename));

			String line = null;
			String [] tokens = null;
			int [] resources = null;

			int n, m;
			
			// n is number of processes
			try {
				n = Integer.parseInt(fileReader.readLine().split(",")[1]);
			} catch (Exception e) {
				System.out.println("Error parsing n on line 1.");
				fileReader.close();
				return;
			}
			// m is the number of resource type

			try {
				m = Integer.parseInt(fileReader.readLine().split(",")[1]);
			} catch (Exception e) {
				System.out.println("Error parsing n on line 2.");
				fileReader.close();
				return;
			}

			try {
				// available is 10 5 7
				// 10, 5, 7
				tokens = fileReader.readLine().split(",")[1].split(" ");

				// resources have 3 spaces
				resources = new int[tokens.length];

				// tokens.length = 3
				for (int i = 0; i < tokens.length; i++)
				// resources = [10 ,5, 7]
					resources[i] = Integer.parseInt(tokens[i]);
			} catch (Exception e) {
				System.out.println("Error parsing resources on line 3.");
				fileReader.close();
				return;
			}

			Banker theBank = new Banker(resources, n);

			int lineNumber = 4;
			while ((line = fileReader.readLine()) != null) {

				// split at ","
				tokens = line.split(",");

				// max demand
				if (tokens[0].equals("c")) {
					try {
						// customer index is 0 (for line 4)
						int customerIndex = Integer.parseInt(tokens[1]);

						// tokens[2] is 7 5 3
						// split at " " means that you have 3 res
						tokens = tokens[2].split(" ");

						// resources have length of 3
						resources = new int[tokens.length];
						for (int i = 0; i < tokens.length; i++)
							resources[i] = Integer.parseInt(tokens[i]);

						// max = 7, 5, 3; index is 0
						// update theBank attribute to have max demand of 7 5 3 for customer 0

						// System.out.println("DEBUG");
						// System.out.println(Arrays.toString(resources));
						theBank.setMaximumDemand(customerIndex, resources);

						// System.out.println("DEBUG1");
						// System.out.println(Arrays.toString(resources));


					} catch (Exception e) {
						System.out.println("Error parsing resources on line "+lineNumber+".");
						fileReader.close();
						return;
					}
					
					// request res
				} else if (tokens[0].equals("r")) {
					try {
						int customerIndex = Integer.parseInt(tokens[1]);
						tokens = tokens[2].split(" ");
						resources = new int[tokens.length];
						for (int i = 0; i < tokens.length; i++)
							resources[i] = Integer.parseInt(tokens[i]);

						// request resources
						// for customer 0, req for 010
						theBank.requestResources(customerIndex, resources);

						// System.out.println("DEBUG2");
						// System.out.println(Arrays.toString(theBank.maximum[0]));
					} catch (Exception e) {
						System.out.println("Error parsing resources on line "+lineNumber+".");
						fileReader.close();
						return;
					}

					
					// System.out.println(Arrays.toString(theBank.maximum[0]));
				} else if (tokens[0].equals("f")) {
					try {
						int customerIndex = Integer.parseInt(tokens[1]);
						tokens = tokens[2].split(" ");
						resources = new int[tokens.length];
						for (int i = 0; i < tokens.length; i++)
							resources[i] = Integer.parseInt(tokens[i]);
							// release 1 1 0 from customer 1
						theBank.releaseResources(customerIndex, resources);
					} catch (Exception e) {
						System.out.println("Error parsing resources on line "+lineNumber+".");
						fileReader.close();
						return;
					}
				} else if (tokens[0].equals("p")) {
					// at the end, print out the state
					theBank.printState();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Error opening: "+filename);
		}

	}

	/**
	 * Main function
	 * @param args  The command line arguments
	 */
	public static void main(String [] args) {
		if (args.length > 0) {
			runFile(args[0]);
		}
	}

}