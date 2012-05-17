from numpy import *
from scipy.optimize import fmin_cg
from PIL import Image as im


width = 16
input_nodes = width*width
hidden_nodes = 16
output_nodes = 10
reg_lambda = 1
epsilon = 0.15;


#sigmoid function of z; for each element if z is an array/matrix
def sigmoid(z):
	return 1.0 / (1.0 + exp(-z))

#returns the gradient of the sigmoid function evaluated at z; for each element if z is an array/matrix
def sigmoidGradient(z):
	return multiply( sigmoid(z), (ones_like(z) - sigmoid(z)) )

#returns J and the gradients of theta1 and theta2
def nnCostFunction(both_theta, X, y):
	#reshape the combined thetas into the original matricies. nasty
	# print "both_theta.shape", both_theta.shape
	theta1 = both_theta[:hidden_nodes*(input_nodes+1)]
	theta1 = theta1.reshape(hidden_nodes, input_nodes + 1)
	# print "theta1.shape", theta1.shape
	theta2 = both_theta[-output_nodes*(hidden_nodes+1):]
	theta2 = theta2.reshape(output_nodes, hidden_nodes + 1)
	# print "theta2.shape", theta2.shape

	m = X.shape[0]
	# print "m ", m
	J = 0
	#have kept a column of ones to the left of X, rather than continually re-adding it
	#similarly, y already exists as a correctly coded matrix

	#calculate prediction:
	hidden_values = sigmoid(X * theta1.T)	#sigmatize the results of the first step
	hidden_values = hstack( (ones_like(hidden_values[:,0]), hidden_values) ) #add bias values, ones_like the first column
	prediction = sigmoid(hidden_values*theta2.T)

	#calculate cost via errors
	ans = log(ones_like(prediction) - prediction)
	ans = multiply( (ones_like(y)-y) , ans )
	ans = - multiply( y, log(prediction) ) - ans 
	J = sum( ans ) / m

	# print "unreg", J

	#add regularisation of Theta1
	ans = theta1[:, 1:]
	ans = sum(multiply( ans, ans ))
	ans = (reg_lambda*ans)/(2*m)
	J = J + ans

	#add regularisation of Theta2
	ans = theta2[:, 1:]
	ans = sum(multiply( ans, ans ))
	ans = (reg_lambda*ans)/(2*m)
	J = J + ans

	print "J", J

	return J

#returns J and the gradients of theta1 and theta2
def nnGradFunction(both_theta, X, y):
	#reshape the combined thetas into the original matricies. nasty
	# print "both_theta.shape", both_theta.shape
	# print "both_theta.type", type(both_theta)
	theta1 = both_theta[:hidden_nodes*(input_nodes+1)]
	theta1 = theta1.reshape(hidden_nodes, input_nodes + 1)
	# print "theta1[0]", theta1[0,0]
	# print "theta1.shape", theta1.shape
	theta2 = both_theta[-output_nodes*(hidden_nodes+1):]
	theta2 = theta2.reshape(output_nodes, hidden_nodes + 1)
	# print "theta2.shape", theta2.shape

	m = X.shape[0]
	# print "m ", m
	J = 0
	theta1_grad = mat(zeros_like( theta1 ))
	theta2_grad = mat(zeros_like( theta2 ))

	#have kept a column of ones to the left of X, rather than continually readding it
	#similarly, y already exists as a correctly coded matrix

	for t in range(0, X.shape[0]):
		input_activation = X[t,:].T
		# print "theta1.shape", theta1.shape
		# print "input_activation.shape", input_activation.shape
		hidden_sum = theta1*input_activation
		# print "hidden_sum.shape", hidden_sum.shape
		hidden_activation = vstack( (1, sigmoid(hidden_sum)) )
		# print "hidden_activation.shape", hidden_activation.shape
		# print "hidden_activation", hidden_activation
		output_sum = theta2*hidden_activation
		output_activation = sigmoid(output_sum)


		output_error = output_activation - y[t,:].T

		hidden_error = multiply( (theta2.T*output_error), sigmoidGradient(vstack( (1, hidden_sum) )) )

		theta2_grad = theta2_grad + output_error*hidden_activation.T
		theta1_grad = theta1_grad + hidden_error[1:]*input_activation.T

	theta1_grad = theta1_grad / m + reg_lambda * hstack( (zeros((theta1.shape[0],1)), theta1[:,1:]) ) / m
	theta2_grad = theta2_grad / m + reg_lambda * hstack( (zeros((theta2.shape[0],1)), theta2[:,1:]) ) / m

	return_grad = asarray( hstack( ( asarray(theta1_grad).flatten(), asarray(theta2_grad).flatten() ) ).T[:] )
	# print "return_grad.shape", return_grad.shape
	# print "return_grad.shape", asarray(theta1_grad).flatten().shape
	# print "return_grad.type", type(return_grad)
	return return_grad
	# return sum(theta1_grad) + sum(theta2_grad)

