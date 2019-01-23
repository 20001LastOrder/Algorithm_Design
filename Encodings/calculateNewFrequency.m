function[newF] = calculateNewFrequency(f, freqToAdd)
	newF = [f*(1-freqToAdd); freqToAdd];
end