//Main game loop. Create GameFrame and HelpFrame. Store pointer to activeframe

#include <stdlib.h>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/glfw.h>
#include "shaderManager.h"
#include "GameFrame.h"
#include "Utils.h"

void init();
void drawLoop();
void shutdown(int exit_status);
int main(int argc, char const *argv[]);

void init() {
	if( !glfwInit() ) {
		shutdown( EXIT_FAILURE );
	}
	if( !glfwOpenWindow( 600, 600, 0,0,0,0,0,0, GLFW_WINDOW ) ) {
		glfwTerminate();
		shutdown( EXIT_FAILURE );
	}
	glfwSetWindowTitle("taes1g09 - COMP3004 C/W 3");
	glewInit();
	glEnable(GL_DEPTH_TEST);
	//glClearColor(0.0, 0.0, 0.0, 1.0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);//one of these is redundant TODO
}

void drawLoop() {
	gameFrame *game = new gameFrame(NULL);
	int quit = false;
	double tick, lasttick;		//TODO I don't like this nomenclature
	while (!quit) {
		tick = glfwGetTime();
		game->render();
		glfwSwapBuffers();
		quit = glfwGetKey( GLFW_KEY_ESC ) || glfwGetKey('Q') || glfwGetKey('q') || !glfwGetWindowParam( GLFW_OPENED );
		lasttick = tick;
	}
}

void shutdown(int exit_status) {
	if (shaderManager::get()) delete shaderManager::get();
	glfwTerminate();
	exit(exit_status);
}

int main(int argc, char const *argv[]) {
	init();
	drawLoop();
	shutdown( EXIT_SUCCESS );
	return 0;
}