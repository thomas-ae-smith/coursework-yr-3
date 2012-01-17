taes1g09 - COMP3004 C/W 3
readme

--Last minute bug: camera starts in sun. Press P to escape--

Solar system. 	All spheres are generated in the geometry shader from an original octahedron.
				All textures are generated using simplex noise, using a GLSL library found here: 
						https://github.com/ashima/webgl-noise
						Used under the MIT license.

Interaction:
The following controls may be used to interact with the demo.

<ESC>, Q				-		Exit the program.
￼
P 						-		Go to the initial position above the sun, where screenshot.jpg was taken

Y 						-		Go to alternative view point 1.
￼
U 						-		Go to alternative view point 2.
￼
T 						- 		Start the tour, ignoring all key presses except E, Q and <ESC>
￼￼
E 						-		Exit the tour mode otherwise ignored.
￼￼
<LEFT>, <RIGHT> 		- 		Turn the camera

<HOME>, <END>			- 		Incline the camera

<PG UP>, <PG DOWN>		- 		Alter the camera's elevation

<UP>, <DOWN>			-		Accelerate and decelerate (the camera cannot go backwards) 

<SPACE>					- 		Stop all motion