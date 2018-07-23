---- This file contains all additional functions that are used in this app and that are shared between files

-- Read specified structured 'file' containing tags following a certain 'template'
-- Returns a double list representing all questions
function readFile(file, template)
	local questions = {}	-- Double list, each list containing elements  depending of the used template
	local lines = {}		-- String Buffer
	local q					-- A single question
	local templateInUse		-- Is the function to use for the template asked
	local IST, notIST		-- Used in template1
	-- 	<ist>
	-- 		<sickness>
	--			<name></name>
	--			<descr></descr>
	--		</sickness>
	--	</ist>
	-- 	<other>
	--		<nameOther></nameOther>
	--	</other>
	-- questions = {<IST>{<Sickness>{<name>,<descr>}}, <notIST>{}}
	local function template1(line)
		if string.find(line, "<ist>") then
			IST = {}
		elseif string.find(line, "</ist>") then
			table.insert(questions, IST)
		elseif string.find(line, "<sickness>") then
			q = {}
		elseif string.find(line, "</sickness>") then
			table.insert(IST, q)
		elseif string.find(line, "<name>") then
			-- Removing tags from line
			local str = string.gsub(line,"%<name>","")
			local str2 = string.gsub(str,"%</name>","")
			table.insert(q, str2)
		elseif string.find(line, "<descr>") then
			local str = string.gsub(line,"%<descr>","")
			local str2 = string.gsub(str,"%</descr>","")
			table.insert(q, str2)
		elseif string.find(line, "<other>") then
			notIST = {}
		elseif string.find(line, "</other>") then
			table.insert(questions, notIST)
		elseif string.find(line, "<nameOther>") then
			local str = string.gsub(line,"%<nameOther>","")
			local str2 = string.gsub(str,"%</nameOther>","")
			table.insert(notIST, str2)
		else
			print("Parsing error in file " .. file)
		end
	end

	-- 	<question>
	--		<symptom></symptom>
	--		<sickness></sickness>
	--	</question>
	local function template2(line)
		if string.find(line, "<question>") then
			q = {}
		elseif string.find(line, "</question>") then
			table.insert(questions, q)
		elseif string.find(line, "<symptom>") then
			-- Removing tags from line
			local str = string.gsub(line,"%<symptom>","")
			local str2 = string.gsub(str,"%</symptom>","")
			table.insert(q, str2)
		elseif string.find(line, "<sickness>") then
			local str = string.gsub(line,"%<sickness>","")
			local str2 = string.gsub(str,"%</sickness>","")
			table.insert(q, str2)
		else
			print("Parsing error in file " .. file)
		end
	end
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
	if template == 1 then
		templateInUse = template1
	elseif template == 2 then
		templateInUse = template2
	elseif template == 3 then
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

function split(inputstr, sep)
        if sep == nil then
                sep = "%s"
        end
        local t={} ; i=1
        for str in string.gmatch(inputstr, "([^"..sep.."]+)") do
                t[i] = str
                i = i + 1
        end
        return t
end