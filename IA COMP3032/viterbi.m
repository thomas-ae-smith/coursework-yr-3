% Recursively find the most probable hidden states given a sequence,
% loaded probability distribution and the hardcoded model
function [states] = viterbi(seq, loaded)

global fair;
global Pij;
global start;

states = zeros(size(seq));

pair = [ fair(seq(1)); loaded(seq(1))].*start';
pairprime = [0;0];

states(1) = find(pair == max(pair));

for i = 2:size(seq,2)
        pairprime(1) = max([ pair(1)+log(Pij(1,1)), ...
			     pair(2)+log(Pij(2,1)) ]) + log(fair(seq(i)));
        pairprime(2) = max([ pair(1)+log(Pij(1,2)), ...
			     pair(2)+log(Pij(2,2)) ]) + log(loaded(seq(i)));
	pair = pairprime;
	states(i) = find(pair == max(pair));
end
