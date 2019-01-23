function [singleE, entropy] = calculateEntropy(A)
	singleE = -A.*log2(A);
	entropy = sum(singleE);
end