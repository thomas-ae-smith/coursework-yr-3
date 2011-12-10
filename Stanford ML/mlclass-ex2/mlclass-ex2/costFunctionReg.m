function [J, grad] = costFunctionReg(theta, X, y, lambda)
%COSTFUNCTIONREG Compute cost and gradient for logistic regression with regularization
%   J = COSTFUNCTIONREG(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta

pred=sigmoid(theta'*X')';
log(ones(size(pred))-pred);
(ones(size(y))-y).*ans;
-(y.*log(pred))-ans;
ones(size(pred))'*ans;
J=ans/m;

ones(size(theta));
ans(1,1)=0;
lambda*(ans'*(theta.*theta))/(2*m);
J=J+ans;

pred-y;
repmat(ans, 1, size(X, 2));
ans.*X;
ones(size(pred))'*ans;
grad=ans./m;

(lambda*theta)'./m;
ans(1,1)=0;
grad=grad+ans;


% =============================================================

end
