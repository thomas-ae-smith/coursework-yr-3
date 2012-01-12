function [my_roll_one,my_roll_two,task_two,posterior] = coursework(T)

% load the rolls.mat file containing roll_one, roll_two, roll_three
load rolls;

even = [1/18,5/18,1/18,5/18,1/18,5/18];
odd = [5/18,1/18,5/18,1/18,5/18,1/18];

global fair Pij start 
fair = [1/6,1/6,1/6,1/6,1/6,1/6];
Pij = [ 0.8, 0.2; 0.1, 0.9 ];
start = [ 1/3, 2/3 ];
% Task one
% Generate my_roll_one and my_roll_two

my_roll_one = zeros(1,T);
my_roll_two = zeros(1,T);

[my_roll_one, state_list] = gensequence(even,T);
my_roll_two = gensequence(odd,T);	

% Task Two
% Use forward algorithm to calculate probabilities
% remember to store them in the array in the order that they are described
% in the coureswork document, and comment on them in your pdf file.

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

task_two = task_two(:)
% Task Three
% for each of the 5 die rolls (your two and the 3 loaded ones)
% and for each model
% work out the most probable hidden state for each timestep and store this
% in the posterior matrix
% list the 5 rolls for model one first, then the 5 rolls for model two

posterior = zeros(10,100);

[ viterbi(my_roll_one, even); state_list];
