clear; close all; clc

f = "Frequency.txt";
[a, b] = textread (f, "%s %f");
sum(b)
%add blank frequency
b = calculateNewFrequency(b, 0.05);

%calculate entropy
[singleEntropy, entropy] = calculateEntropy(b);
sum(b)

%add blank to alphabet
a = [a; 'blank'];

%output to file
file1 = 'newFrequency.txt';
file2 = 'entropy.txt';
freqOut = fopen(file1, "w");
entropyOut = fopen(file2, "w");
for i = 1 : size(a)
	fprintf(freqOut, "%s %f\n",a{i},b(i));
	fprintf(entropyOut, "%s %f\n",a{i},singleEntropy(i));
end
fprintf(entropyOut, "Total Entropy = %f", entropy);
fclose('all')

