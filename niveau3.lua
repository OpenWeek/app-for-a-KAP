function level3()


	---- Declaration
	
	
	local background, title, exp, help, quest, buttonO, buttonN, buttonOK, buttonRE, buttonEnd, buttonQuit
	local nextQuestion, check, buttonNxt, reset, finishLvl
	local questions, ansField, passed, numQ
	
	
	---- Functions

	
	-- Check if question number 'numQ' has been correctly answered by 'answer'
	function check(answer)
		if tonumber(questions[numQ][2]) == answer then
			ansField[numQ] = true
			quest:setText("Vrai : " .. questions[numQ][3])
		else
			ansField[numQ] = false
			quest:setText("Faux : " .. questions[numQ][3])
		end
		background:removeChild(buttonO)
		background:removeChild(buttonN)
		background:addChild(buttonNxt)
	end
	
	-- Look at how much questions are remaining
	-- Give a random question number or -1 if there isn't anymore
	nextQuestion = function()
		local remaining = {}
		for i = 1, #questions do
			if ansField[i] == nil then
				table.insert(remaining, i)
			end
		end
		if #remaining ~= 0 then
			passed = passed + 1
			return remaining[math.random(#remaining)]
		else
			return -1
		end
	end
	
	-- Go to the next question or if there isn't anymore, compute final score and
	-- unlock next continent or make quizz start again
	function next()
		background:removeChild(buttonNxt)
		numQ = nextQuestion()
		if numQ ~= -1 then
			quest:setText(questions[numQ][1])
			help:setText("Vous êtes à la question " .. passed .. " sur " .. #questions .. ".")
			background:addChild(buttonO)
			background:addChild(buttonN)
		else
			local goodAns = 0
			for k, v in pairs(ansField) do
				if v then
					goodAns = goodAns + 1
				end
			end
			background:addChild(buttonRE)
			if goodAns < (#questions * 0.8) then
				quest:setText("Vous n'avez pas assez de bonnes réponses, vous en avez " .. goodAns .. " bonnes sur " .. #questions .. ".")
			else
				quest:setText("Vous avez " .. goodAns .. " bonnes réponses sur " .. #questions .. ". Continent 3 débloqué !")
				background:addChild(buttonEnd)
			end
		end
	end
	
	-- Function for reset button
	function reset()
		background:removeChild(buttonRE)
		ansField = {}
		numQ = nextQuestion()
		passed = 1
		quest:setText(questions[numQ][1])
		background:addChild(buttonO)
		background:addChild(buttonN)
	end
	
	-- Function for quit button 
	function finishLvl()
		stage:removeChild(background)
		if lock3 ~= 1 then
			lock3 = 1
			startMiniGame(2)
		else
			launchMenuContinent(3,1)
		end
	end
	
	
	---- Initialization
	
	
	questions = readFile("questions/QuestionsLvl3.txt", 3)
	ansField = {}
	passed = 0
	numQ = nextQuestion()
	

	---- Interface


	-- Background --

	background = Bitmap.new(FondNiveau)
	stage:addChild(background)
	
	-- Title --

	title = TextField.new(titleFont, "Niveau 3 : Trouvez les pratiques à risque !")
	title:setPosition(10, 25)
	background:addChild(title)
	
	-- Explication text --

	exp = TextWrap.new("Appuyez sur 'Oui' si la pratique suivant est à risque. Sinon, appuyez sur 'Non'.", 280, "justify", 1.5, font)
	exp:setPosition(10, 50)
	background:addChild(exp)
	
	-- Help text --

	help = TextField.new(font, "Vous êtes à la question " .. passed .. " sur " .. #questions .. ".")
	help:setPosition(10, 100)
	background:addChild(help)
	
	-- Question --
	
	quest = TextWrap.new(questions[numQ][1], 180, "justify", 1.5, font)
	quest:setPosition(70, 150)
	background:addChild(quest)
	
	-- Button Oui --
	
	buttonO = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Oui"))
	buttonO:setPosition(40, 250)
	background:addChild(buttonO)
	buttonO:addEventListener("click", check, 1)
	
	-- Button Non --
	
	buttonN = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Non"))
	buttonN:setPosition(200, 250)
	background:addChild(buttonN)
	buttonN:addEventListener("click", check, 0)
	
	-- Button Next --
	
	buttonNxt = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Suivant"))
	buttonNxt:setPosition(200, 250)
	buttonNxt:addEventListener("click", next)
	
	-- Button Reset --
	
	buttonRE = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Réessayer"))
	buttonRE:setPosition(40,250)
	buttonRE:addEventListener("click", reset)
	
	-- Button End --
	
	buttonEnd = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Terminer"))
	buttonEnd:setPosition(200,250)
	buttonEnd:addEventListener("click", finishLvl)
	
	-- Button Quit --
	
	buttonQuit = Button.new(Bitmap.new(QuitButtonUp), Bitmap.new(QuitButtonDown), TextField.new(font, "Quitter le niveau"))
	buttonQuit:setPosition(140, 435)
	background:addChild(buttonQuit)
	buttonQuit:addEventListener("click", finishLvl)

	
end