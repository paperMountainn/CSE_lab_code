all : processManagement_lab.c ./checkerCode/check.c
	gcc processManagement_lab.c -o out
	gcc ./checkerCode/check.c -o ./checkerCode/checkout
	

test : ./checkerCode/checkout
	./checkerCode/checkout
	