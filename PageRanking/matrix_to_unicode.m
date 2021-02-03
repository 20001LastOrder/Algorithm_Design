function o = matrix_to_unicode(A)
	printf('\\matrix(');
	for i = 1:(size(A,1)-1)
		for j = 1:(size(A,2)-1)
			printf('%7.6f&',A(i,j));
		end
		printf('%7.6f@',A(i,size(A,2)));
	end
	for j = 1:(size(A,2)-1)
		printf('%7.6f&',A(size(A,1),j));
	end
	printf('%7.6f)\n',A(size(A,1),size(A,2)));
end