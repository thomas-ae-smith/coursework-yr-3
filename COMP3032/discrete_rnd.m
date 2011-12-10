% I originally did this coursework in Octave, which has a useful discrete_rnd function
% This is a functionally similar facade, but only for the specific cases I used
% Notably, will only ever return one value, regarless of the first parameter
function [num] = discrete_rnd(count, values, dist)

rnd = rand();
for i = 1:size(values,2)
	rnd = rnd - dist(i);
	if rnd < 0
		num = values(i);
		return;
	end
end
