function level1()

	local IST = {{"VIH", "C’est-à-dire le Virus de l’Immunodéficience Humaine. Si on le contracte on est séropositif. S’il reste non traité il détruit petit à petit le système immunitaire et on devient malade du sida (c’est-à-dire le Syndrome d’ImmunoDéficience Acquise)."},
		   {"Chlamydia", "la population la plus touchée par cette bactérie sont les femmes de 20 à 24 ans."},
		   {"Syphilis", "Elle est due à la bactérie tréponème pâle. La syphilis  est en troisième place sur le podium des IST les plus contractées par les belges."},
		   {"Gonorrhée", "C’est une infection due au gonocoque. Elle est 2 fois plus diagnostiquée chez les hommes, aussi appelée chaude pisse."},
		   {"Hépatite C", "1500 nouveaux cas de ce virus sont détectés chaque année en Belgique"},
		   {"Herpès génital", "Le virus Herpes simplex touche près de 2 millions de belges."},
		   {"Papillomavirus", "Ou HPV (human papillomavirus), il est responsable de la plupart des cancers du col de l’utérus."}}
	local NotIST = {"Rougeole", "Gale", "Tuberculose", "Cancer des testicules", "Maladie crohn", "Méningite",
				"Maladie de Lyme", "Fibromyalgie", "Mucoviscidose", "Polio", "Morpions",
				"Sclérose en plaque", "Rubéole", "Oreillon"}

	wallLevel1 = Bitmap.new(FondNiveau)
	stage:addChild(wallLevel1)
	
	lock1 = 2
	
	-- Constantes des bouttons
	local posBut1H = 150
	local posBut2H = posBut1H + 70
	local posBut3H = posBut2H + 70
	
	-- Start du niveau
	math.randomseed(os.time())
	math.random(); math.random(); math.random()
	
	local backText = TextField.new(font,'Level 1 - Retour')	
	local back = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), backText)
	back:setPosition(MARGE, 400)
	back:addEventListener("click", 
		function()
			stage:removeChild(wallLevel1)
			launchMenuContinent(1,2)
		end)
	
	local Title = TextField.new(bigTitleFont, "Familiarisation")
	local txtDepart = "CONSIGNE : Dans cette première épreuve," .. 
						"tu vas devoir trouver les IST parmi une série d’autres noms... " .. 
						"D’ailleurs on parle maintenant d’IST, c’est-à-dire d’Infection Sexuellement Transmissible." .. 
						"On a abandonné le terme MST (=maladie sexuellement transmissible) car toutes les IST n’ont pas de symptômes visibles."
	local TxtDepart = TextWrap.new(txtDepart, 275, "justify", 1.5, font)
	Title:setPosition(80, 35)
	TxtDepart:setPosition(20, 60)
	wallLevel1:addChild(Title)
	wallLevel1:addChild(TxtDepart)
	
	local b1 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, IST[1][1]))
	local b2 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, NotIST[1]))
	local b3 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, NotIST[2]))
	local buttonOK = Button.new(Bitmap.new(LittleButtonUp), Bitmap.new(LittleButtonDown), TextField.new(font, "OK"))
	
	b1:setPosition(MARGE, posBut1H)
	b2:setPosition(MARGE, posBut2H)
	b3:setPosition(MARGE, posBut3H)
	buttonOK:setPosition(200, 250)
	
	-- Configuration du jeu	
	
	local numQ = 1
	
	function printQuestion()
		q = IST[numQ]
		b1:setText(q[1])
		local rdm = math.random(#NotIST)
		b2:setText(NotIST[rdm])
		local rdm2
		repeat 
			rdm2 = math.random(#NotIST)
		until rdm2 ~= rdm
		b3:setText(NotIST[rdm2])
		wallLevel1:addChild(b1)
		wallLevel1:addChild(b2)
		wallLevel1:addChild(b3)
	end
	
	function check(answer)
		wallLevel1:removeChild(b1)
		wallLevel1:removeChild(b2)
		wallLevel1:removeChild(b3)
		if answer == 1 then
			local q = IST[numQ]
			txtAnswer = TextWrap.new(q[2], 230, "justify", 1.5, font)
			txtAnswer:setPosition(40, 170)
			wallLevel1:addChild(txtAnswer)
		else
			local txtAnswer = TextField.new(font, "Non, il s'agissait de " .. IST[numQ][1]) 
			txtAnswer:setPosition(40, 200)
			wallLevel1:addChild(txtAnswer)
		end
		wallLevel1:addChild(buttonOK)
	end
	
	function next()
		wallLevel1:removeChild(buttonOK)
		wallLevel1:removeChild(txtAnswer)
		if numQ == #IST then
			lock1 = 2
		else
			numQ = numQ + 1
		end	
	end
	
	wallLevel1:addChild(back)
	printQuestion()
	
	b1:addEventListener("click", check, 1)
	b2:addEventListener("click", check, 2)
	b3:addEventListener("click", check, 3)
	buttonOK:addEventListener("click", next)
	
	--function readFile(file, ist, other)
		--local istB = false
		--local otherB = false
		--for line in io.lines(file) do
			--if line == "<ist>" then
				--istB = true
			--elseif line == "</ist>" then
				--istB = false
			--else
				--if istB then
					--if line == "<other>" then
						--otherB = true
					--elseif line == "</other>" then
						--otherB = false
					--else
						--table.insert(other, line)
					--end
				--else
					--table.insert(ist, split(line, "="))
				--end
			--end
		--end
	--end
end
