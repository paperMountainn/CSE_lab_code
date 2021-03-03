# Lab 2 Report

## Analysis of Time Complexity of Banker's Algorithm

Banker's Algo has a time complexity of $O(MN^2)$

- M = number of resource types
- N = number of processes

The Banker's algorithm consists of the Safety Algorithm(SA) and the Resource Allocation(RA) Algorithm.

The crux lies in the SA algorithm, this portion of code

- while loop will execute the whole chunk of code at worst N times
    - the first for loop needs to be done N times
        - the second for loop needs to be done M times —O(MN^2)
- if need_lesser_than_work, inside there is another for loop that needs to be done M times — O(M)

So the worst time complexity is O(MN^2)

```java
while (indexExist){ // do N times
			indexExist = false;
			// i is index of Customer
			for (int i = 0; i < numberOfCustomers; i++){ // do N times
				if (finished[i] == false){
					boolean need_lesser_than_work = true;
					for (int j = 0; j < numberOfResources; j++){ // do M times
						if (need_copy[i][j] > avail_copy[j]){
							need_lesser_than_work = false;
						}
					}
					if (need_lesser_than_work){
						indexExist = true;
						// System.out.println("need_lesser_than_work");
						// System.out.println(need_lesser_than_work);
						// work = work + alloc 
						// k is index for resources
						for (int k = 0; k < numberOfResources; k ++){ // do M times
							avail_copy[k] = avail_copy[k] + alloc_copy[i][k]; 
						}
						finished[i] = true;
					}
					
				}
			}

		}
```