function level4()

	---- Declaration


	local background, title, helpText, buttonO, buttonN, buttonOK, buttonQuit
	local printQuestion, check, next, quit
	local questions, ans, numQ, q


	---- Functions


	printQuestion = function()
		q = questions[numQ]
		quest = TextWrap.new(q[1], 280, "justify", 1.5, font)
		quest:setPosition(10, 150)
		background:addChild(quest)
		background:addChild(buttonO)
		background:addChild(buttonN)
		buttonN:setText(q[4])
		buttonO:setText(q[5])
	end

	check = function(answer)
		local txtA
		if tonumber(q[2]) == answer then
			txtA = "VRAI : " .. q[3]
		else
			txtA = "FAUX : " .. q[3]
		end
		ans = TextWrap.new(txtA, 280, "justify", 1.5, font)
		background:removeChild(quest)
		background:removeChild(buttonO)
		background:removeChild(buttonN)

		ans:setPosition(10, 150)
		background:addChild(ans)
		background:addChild(buttonOK)
		numQ = numQ + 1
	end

	next = function()
		background:removeChild(ans)
		background:removeChild(buttonOK)

		if numQ > #questions then
			stage:removeChild(background)
			lock4 = 1
			launchMenuContinent(3,1)
		else
			printQuestion()
		end
	end

	quit = function()
		stage:removeChild(background)
		collectgarbage()
		launchMenuContinent(3,1)
	end


	---- Interface

	-- Background --

	background = Bitmap.new(FondNiveau)
	stage:addChild(background)

	-- Title --

	title = TextWrap.new("Niveau 4 : Dépistage", 280, "justify", 1.5, titleFont)
	title:setPosition(10, 25)
	background:addChild(title)

	-- HelpText --

	helpText = TextWrap.new("Maintenant que tu connais tout sur les IST et leurs modes de transmission, il est temps de tester tes connaissances sur la prévention et le dépistage… Réponds aux questions et tu découvriras si tu es prêt à affronter le monde cruel des IST !", 280, "justify", 1.5, font)
	helpText:setPosition(10, 40)
	background:addChild(helpText)

	-- Button Oui --

	buttonO = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "OUI"))
	buttonO:setPosition(40, 250)
	buttonO:addEventListener("click", check, 1)

	-- Button Non --

	buttonN = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "NON"))
	buttonN:setPosition(200, 250)
	buttonN:addEventListener("click", check, 0)

	-- Button OK --

	buttonOK = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "OK"))
	buttonOK:setPosition(200, 250)
	buttonOK:addEventListener("click", next)

	-- Button Quit --

	buttonQuit = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, "Quitter le niveau"))
	buttonQuit:setPosition(70, 400)
	buttonQuit:addEventListener("click", quit)
	background:addChild(buttonQuit)


	---- Initialization

	numQ = 1
	questions = readFile("questions/QuestionsLvl4.txt", 4)
	printQuestion()

end
