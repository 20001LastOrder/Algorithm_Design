function bps = bitsPerSymbol(name)
	[n, f, e] = textread (name, "%s %f %s");
	bps = 0;
	for i = 1 : length(e)
		bps = bps + f(i)*length(e{i});
	end

