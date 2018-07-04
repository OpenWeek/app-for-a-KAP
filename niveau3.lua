function level3()
-- TODO barre de progression pour l'avancement des questions
	font = TTFont.new("Roboto-Condensed.ttf", 14)

	local fond = Bitmap.new(Texture.new("FondNiveauBlanc.png"))
	stage:addChild(fond)	
	
	-- 0 pour non, 1 pour oui
	-- Question, réponse, explication
	-- Il faudrait que ces questions soient lues depuis un fichier
	local questions = {{"Se faire tatouer, pratique a risque ?", 0, "Pas si tu le fais dans un salon qui respecte les normes d'hygiène"},
	                   {"Une fellation, pratique a risque ?", 1, "Cela nécessite un préservatif"},
					   {"Faire des massages", 0, "Le contact peau à peau n'est pas un mode de transmission, pas de soucis à se faire !"},
					   {"Une caresse sexuelle", 1, "Tu peux contracter l'herpès génital, le papillomavirus, la chlamydia, la gonorrhée et la syphilis au stade secondaire"},
					   {"Se faire piquer par un moustique", 0, "Les moustiques peuvent transmettre des maladies mais PAS des IST"},
					   {"Une pénétration", 1, "Anale comme vaginale, une pénétration doit se faire avec une capote ! Comme tu t'en doutes, les risques sont grands, tu peux contacter le VIH, l'hépatite B et C, la syphilis, l'herpès génital, le papillomavirus, la chlamydia et la gonorrhée"},
					   {"Echanger sa seringue", 1, "Il s'agit d'un contact de sang à sang, tu risques de contracter le VIH, l'hépatite B et C et la syphilis"},
					   {"Embrasser quelqu'un", 0, "Tu peux pécho sans remord. Par contre si ça va plus loin protège-toi !"},
					   {"Un cunnilingus", 1, "Pour pratiquer un cunnilingus safe, tu peux utiliser des carrés de latex, disponible en pharmacie. Tu risques de contracter l’hépatite B, la syphilis, l’herpès génital, le papillomavirus, la chlamydia et la gonorrhée"},
					   {"S’assoir sur une planche de toilette publique", 0, "Ce n’est pas toujours très ragoutant, on est d’accord mais pas de risque d’y attraper des IST !"},
					   {"Boire dans le verre d’un inconnu", 0, "La salive n’est pas un mode de transmission, par contre reste quand même vigilant·e, on ne sait pas ce qu’il peut y avoir d’autre dedans..."},
					   {"Se faire vacciner", 0, "En Belgique les mesures d’hygiène sont très contrôlées,  tu ne risques pas de contracter une IST lors d’une visite chez le médecin"},
					   {"Recevoir une transfusion sanguine", 0, "le sang donné est strictement contrôlé, pas de panique"}}
	
	-- Boutons
	textureUp = Texture.new("little_button_up.png")
	textureDown = Texture.new("little_button_down.png")
	local up = Bitmap.new(Texture.new("quit_button_up.png"))
	local down = Bitmap.new(Texture.new("quit_button_down.png"))
	local upLO = Bitmap.new(textureUp)
	local downLO = Bitmap.new(textureDown)
	local upLN = Bitmap.new(textureUp)
	local downLN = Bitmap.new(textureDown)
	local upOK = Bitmap.new(textureUp)
	local downOK = Bitmap.new(textureDown)
	local upRE = Bitmap.new(textureUp)
	local downRE = Bitmap.new(textureDown)
	local txt = TextField.new(font, "Quitter le niveau")
	local txtO = TextField.new(font, "OUI")
	local txtN = TextField.new(font, "NON")
	local txtOK = TextField.new(font, "OK")
	local txtRE = TextField.new(font, "Refaire")	
	
	local buttonO = Button.new(upLO, downLO, txtO)
	local buttonN = Button.new(upLN, downLN, txtN)
	local buttonOK = Button.new(upOK, downOK, txtOK)
	local buttonRE = Button.new(upRE, downRE, txtRE)
	local buttonQuit = Button.new(up, down, txt)
	
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