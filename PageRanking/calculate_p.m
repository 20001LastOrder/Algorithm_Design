function [p] = calculate_p(filename, output)
	if(exist("output", "var") == 0)
		output = false;
	end

	matrix = load('-ascii', filename);
	matrix = matrix'; %calculate the transpose of the original matrix to make the rows to be destinations
	num_el = size(matrix, 1);

	% amortized rank
	A = ones(num_el) .* 0.15 ./ num_el;
	% ranks for nodes with no out edges
	% calculate ranks for links
	B = zeros(num_el);
	C = zeros(num_el);
	out_degrees = sum(matrix); 
	for i = 1 : length(out_degrees)
		if(out_degrees(i) == 0)
			B(:,i) = ones(num_el, 1) .* 0.85 ./ num_el;
		else
			C(:,i) = ones(num_el, 1) .* 0.85 ./ out_degrees(i);
		end
	end

	% C only for those who actually is linked to
	C = C .* matrix;
	if(output)
		matrix_to_unicode(A);
		matrix_to_unicode(B);
		matrix_to_unicode(C);
	end

	% calculate p such that px = [1;zeros(numel,1)]
	p = [ones(1, num_el); (A + B + C) - eye(num_el)];
end