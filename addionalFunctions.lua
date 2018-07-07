---- This file contains all additional functions that are used in this app and that are shared between files

-- Read specified structured 'file' containing tags following a certain 'template'
-- Returns a double list representing all questions
function readFile(file, template)
	local questions = {}	-- Double list, each single list containing : Question, Answer, Explanation
	local lines = {}		-- String Buffer
	local q					-- A single question
	local templateInUse		-- Is the function to use for the template asked
	
	-- 	<question>
	--		<practice></practice>
	--		<ans></ans>
	--		<explain></explain>
	--	</question>
	local function template3(line)
		if string.find(line, "<question>") then
			q = {}
		elseif string.find(line, "</question>") then
			table.insert(questions, q)
		elseif string.find(line, "<practice>") then
			-- Removing tags from line
			local str = string.gsub(line,"%<practice>","")
			local str2 = string.gsub(str,"%</practice>","")
			table.insert(q, str2)
		elseif string.find(line, "<ans>") then
			local str = string.gsub(line,"%<ans>","")
			local str2 = string.gsub(str,"%</ans>","")
			table.insert(q, str2)
		elseif string.find(line, "<explain>") then
			local str = string.gsub(line,"%<explain>","")
			local str2 = string.gsub(str,"%</explain>","")
			table.insert(q, str2)
		else
			print("Parsing error in file " .. file)
		end
	end

	-- 	<question>
	--		<quest></quest>
	--		<ans></ans>
	--		<explain></explain>
	--		<prop1></prop1>
	--		<prop2></prop2>
	--	</question>
	local function template4(line)
		if string.find(line, "<question>") then
			q = {}
		elseif string.find(line, "</question>") then
			table.insert(questions, q)
		elseif string.find(line, "<quest>") then
			-- Removing tags from line
			local str = string.gsub(line,"%<quest>","")
			local str2 = string.gsub(str,"%</quest>","")
			table.insert(q, str2)
		elseif string.find(line, "<ans>") then
			local str = string.gsub(line,"%<ans>","")
			local str2 = string.gsub(str,"%</ans>","")
			table.insert(q, str2)
		elseif string.find(line, "<explain>") then
			local str = string.gsub(line,"%<explain>","")
			local str2 = string.gsub(str,"%</explain>","")
			table.insert(q, str2)
		elseif string.find(line, "<prop1>") then
			local str = string.gsub(line,"%<prop1>","")
			local str2 = string.gsub(str,"%</prop1>","")
			table.insert(q, str2)
		elseif string.find(line, "<prop2>") then
			local str = string.gsub(line,"%<prop2>","")
			local str2 = string.gsub(str,"%</prop2>","")
			table.insert(q, str2)
		else
			print("Parsing error in file " .. file)
		end
	end
	
	-- We choose the template here
	if template == 3 then
		templateInUse = template3
	elseif template == 4 then
		templateInUse = template4
	else
		print("Error: this template (" .. template .. ") doesn't exist")
	end
	
	-- We read the file
	for line in io.lines(file) do
		templateInUse(line)
	end
	return questions
end