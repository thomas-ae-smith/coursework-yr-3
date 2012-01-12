#include <GL/glfw.h>
#include <stdlib.h>
int main( void ) {
    int running = GL_TRUE;
    int k = 0;
// Initialize GLFW
    if( !glfwInit() ) {
        exit( EXIT_FAILURE );
    }
 // Open an OpenGL window
    if( !glfwOpenWindow( 300,300, 0,0,0,0,0,0, GLFW_WINDOW ) ) { 
        glfwTerminate();
        exit( EXIT_FAILURE );
        }
 // Main loop
    while( running ) {
 // OpenGL rendering goes here...
        if(k <= 70)
            glClearColor(1., 1., 0., 1.);
        if(k>70)
            glClearColor(0., 1., 1., 1.);
        glClear( GL_COLOR_BUFFER_BIT );
 // Swap front and back rendering buffers
        glfwSwapBuffers();
        k=k+1;
        if(k>140)
            k = 0;
 // Check if ESC key was pressed or window was closed
        running = !glfwGetKey( GLFW_KEY_ESC ) &&
                  glfwGetWindowParam( GLFW_OPENED );
        }
 // Close window and terminate GLFW
     glfwTerminate();
 // Exit program
     exit( EXIT_SUCCESS );
 }
