% Generate the sequences of die rolls using input loaded
% distribution and hardcoded state change probabilities
function [roll_list, state_list] = gensequence(loaded,T)

% Setup the probabilities from S1 and S2
probabilities = [[1/6,1/6,1/6,1/6,1/6,1/6]; loaded];
values = [1, 2, 3, 4, 5, 6];

roll_list = zeros(1,T);
state_list = zeros(1,T);

global Pij;
global start;

% Set initial state from prior probabilities
state_list(1) = discrete_rnd(1,[1,2], start);
roll_list(1) = discrete_rnd(1, values, probabilities(state_list(1),:));

for i = 2:T
	state_list(i) = discrete_rnd(1, [1,2], Pij(state_list(i-1),:));
	roll_list(i) = discrete_rnd(1, values, probabilities(state_list(i),:));
end
