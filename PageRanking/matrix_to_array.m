function o = matrix_to_array(A)
	for i = 1:size(A,1)
		printf('[');
		for j = 1:(size(A,2)-1)
			printf('%7.6f,',A(i,j));
		end
		printf('%7.6f],\n',A(i,size(A,2)));
	end
end