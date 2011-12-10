% COMP3032 coursework by taes1g09
% coursework(T) will generate two sequences of dice rolls using separate
% HMMs representing the dice at a dishonest casino.
% It will then load three further sequences from rolls.mat, generated 
% using the same models, and use the forward algorithm to calculate the 
% probability of each sequence coming from each model, and then the viterbi
% algorithm to calculate the likely hidden states in the same circumstances

% File layout:
% 17: coursework(T): primary driver function
% 84: gensequence(loaded,T): generates a sequence of length T, using loaded distribution
% 109: discrete_rnd(count, values, dist): generates a value from values according to dist
% 124: forward(seq, loaded): calculates the probability of seq according to loaded distribution
% 145: viterbi(seq, loaded): generates the most likely hidden states sequence


function [my_roll_one,my_roll_two,task_two,posterior] = coursework(T)

% load the rolls.mat file containing roll_one, roll_two, roll_three
load rolls;

% biased distributions
even = [1/18,5/18,1/18,5/18,1/18,5/18];
odd = [5/18,1/18,5/18,1/18,5/18,1/18];

global fair
fair = [1/6,1/6,1/6,1/6,1/6,1/6];

% properties of the HMM
global Pij start
Pij = [ 0.8, 0.2; 0.1, 0.9 ];
start = [ 1/3, 2/3 ];

% Task one
% Generate my_roll_one and my_roll_two

my_roll_one = zeros(1,T);
my_roll_two = zeros(1,T);

my_roll_one = gensequence(even,T);
my_roll_two = gensequence(odd,T);

% Task Two
% Use the forward algorithm to calculate probabilities

task_two = zeros(1,10);

base = 0;
loaded = even;
for i = 1:2
	task_two(base + 1) = forward(my_roll_one,loaded);
        task_two(base + 2) = forward(my_roll_two,loaded);
        task_two(base + 3) = forward(roll_one',loaded);
        task_two(base + 4) = forward(roll_two',loaded);
        task_two(base + 5) = forward(roll_three',loaded);
	loaded = odd;
	base = 5;
end

% Task Three
% for each of the 5 die rolls and for each model
% work out the most probable hidden state for each timestep 
% and store this in the posterior matrix

posterior = zeros(10,100);

base = 0;
loaded = even;
for i = 1:2
	% pad the generated rolls if T < 100
	posterior(base + 1,:) = padarray(viterbi(my_roll_one,loaded),[0, 100-T], 0, 'post');
        posterior(base + 2,:) = padarray(viterbi(my_roll_two,loaded),[0, 100-T], 0, 'post');
        posterior(base + 3,:) = viterbi(roll_one',loaded);
        posterior(base + 4,:) = viterbi(roll_two',loaded);
        posterior(base + 5,:) = viterbi(roll_three',loaded);
	loaded = odd;
	base = 5;
end

%------------------------------------------------------------------------------

% Generate the sequences of die rolls using input loaded
% distribution and hardcoded state change probabilities
function [roll_list, state_list] = gensequence(loaded,T)

global Pij start fair

% Setup the probabilities from S1 and S2
probabilities = [fair; loaded];

roll_list = zeros(1,T);
state_list = zeros(1,T);

% Set initial state from prior probabilities
state_list(1) = discrete_rnd(1,[1,2], start);
roll_list(1) = discrete_rnd(1, [1:6], probabilities(state_list(1),:));

% Select state, and generate die roll
for i = 2:T
	state_list(i) = discrete_rnd(1, [1,2], Pij(state_list(i-1),:));
	roll_list(i) = discrete_rnd(1, [1:6], probabilities(state_list(i),:));
end

%------------------------------------------------------------------------------

% I originally did this coursework in Octave, which has a useful discrete_rnd function
% This is a functionally similar facade, but only for the specific cases I used
% Notably, will only ever return one value, regardless of the first parameter
function [num] = discrete_rnd(count, values, dist)

rnd = rand();
for i = 1:size(values,2)
	rnd = rnd - dist(i);
	if rnd < 0
		num = values(i);
		return;
	end
end

%------------------------------------------------------------------------------

% Recursively find the probability of a given sequence, given a
% probability distribution and the hardcoded model
function [probability] = forward(seq, loaded)

global Pij start fair

% set initial probability based on first emmission and equilibrium
alpha = [ fair(seq(1)); loaded(seq(1))].*start';
alphaprime = [0;0];

% march across time calculating successive probabilities
for i = 2:size(seq,2)
	alphaprime(1) = (alpha(1)*Pij(1,1) + alpha(2)*Pij(2,1))*fair(seq(i));
	alphaprime(2) = (alpha(1)*Pij(1,2) + alpha(2)*Pij(2,2))*loaded(seq(i)); 
	alpha = alphaprime;
end
% sum final probabilities
probability = sum(alpha);

%------------------------------------------------------------------------------

% Recursively find the most probable hidden states given a sequence,
% loaded probability distribution and the hardcoded model
function [states] = viterbi(seq, loaded)

global Pij start fair

% initialise return variable
states = zeros(size(seq));

% set initial probability based on first emmission and equilibrium
pair = [ fair(seq(1)); loaded(seq(1))].*start';
pairprime = [0;0];

% find most likely state at t=1
states(1) = find(pair == max(pair));

% march through the emmissions, storing probable states
for i = 2:size(seq,2)
        pairprime(1) = max([ pair(1)+log(Pij(1,1)), ...
			     pair(2)+log(Pij(2,1)) ]) + log(fair(seq(i)));
        pairprime(2) = max([ pair(1)+log(Pij(1,2)), ...
			     pair(2)+log(Pij(2,2)) ]) + log(loaded(seq(i)));
	pair = pairprime;
	states(i) = find(pair == max(pair));
end
