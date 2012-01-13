#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#include "Utils.h"



/** read file into allocated char pointer buffer **/
const char* filetobuf(const char *file) {
	FILE *fptr;
	long length;
	char *buf;
	fptr = fopen(file, "rb"); // read binary
	if (!fptr) {
		fprintf(stderr, "failed to open %s\n", file);
		return NULL;
	}
	fseek(fptr, 0, SEEK_END); // Seek to end of file
	length = ftell(fptr);
	buf = (char*)malloc(length + 1); // file contents + null terminator

	fseek(fptr, 0, SEEK_SET); // Seek to beginning of file
	fread(buf, length, 1, fptr); // Read file into buffer
	fclose(fptr);
	buf[length] = 0; // Null terminator
	const char* result = buf;
	return result;
}