def confusionMatrix(both_theta, X, y):
	#reshape the combined thetas into the original matricies. nasty
	theta1 = both_theta[:hidden_nodes*(input_nodes+1)]
	theta1 = theta1.reshape(hidden_nodes, input_nodes + 1)
	theta2 = both_theta[-output_nodes*(hidden_nodes+1):]
	theta2 = theta2.reshape(output_nodes, hidden_nodes + 1)

	cMatrix = zeros( (output_nodes, output_nodes) )

	true_results = 0
	for t in range(0, X.shape[0]):
		input_activation = X[t,:].T
		hidden_activation = vstack( (1, sigmoid(theta1*input_activation)) )
		output_activation = sigmoid(theta2*hidden_activation)

		prediction = output_activation.argmax()
		actual = y[t,:].argmax()
		if prediction == actual:
			true_results += 1
		cMatrix[prediction, actual] += 1

	mcc = 0

	for i in range(0, output_nodes):
		tp = cMatrix[i,i]
		# print "true_positives", tp
		fp = sum(cMatrix[i,:]) - tp
		# print "false_positives", fp
		fn = sum(cMatrix[:,i]) - tp
		# print "false_negatives", fn
		tn = X.shape[0] - tp - fp - fn
		# print "true_negatives", tn
		ci = (tp*tn-fp*fn)/sqrt((tp+fp)*(tp+fn)*(tn+fp)*(tn+fn))
		# print "ci", ci
		mcc += ci

	mcc = mcc / output_nodes
	print "mcc", mcc

	return cMatrix

def displayImgs( data, num ):
	imgWidth = width*num+num-1
	display = im.new("L", (imgWidth, imgWidth), 128)
	for i in range(data.shape[0]):
		seg = 255 - data[i,:].reshape(width, width)*256
		display.paste( im.fromarray(seg), ( (i%num)*(width+1) , (i/num)*(width+1)))
	display.show()
	display.save('image' + str(num) + '.png')


print "hello World."

# Open a file for reading
in_file = open('semeion.data', 'r')

# Get all the lines
lines = in_file.readlines()

# Loop through all the lines 
data = []
labels = []
for line in lines:
	nums = line.split()  # split the line (based on whitespaces)
	data.extend( [map( float, nums[:input_nodes] )] )
	labels.extend( [map( int, nums[-output_nodes:] )] )

# Ouput the number of lines
print 'Number of lines', len(data)
print 'Number of labels', len(labels)
input_data = mat(data)
input_data = hstack( (ones(input_data[:, 0].shape), input_data) )	#add a column of ones for the bias nodes
output_data = mat(labels)
print input_data.shape

indexes = []
for i in range(100):
	indexes.extend([random.randint(0, input_data.shape[0])])
chosen = input_data[indexes,1:]
print "chosen.shape", chosen.shape

# displayImgs(chosen, 10)
# img = input_data[0,1:].reshape(width, width)*256
# print img
# display = im.fromarray(img)
# display.show()

theta1 = mat(random.rand( hidden_nodes, input_nodes + 1) * epsilon)
theta2 = mat(random.rand( output_nodes, hidden_nodes + 1) * epsilon)
print "theta1.shape", theta1.shape
# print theta2
print "initial cost:"
J = nnCostFunction( hstack( (theta1.ravel(), theta2.ravel()) ).T, input_data, output_data)
print "optimizing"
xopt = fmin_cg( nnCostFunction, hstack( (theta1.ravel(), theta2.ravel()) ).T, nnGradFunction, (input_data, output_data), maxiter=50)

print confusionMatrix(xopt, input_data, output_data)

theta1 = xopt[:hidden_nodes*(input_nodes+1)]
theta1 = theta1.reshape(hidden_nodes, input_nodes + 1)
# theta2 = both_theta[-output_nodes*(hidden_nodes+1):]
# theta2 = theta2.reshape(output_nodes, hidden_nodes + 1)

displayHidden = theta1[:,1:]
average = sum(displayHidden)/displayHidden.size
displayHidden = displayHidden - average + 0.5
minimum = displayHidden.min()
maximum = displayHidden.max()
if 0.5 - minimum < maximum - 0.5:
	displayHidden = displayHidden / maximum
else:
	displayHidden = displayHidden - minimum

# print "minimum", minimum
# print "maximum", maximum
print displayHidden

displayImgs(1-displayHidden, 4)

import sys
print sys.executable


