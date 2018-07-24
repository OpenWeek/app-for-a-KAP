function level1()

	---- Declaration


	local background, title, exp, help, quest, buttonO, buttonN, buttonOK, buttonRE, buttonEnd, buttonQuit
	local init, nextQuestion, check, buttonNxt, reset, finishLvl
	local ist, expl, other, questions, ansField, passed, numQ


	---- Functions


	init = function()
		local raw = readFile("questions/QuestionsLvl1.txt", 1)	-- Contain raw data from questionLvl1 file
		other = raw[2]											-- List containing notIST sickness
		ist = {}												-- List containing names of IST sicknesses
		questions = {}											-- List containing both IST and other sicknesses names
		expl = {}												-- List containing descriptions of IST (with same index as ist)

		-- We separate IST names from questions
		for i = 1, #raw[1] do
			table.insert(ist, raw[1][i][1])
			table.insert(expl, raw[1][i][2])
		end
		for i = 1, #ist do
			table.insert(questions, ist[i])
		end
		for i = 1, #other do
			table.insert(questions, other[i])
		end

		ansField = {}
		passed = 0
		raw = nil
		return nextQuestion()
	end

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

	check = function(answer)
		local indexIST = -1
		local indexOther = -1
		for i = 1, #ist do
			if questions[numQ] == ist[i] then
				indexIST = i
			end
		end
		for i = 1, #other do
			if questions[numQ] == other[i] then
				indexOther = i
			end
		end
		if answer and indexIST ~= -1 then
			ansField[numQ] = true
			quest:setText("Vrai : " .. expl[indexIST])
		elseif answer then
			ansField[numQ] = false
			quest:setText("Faux : ce n'est pas une IST.")
		elseif not answer and indexOther ~= -1 then
			ansField[numQ] = true
			quest:setText("Vrai : ce n'est pas une IST.")
		elseif not answer then
			ansField[numQ] = false
			quest:setText("Faux : il s'agit bien d'une IST.")
		end
		background:removeChild(buttonO)
		background:removeChild(buttonN)
		background:addChild(buttonNxt)
	end

	next = function()
		background:removeChild(buttonNxt)
		numQ = nextQuestion()
		if numQ ~= -1 then
			quest:setText(questions[numQ])
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
				quest:setText("Vous avez " .. goodAns .. " bonnes réponses sur " .. #questions .. ". Niveau suivant débloqué !")
				background:addChild(buttonEnd)
			end
		end
	end

	-- Fonction pour le bouton recommencer
	reset = function()
		background:removeChild(buttonRE)
		ansField = {}
		numQ = nextQuestion()
		passed = 1
		help:setText("Vous êtes à la question " .. passed .. " sur " .. #questions .. ".")
		quest:setText(questions[numQ])
		background:addChild(buttonO)
		background:addChild(buttonN)
	end

	finishLvl = function()
		stage:removeChild(background)
		if lock1 < 2 then
			lock1 = 2
		end
		collectgarbage()
		launchMenuContinent(1,2)
	end


	---- Initialization

	numQ = init()

	---- Interface

	-- Background --

	background = Bitmap.new(FondNiveau)
	stage:addChild(background)

	-- Title --

	title = TextField.new(titleFont, "Niveau 1 : Trouvez les IST !")
	title:setPosition(10, 25)
	background:addChild(title)

	-- Explication text --

	exp = TextWrap.new("Appuyez sur 'Oui' si la maladie suivant est une IST. Sinon, appuyez sur 'Non'.", 280, "justify", 1.5, font)
	exp:setPosition(10, 50)
	background:addChild(exp)

	-- Help text --

	help = TextField.new(font, "Vous êtes à la question " .. passed .. " sur " .. #questions .. ".")
	help:setPosition(10, 100)
	background:addChild(help)

	-- Question --

	quest = TextWrap.new(questions[numQ], 200, "justify", 1.5, font)
	quest:setPosition(30, 150)
	background:addChild(quest)

	-- Button Oui --

	buttonO = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Oui"))
	buttonO:setPosition(40, 250)
	background:addChild(buttonO)
	buttonO:addEventListener("click", check, true)

	-- Button Non --

	buttonN = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Non"))
	buttonN:setPosition(200, 250)
	background:addChild(buttonN)
	buttonN:addEventListener("click", check, false)

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
