//Main game loop. Create GameFrame and HelpFrame. Store pointer to activeframe

#include <stdlib.h>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/glfw.h>
#include "shaderManager.h"
#include "GameFrame.h"
#include "Utils.h"

// Forward declarations
void init();
void drawLoop();
void shutdown(int exit_status);
int main(int argc, char const *argv[]);

// Set everything up
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
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

void drawLoop() {
	gameFrame *game = new gameFrame(NULL);
	int quit = false;
	double tick, lasttick;
	printf("%s\n", (const char*) glGetString(GL_VERSION));
	while (!quit) {
		tick = glfwGetTime();
		game->update(tick-lasttick);
		game->render();
		glfwSwapBuffers();
		quit = glfwGetKey( GLFW_KEY_ESC ) || glfwGetKey('Q') || !glfwGetWindowParam( GLFW_OPENED );
		// printf("%f FPS\n", 1/(tick-lasttick));
		lasttick = tick;
	}
	delete game;
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