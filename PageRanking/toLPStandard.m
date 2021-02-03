function [A, b] = toLPStandard(P, c, output)
	A = [P; -P];
	b = [c; -c];
	if(output)
		for i = 1:(size(A,1))
			for j = 1:(size(A,2)-1)
				printf('%7.6fX_%d+',A(i,j), j);
			end
			printf('%7.6fX_%d <=',A(i,size(A,2)), size(A,2));
			printf('%7.6f \n',b(i));
		end
	end
end
