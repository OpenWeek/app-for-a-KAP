function level1()


	---- Declaration


	local background, title, exp, help, quest, buttonO, buttonN, buttonOK, buttonRE, buttonEnd, buttonQuit
	local readFile, nextQuestion, check, buttonNxt, reset, finishLvl
	local ist, other, questions, ansField, passed, numQ
	

	---- Functions

	readFile = function(file)
		local ist = {}
		local other = {}
		local questions = {}
		local istB = false
		local otherB = false
		for line in io.lines(file) do
			if line == "<ist>" then
				istB = true
			elseif line == "</ist>" then
				istB = false
			else
				if istB then
					if line == "<other>" then
						otherB = true
					elseif line == "</other>" then
						otherB = false
					else
						other[line] = true
						table.insert(questions, line)
					end
				else
					local thisIST = split(line, "=")
					ist[thisIST[1]] = thisIST[2]
					table.insert(questions, thisIST[1])
				end
			end
		end
		return ist, other, questions
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

	function check(answer)
		local indexIST = -1
		local indexOther = -1
		for i = 1, #ist do
			if questions[numQ] == ist[i][1] then
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
			quest:setText("Vrai : " .. ist[indexIST][2])
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

	function next()
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
	function reset()
		background:removeChild(buttonRE)
		ansField = {}
		numQ = nextQuestion()
		passed = 1
		quest:setText(questions[numQ])
		background:addChild(buttonO)
		background:addChild(buttonN)
	end

	function finishLvl()
		stage:removeChild(background)
		if lock1 < 1 then
			lock1 = 1
		end
		launchMenuContinent(1,2)
	end


	---- Initialization


	-- ist, other, questions = readFile("questions/questionsLvl3.txt")
	ist = {{"VIH", "C’est-à-dire le Virus de l’Immunodéficience Humaine. Si on le contracte on est séropositif. S’il reste non traité il détruit petit à petit le système immunitaire et on devient malade du sida (c’est-à-dire le Syndrome d’ImmunoDéficience Acquise)."},
		   {"Chlamydia", "la population la plus touchée par cette bactérie sont les femmes de 20 à 24 ans."},
		   {"Syphilis", "Elle est due à la bactérie tréponème pâle. La syphilis  est en troisième place sur le podium des IST les plus contractées par les belges."},
		   {"Gonorrhée", "C’est une infection due au gonocoque. Elle est 2 fois plus diagnostiquée chez les hommes, aussi appelée chaude pisse."},
		   {"Hépatite C", "1500 nouveaux cas de ce virus sont détectés chaque année en Belgique"},
		   {"Herpès génital", "Le virus Herpes simplex touche près de 2 millions de belges."},
		   {"Papillomavirus", "Ou HPV (human papillomavirus), il est responsable de la plupart des cancers du col de l’utérus."}}
	other = {"Rougeole", "Gale", "Tuberculose", "Cancer des testicules", "Maladie crohn", "Méningite",
				"Maladie de Lyme", "Fibromyalgie", "Mucoviscidose", "Polio", "Morpions",
				"Sclérose en plaque", "Rubéole", "Oreillon"}
	questions = {}
	for i = 1, #ist do
		table.insert(questions, ist[i][1])
	end
	for i = 1, #other do
		table.insert(questions, other[i])
	end
	ansField = {}
	passed = 0
	numQ = nextQuestion()


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

	quest = TextWrap.new(questions[numQ], 180, "justify", 1.5, font)
	quest:setPosition(70, 150)
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
