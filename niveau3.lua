function level3()

local fond = Bitmap.new(Texture.new("FondNiveau.png"))
	stage:addChild(fond)
	
	local up = Bitmap.new(Texture.new("button_up.png"))
	local down = Bitmap.new(Texture.new("button_down.png"))
	local txt = TextField.new(nil, "Quitter le niveau")
	
	local buttonQuit = Button.new(up, down, txt)
	buttonQuit:setPosition(70, 400)
	fond:addChild(buttonQuit)
	buttonQuit:addEventListener("click", 
		function() 
			stage:removeChild(fond)
		end)	
	-- 0 pour non, 1 pour oui
	-- Question, réponse, explication
	-- Il faudrait que ces questions soient lues depuis un fichier
	local questions = {{"Se faire tatouer, pratique a risque ?", 0, "Pas si tu le fais dans un salon\n qui respecte les normes d'hygiène"},
	                   {"Une fellation, pratique a risque ?", 1, "Cela nécessite un préservatif"},
					   {"Faire des massages", 0, "Le contact peau à peau\n n'est pas un mode de transmission,\n pas de soucis à se faire !"},
					   {"Une caresse sexuelle", 1, "Tu peux contracter l'herpès génital, le papillomavirus, la chlamydia,\n la gonorrhée et la syphilis au stade secondaire"},
					   {"Se faire piquer par un moustique", 0, "Les moustiques peuvent transmettre des maladies mais PAS des IST"},
					   {"Une pénétration", 1, "Anale comme vaginale, une pénétration doit se faire avec une capote !\n Comme tu t'en doutes, les risques sont grands, tu peux contacter le VIH, l'hépatite B et C, la syphilis, l'herpès génital, le papillomavirus, la chlamydia et la gonorrhée"},
					   {"Echanger sa seringue", 1, "Il s'agit d'un contact de sang à sang,\n tu risques de contracter le VIH,\n l'hépatite B et C et la syphilis"},
					   {"Embrasser quelqu'un", 0, "Tu peux pécho sans remord. Par contre si ça va plus loin protège-toi !"},
					   {"Un cunnilingus", 1, "Pour pratiquer un cunnilingus safe,\n tu peux utiliser des carrés de latex, disponible en pharmacie.\n Tu risques de contracter l’hépatite B, la syphilis, l’herpès génital, le papillomavirus, la chlamydia et la gonorrhée"},
					   {"S’assoir sur une planche de toilette publique", 0, "Ce n’est pas toujours très ragoutant,\n on est d’accord mais pas de risque d’y attraper des IST !"},
					   {"Boire dans le verre d’un inconnu", 0, "La salive n’est pas un mode de transmission,\n par contre reste quand même vigilant·e, on ne sait pas ce qu’il peut y avoir d’autre dedans..."},
					   {"Se faire vacciner", 0, "En Belgique les mesures d’hygiène sont très contrôlées, \n tu ne risques pas de contracter une IST lors d’une visite chez le médecin"},
					   {"Recevoir une transfusion sanguine", 0, "le sang donné est strictement contrôlé,\n pas de panique"}}
	
	local upLO = Bitmap.new(Texture.new("little_button_up.png"))
	local downLO = Bitmap.new(Texture.new("little_button_down.png"))
	local upLN = Bitmap.new(Texture.new("little_button_up.png"))
	local downLN = Bitmap.new(Texture.new("little_button_down.png"))
	local upOK = Bitmap.new(Texture.new("little_button_up.png"))
	local downOK = Bitmap.new(Texture.new("little_button_down.png"))
	local upRE = Bitmap.new(Texture.new("little_button_up.png"))
	local downRE = Bitmap.new(Texture.new("little_button_down.png"))
	local upFIN = Bitmap.new(Texture.new("little_button_up.png"))
	local downFIN = Bitmap.new(Texture.new("little_button_down.png"))
	local txtO = TextField.new(nil, "OUI")
	local txtN = TextField.new(nil, "NON")
	local txtOK = TextField.new(nil, "OK")
	local txtRE = TextField.new(nil, "Refaire")
	local txtFIN = TextField.new(nil, "Bien joué !")
	
	local buttonO = Button.new(upLO, downLO, txtO)
	local buttonN = Button.new(upLN, downLN, txtN)
	local buttonOK = Button.new(upOK, downOK, txtOK)
	local buttonRE = Button.new(upRE, downRE, txtRE)
	local buttonFIN = Button.new(upFIN, downFIN, txtFIN)
	
	buttonO:setPosition(40, 250)
	buttonN:setPosition(200, 250)
	buttonOK:setPosition(200, 250)
	buttonRE:setPosition(200,250)
	buttonFIN:setPosition(200,250)
	
	numQ = 1
	ansField = {}
	
	function printQuestion()
		q = questions[numQ]
		quest = TextField.new(nil, q[1])
		quest:setPosition(10, 150)
		fond:addChild(quest)
		fond:addChild(buttonO)
		fond:addChild(buttonN)
	end
	
	function check(answer) 
		if q[2] == answer then
			ansField[#ansField+1] = true
			txtA = "VRAI : " .. q[3]
			ans = TextField.new(nil, txtA)
		else
			ansField[#ansField+1] = false
			txtA = "FAUX : " .. q[3]
			ans = TextField.new(nil, txtA)
		end
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
			goodAns = countGoodAnswers(ansField)
			if goodAns < (#ansField * 0.8) then
				Txt = "Vous n'avez pas assez de bonnes réponses, \nvous en avez " .. goodAns .. " bonnes sur " .. #ansField
				faux = TextField.new(nil, Txt)
				faux:setPosition(10, 150)
				fond:addChild(faux)
				fond:addChild(buttonRE)
			else
				Txt = "Vous avez " .. goodAns .. " bonnes réponses sur " .. #ansField
				vrai = TextField.new(nil, Txt)
				vrai:setPosition(10, 150)
				fond:addChild(vrai)
				fond:addChild(buttonFIN)
			end
		else
			printQuestion()
		end
	end
	
	function reset()
		numQ = 1
		ansField = {}
		fond:removeChild(faux)
		fond:removeChild(buttonRE)
		printQuestion()
	end
	
	function finishLvl()
		stage:removeChild(fond)
	end
	
	printQuestion()
	buttonO:addEventListener("click", check, 1)
	buttonN:addEventListener("click", check, 0)
	buttonOK:addEventListener("click", next)
	buttonRE:addEventListener("click", reset)
	buttonFIN:addEventListener("click", finishLvl)
end