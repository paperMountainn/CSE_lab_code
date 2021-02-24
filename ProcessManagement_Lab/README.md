### main_loop description

```
initialise a boolean variable called assigned

while there are still inputs (fscanf scans for new task), set assigned == false
busy wait and loop through all the processes 
    for each child process, check if child alive && task_status == 0
        assign new task
        increase associated semaphore, so that children can request for the task with sem_wait()
        set assigned to true
        break from for loop
    if child is assigned task, break busy wait loop
    for each child process,
        if child not alive
            fork() to create a new child process
            if pid > 0, adult process assign new jobs, sem_post() the job so that the child can request for the task
            if pid == 0, child process calls job_dispatch(i) to do the job
        set assigned to true
        break from for loop