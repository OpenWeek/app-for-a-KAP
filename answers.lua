function friendlyMessage()
	local frdlyMsg = {"Bien joué !",
					  "Bonne réponse !"}
	local rdm = math.random(1,#frdlyMsg)
	return frdlyMsg[rdm]
end

function countGoodAnswers(table)
	local i = 1
	local count = 0
	for i = 1, #table do
		if table[i] then
			count = count + 1
		end
	end
	return count 
end