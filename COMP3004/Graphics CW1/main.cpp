#include <stdlib.h>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/glfw.h>
#include "utils.h"
#include "stylesBase.h"
#include "sphereWireframe.h"
#include "coneWireframe.h"
#include "sphereNormals.h"
#include "sphereShaded.h"
#include "sceneWireframe.h"



DrawStyles *drawStyle;
char lastChar;

void startup(char type) {
    if (type != lastChar) {
//        printf("%c detected\n", type);
        lastChar = type;
        delete drawStyle;
        switch (type) {
            case ('A'):
                drawStyle = new DrawSphereWireframe(true);
                break;
            case ('B'):
                drawStyle = new DrawConeWireframe();
                break;
            case ('C'):
                drawStyle = new DrawSphereNormals();
                break;
            case ('D'):
                drawStyle = new DrawSphereShaded();
                break;
            case ('E'):
                drawStyle = new DrawSceneWireframe();
                break;
            default:
                break;
        }
//        printf("Created new drawing object\n");
    }
}

void drawLoop() {
    int quit = false;
    double tick, lasttick;
//    printf("Starting main loop\n");
    while (!quit) {
        tick = glfwGetTime();


        if ( glfwGetKey('A') || glfwGetKey('a') )
            startup('A');
        else if ( glfwGetKey('B') || glfwGetKey('b') )
            startup('B');
        else if ( glfwGetKey('C') || glfwGetKey('c') )
            startup('C');
        else if ( glfwGetKey('D') || glfwGetKey('d') )
            startup('D');
        else if ( glfwGetKey('E') || glfwGetKey('e') )
            startup('E');


        drawStyle->render(tick - lasttick);
        glfwSwapBuffers();
        quit = glfwGetKey( GLFW_KEY_ESC ) || glfwGetKey('Q') || glfwGetKey('q') || !glfwGetWindowParam( GLFW_OPENED );
        lasttick = tick;
    }
}

void shutdown(int exit_status) {
    delete drawStyle;
    glfwTerminate();
    exit(exit_status);
}

void init() {
    if( !glfwInit() ) {
        shutdown( EXIT_FAILURE );
    }
    if( !glfwOpenWindow( 600, 600, 0,0,0,0,0,0, GLFW_WINDOW ) ) {
        glfwTerminate();
        shutdown( EXIT_FAILURE );
    }
    glfwSetWindowTitle("3004 C/W 1, taes1g09");
    glewInit();
    glEnable(GL_DEPTH_TEST);
    //glClearColor(0.0, 0.0, 0.0, 1.0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);//one of these is redundant
}

int main(int argc, char *argv[]) {
    init();
    startup('A');
    drawLoop();
    shutdown( EXIT_SUCCESS );
    return 0;
}
