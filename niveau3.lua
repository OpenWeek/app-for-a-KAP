function level3()
-- TODO barre de progression pour l'avancement des questions

	local fond = Bitmap.new(FondNiveau)
	stage:addChild(fond)

	-- 0 pour non, 1 pour oui
	-- Question, réponse, explication
	local questions = {}
	local lines = {}
	for line in io.lines("questions/questionsLvl3.txt") do
		table.insert(lines, line)
		if #lines == 3 then
			table.insert(questions, lines)
			lines = {}
		end
	end
	
	-- Définitions Boutons
	local buttonO = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "OUI"))
	local buttonN = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "NON"))
	local buttonOK = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "OK"))
	local buttonRE = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "Refaire"))
	local buttonQuit = Button.new(Bitmap.new(QuitButtonUp), Bitmap.new(QuitButtonDown), TextField.new(font, "Quitter le niveau"))
	
	buttonO:setPosition(40, 250)
	buttonN:setPosition(200, 250)
	buttonOK:setPosition(200, 250)
	buttonRE:setPosition(200,250)
	buttonQuit:setPosition(70, 400)	
	
	---- Questions
	-- Liste des réponses aux questions, indice correspond à l'indice de la question et true/false
	local ansField = {}
	-- Question en cours
	local q
	local numQ = math.random(#questions)
	
	math.randomseed(os.time())
	math.random(); math.random(); math.random()
	-- https://stackoverflow.com/questions/20154991/generating-uniform-random-numbers-in-lua
	
	---- Comportement des boutons
	function printQuestion()
		q = questions[numQ]
		quest = TextWrap.new(q[1], 300, "justify", nil, font)
		quest:setPosition(10, 150)
		fond:addChild(quest)
		fond:addChild(buttonO)
		fond:addChild(buttonN)
	end
	
	-- Check si la réponse est correcte
	-- answer : boolean
	function check(answer) 
		local txtA
		if q[2] == answer then
			ansField[numQ] = true
			txtA = "VRAI : " .. q[3]
		else
			ansField[numQ] = false
			txtA = "FAUX : " .. q[3]
		end
		ans = TextWrap.new(txtA, 300, "justify", nil, font)
		fond:removeChild(quest)
		fond:removeChild(buttonO)
		fond:removeChild(buttonN)
		
		ans:setPosition(10, 150)
		fond:addChild(ans)
		fond:addChild(buttonOK)
	end
	
	function next()
		fond:removeChild(ans)
		fond:removeChild(buttonOK)
		-------
		-- On ajoute toutes les indices questions restantes dans remaining
		local remaining = {}
		for i = 1, #questions do
			if ansField[i] == nil then
				table.insert(remaining, i)
			end
		end		
		if #remaining ~= 0 then
			local i = math.random(#remaining)
			numQ = remaining[i]
			printQuestion()
		else
			local goodAns = 0
			for k, v in pairs(ansField) do
				if v then
					goodAns = goodAns + 1
				end
			end
			if goodAns < (#questions * 0.8) then
				Txt = "Vous n'avez pas assez de bonnes réponses, vous en avez " .. goodAns .. " bonnes sur " .. #questions
				faux = TextWrap.new(Txt, 300, "justify", nil, font)
				faux:setPosition(10, 150)
				fond:addChild(faux)
				fond:addChild(buttonRE)
			else
				lock3 = 1
				Txt = "Vous avez " .. goodAns .. " bonnes réponses sur " .. #questions
				vrai = TextField.new(font, Txt)
				vrai:setPosition(10, 150)
				Txt2 = "Continent 3 débloqué !"
				debloc = TextField.new(font, Txt2)
				debloc:setPosition(10,100)
				fond:addChild(vrai)
			end
		end
	end
	
	-- Fonction pour le bouton recommencer
	function reset()
		numQ = math.random(#questions)
		ansField = {}
		fond:removeChild(faux)
		fond:removeChild(buttonRE)
		printQuestion()
	end
	
	function finishLvl()
		stage:removeChild(fond)
		launchMenuContinent(2,1)
	end
	
	fond:addChild(buttonQuit)
	
	printQuestion()
	buttonQuit:addEventListener("click", finishLvl)	
	buttonO:addEventListener("click", check, 1)
	buttonN:addEventListener("click", check, 0)
	buttonOK:addEventListener("click", next)
	buttonRE:addEventListener("click", reset)
end