% Recursively find the probability of a given sequence, given a
% probability distribution and the hardcoded model
function [probability] = forward(seq, loaded)

global fair;
global Pij;
global start;

alpha = [ fair(seq(1)); loaded(seq(1))].*start';
alphaprime = [0;0];

for i = 2:size(seq,2)
	alphaprime(1) = (alpha(1)*Pij(1,1) + alpha(2)*Pij(2,1))*fair(seq(i));
	alphaprime(2) = (alpha(1)*Pij(1,2) + alpha(2)*Pij(2,2))*loaded(seq(i)); 
	alpha = alphaprime;
end
probability = sum(alpha);
