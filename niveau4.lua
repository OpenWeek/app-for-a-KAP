function level4()

	local fond = Bitmap.new(FondNiveau)
	stage:addChild(fond)
	
	local txt = TextField.new(font, "Quitter le niveau")
	
	local buttonQuit = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), txt)
	buttonQuit:setPosition(70, 400)
	fond:addChild(buttonQuit)
	buttonQuit:addEventListener("click", 
		function() 
			stage:removeChild(fond)
			launchMenuContinent(3,1)
		end)	
	-- 0 pour non, 1 pour oui
	-- Question, réponse, explication
	local questions = {}
	function lines_from()
		local lines = {}
		for line in io.lines("questions.txt") do
			table.insert(lines, line)
			if #lines == 5 then
				table.insert(questions, lines)
				lines = {}
			end
		end
	end
	
	local txtO = TextField.new(font, "OUI")
	local txtN = TextField.new(font, "NON")
	
	local buttonO = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), txtO)
	local buttonN = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), txtN)
	local buttonOK = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "OK"))
	
	buttonO:setPosition(40, 250)
	buttonN:setPosition(200, 250)
	buttonOK:setPosition(200, 250)
	numQ = 1
	
	function printQuestion()
		q = questions[numQ]
		quest = TextWrap.new(q[1], 280, "justify", 1.5, font)
		quest:setPosition(10, 150)
		fond:addChild(quest)
		fond:addChild(buttonO)
		fond:addChild(buttonN)
		txtN:setText(q[4])
		txtO:setText(q[5])
	end
	
	function check(answer) 
		local txtA
		if tonumber(q[2]) == answer then
			txtA = "VRAI : " .. q[3]
		else
			txtA = "FAUX : " .. q[3]
		end
		ans = TextWrap.new(txtA, 280, "justify", 1.5, font)
		fond:removeChild(quest)
		fond:removeChild(buttonO)
		fond:removeChild(buttonN)
		
		ans:setPosition(10, 150)
		fond:addChild(ans)
		fond:addChild(buttonOK)
		numQ = numQ + 1
	end
	
	function next()
		fond:removeChild(ans)
		fond:removeChild(buttonOK)
		
		if numQ > #questions then
			stage:removeChild(fond)
		else
			printQuestion()
		end
	end
	
	lines_from()
	printQuestion()
	buttonO:addEventListener("click", check, 1)
	buttonN:addEventListener("click", check, 0)
	buttonOK:addEventListener("click", next)	
end