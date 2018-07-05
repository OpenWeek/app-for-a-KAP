function level2()

	-- Background

	local background = Bitmap.new(FondNiveau)
	stage:addChild(background)
	
	local title = TextField.new(nil, "Niveau 2")
	title:setPosition(30, 30)
	background:addChild(title)
	
	-- Symptoms
	
	local symp = {}
	local temp = {}
	for line in io.lines("QuestionsLvl2.txt") do
		table.insert(temp, line)
		if #temp == 2 then
			table.insert(symp, temp)
			temp = {}
		end
	end
	
	
	local current_symp = 1
	
	-- Explication text
	
	local exp = TextField.new(nil, "Quelle IST cause ces symptômes ? (" .. current_symp .. "/" .. #symp .. ")")
	exp:setPosition(10, 60)
	background:addChild(exp)
	
	-- Print Current Symp
	
	local symp_text = TextWrap.new(symp[current_symp][1], 260)
	symp_text:setPosition(20, 30)
	exp:addChild(symp_text)
	
	-- Print button left
	
	local leftArrowUp = Bitmap.new(LeftArrow)
	local leftArrowDown = Bitmap.new(LeftArrow)
	local leftArrow = Button.new(leftArrowUp, leftArrowDown, nil)
	leftArrow:setPosition(0, 40)
	leftArrow:addEventListener("click", 
		function()
			if current_symp > 1 then
				current_symp = current_symp - 1
				symp_text:setText(symp[current_symp][1])
				exp:setText("Quelle IST cause ces symptômes ? (" .. current_symp .. "/" .. #symp .. ")")
			end
		end
	)
	exp:addChild(leftArrow)
	
	-- Print button right
	
	local rightArrowUp = Bitmap.new(RightArrow)
	local rightArrowDown = Bitmap.new(RightArrow)
	local rightArrow = Button.new(rightArrowUp, rightArrowDown, nil)
	rightArrow:setPosition(290, 40)
	rightArrow:addEventListener("click", 
		function()
			if current_symp < #symp then
				current_symp = current_symp + 1
				symp_text:setText(symp[current_symp][1])
				exp:setText("Quelle IST cause ces symptômes ? (" .. current_symp .. "/" .. #symp .. ")")
			end
		end
	)
	exp:addChild(rightArrow)
	
	-- Print Result
	
	local result = {}
	
	function printResult()
		local correctVals = 0
		local i = 1
		for k, v in pairs(result) do
			if v == i then
				correctVals = correctVals + 1
				i = i + 1
			end
		end
		background:removeChild(exp)
		local res1 = TextField.new(nil, "Plus de questions !")
		res1:setPosition(10, 60)
		background:addChild(res1)
		local res2 = TextField.new(nil, "Vous avez répondu correctement à " .. correctVals .. " questions sur " .. #symp .. ".")
		res2:setPosition(0, 10)
		res1:addChild(res2)
		-- if correctVals == #symp then
		-- 		lock2 = 1
		--		local res3 = TextField.new(nil, "Vous avez débloqué le continent suivant !")
		--		res3:setPosition(0, 20)
		--		res2:addChild(res3)
		-- end
	end
	
	-- Check result
	
	local btns = {}
	
	function check(btn, thisIST)
	
		function checkButton(i)
			btns[i]:setText(symp[i][2] .. " (" .. current_symp .. ")")
			btns[i]:setState(Bitmap.new(LongButtonGray), Bitmap.new(LongButtonDown))
			result[i] = current_symp
		end
		
		function uncheckButton(i)
			btns[i]:setText(symp[i][2])
			btns[i]:setState(Bitmap.new(LongButtonUp), Bitmap.new(LongButtonDown))
			result[i] = nil
		end
		
		if result[thisIST] == nil then
			checkButton(thisIST)
		else
			uncheckButton(thisIST)
		end
		
		local response = 0
		for k, v in pairs(result) do
			if k ~= thisIST and v == current_symp then uncheckButton(k) end
			if v ~= nil then response = response + 1 end
		end
		
		for k, v in pairs(result) do
			print(k .. ", " .. tostring(v))
		end
		if response == #symp then printResult() end
	end
	
	-- Print IST Buttons
	
	local vert = 100
	local printed = {}
	
	math.randomseed(os.time())
	math.random(); math.random(); math.random()
	-- https://stackoverflow.com/questions/20154991/generating-uniform-random-numbers-in-lua#20157671
	
	for i = 1, #symp do
		local remaining = {}
		for i = 1, #symp do
			if printed[i] == nil then
				table.insert(remaining, i)
			end
		end
		local thisIST = remaining[math.random(#remaining)]
		printed[thisIST] = true

		local txt = TextField.new(nil, symp[thisIST][2])
		local btn = Button.new(Bitmap.new(LongButtonUp), Bitmap.new(LongButtonDown), txt)
		btn:setPosition(30, vert)
		btn:addEventListener("click", function() check(btn, thisIST) end)
		exp:addChild(btn)
		vert = vert + 40
		btns[thisIST] = btn
	end
	
	-- Quit Button

	local txt = TextField.new(nil, "Quitter le niveau")
	local quit = Button.new(Bitmap.new(QuitButtonUp), Bitmap.new(QuitButtonDown), txt)
	quit:setPosition(150, 430)
	quit:addEventListener("click", 
		function()
			stage:removeChild(background)
			launchMenuContinent(1,2)
		end
	)
	background:addChild(quit)
	
end