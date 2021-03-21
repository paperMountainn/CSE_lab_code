#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>

// chmod 744 logaccess
// only ROOT can read, write, execute 
// no SUID bit set

int main(int argc, char* argv[]){

    char * message = argv[1];
    FILE * fileHandler;

    // write message into rootlogfile.txt
    char * fileName = "../Root/rootlogfile.txt";

    fileHandler = fopen(fileName, "a+");
    if (fileHandler == NULL){
        printf("Root logfile cannot be opened\n");
        return 1;
    }

    // write into rootlogfile.txt with fprintf
    // you can write into rootlogfile.txt if you are in root mode or 
    // if you use rootdo
    fprintf(fileHandler, "\nPID %d is writing -- ", getpid());
    fprintf(fileHandler, "%s", message);
    fprintf(fileHandler, "\n");
    fclose(fileHandler);
    printf("Root log write success\n");

    return 0;

}