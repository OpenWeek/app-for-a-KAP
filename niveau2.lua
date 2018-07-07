function level2()

	---- Declaration


	local background, title, exp, help, leftArrow, symp_text, rightArrow, valid, quit
	local check, printResult, countAnswers, checkButton, uncheckButton
	local symp, current_symp, vert, btns, result


	---- Functions


	check = function(thisIST)
		if result[thisIST] == nil then
			btns[thisIST]:setText(symp[thisIST][2] .. " - " .. current_symp)
			btns[thisIST]:setState(Bitmap.new(LongButtonGray), Bitmap.new(LongButtonDown))
			result[thisIST] = current_symp
		else
			btns[thisIST]:setText(symp[thisIST][2])
			btns[thisIST]:setState(Bitmap.new(LongButtonUp), Bitmap.new(LongButtonDown))
			result[thisIST] = nil
		end

		for k, v in pairs(result) do
			if k ~= thisIST and v == current_symp then
				btns[k]:setText(symp[k][2])
				btns[k]:setState(Bitmap.new(LongButtonUp), Bitmap.new(LongButtonDown))
				result[k] = nil
			end
		end

		if answers() == #symp then
			valid:setState(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown))
		else
			valid:setState(Bitmap.new(GrayButton), Bitmap.new(ButtonDown))
		end
	end

	printResult = function()
		background:removeChild(exp)

		local vert = 20
		local total, correctVals, errors = answers()

		local res = TextField.new(font, "Vous avez répondu correctement à " .. correctVals .. " questions sur " .. total .. ".")
		res:setPosition(10, 45)
		background:addChild(res)

		if correctVals >= 2 * total / 3 then
			local unlock = TextField.new(font, "Vous avez débloqué le continent suivant !")
			unlock:setPosition(0, vert)
			res:addChild(unlock)

			vert = vert + 20
		end

		local errorText = ""
		local i = 0
		for k, v in pairs(errors) do
			i = i + 1
			if i < total - correctVals - 1 then
				errorText = errorText .. v .. ", "
			elseif i == total - correctVals - 1 then
				errorText = errorText .. v .. " et "
			else
				errorText = errorText .. v .. "."
			end
		end

		if i > 0 then
			local error = TextWrap.new("Vous vous êtes trompé aux IST suivantes : " .. errorText, 280, "justify", 1.5, font)
			error:setPosition(0, vert)
			res:addChild(error)

			vert = vert + 50
		end

		local reload = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, "Réessayer"))
		reload:setPosition(30, vert)
		res:addChild(reload)
		reload:addEventListener("click",
			function()
				stage:removeChild(background)
				level2()
			end
		)

		vert = vert + 80

		if correctVals >= 2 * total / 3 or lock2 == 1 then
			local next = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, "Aller au continent suivant"))
			next:setPosition(30, vert)
			res:addChild(next)
			next:addEventListener("click",
				function()
					stage:removeChild(background)
					if lock2 ~= 1 then
						lock2 = 1
						startMiniGame(1)
					else
						launchMenuContinent(2,1)
					end
				end
			)
		end
	end

	answers = function()
		local total = 0
		local correctVals = 0
		local errors = {}
		local i = 1
		for k, v in pairs(result) do
			if v ~= nil then
				total = total + 1
				if k == v then
					correctVals = correctVals + 1
				else
					errors[v] = symp[k][2]
				end
			end
			i = i + 1
		end
		return total, correctVals, errors
	end


	---- Initialization


	symp = readFile("questions/QuestionsLvl2.txt", 2)
	current_symp = 1
	vert = 100
	btns = {}
	result = {}


	---- Interface


	-- Background --

	background = Bitmap.new(FondNiveau)
	stage:addChild(background)

	-- Title --

	title = TextField.new(titleFont, "Niveau 2 : Reliez les IST et leur symptômes !")
	title:setPosition(10, 25)
	background:addChild(title)

	-- Explication text --

	exp = TextField.new(font, "Appuyez sur les flèches pour changer de symptôme.")
	exp:setPosition(10, 50)
	background:addChild(exp)

	help = TextField.new(font, "Vous êtes au symptôme " .. current_symp .. " sur " .. #symp .. ".")
	help:setPosition(0, 20)
	exp:addChild(help)

	-- Left button --

	leftArrow = Button.new(Bitmap.new(LeftArrow), Bitmap.new(LeftArrow), nil)
	leftArrow:setPosition(0, 45)
	exp:addChild(leftArrow)
	leftArrow:addEventListener("click",
		function()
			if current_symp > 1 then
				current_symp = current_symp - 1
				symp_text:setText(symp[current_symp][1])
				help:setText("Vous êtes au symptôme " .. current_symp .. " sur " .. #symp .. "..")
			end
		end
	)

	-- Current Symp --

	symp_text = TextWrap.new(symp[current_symp][1], 250, "justify", 1.5, smallFont)
	symp_text:setPosition(20, 40)
	exp:addChild(symp_text)

	-- Right button --

	rightArrow = Button.new(Bitmap.new(RightArrow), Bitmap.new(RightArrow), nil)
	rightArrow:setPosition(290, 45)
	exp:addChild(rightArrow)
	rightArrow:addEventListener("click",
		function()
			if current_symp < #symp then
				current_symp = current_symp + 1
				symp_text:setText(symp[current_symp][1])
				help:setText("Vous êtes au symptôme " .. current_symp .. " sur " .. #symp .. ".")
			end
		end
	)

	-- IST Buttons --

	local printed = {}
	for i = 1, #symp do
		local remaining = {}
		for i = 1, #symp do
			if printed[i] == nil then
				table.insert(remaining, i)
			end
		end
		local thisIST = remaining[math.random(#remaining)]

		local btn = Button.new(Bitmap.new(LongButtonUp), Bitmap.new(LongButtonDown), TextField.new(font, symp[thisIST][2]))
		btn:setPosition(30, vert)
		exp:addChild(btn)
		btn:addEventListener("click", check, thisIST)

		vert = vert + 35
		printed[thisIST] = true
		btns[thisIST] = btn
	end

	-- Validate Button --

	valid = Button.new(Bitmap.new(GrayButton), Bitmap.new(ButtonDown), TextField.new(font, "Valider"))
	valid:setPosition(30, vert + 5)
	exp:addChild(valid)
	valid:addEventListener("click",
		function()
			if answers() == #symp then
				printResult()
			end
		end
	)

	-- Quit Button --

	quit = Button.new(Bitmap.new(QuitButtonUp), Bitmap.new(QuitButtonDown), TextField.new(font, "Quitter le niveau"))
	quit:setPosition(140, 435)
	background:addChild(quit)
	quit:addEventListener("click",
		function()
			stage:removeChild(background)
			launchMenuContinent(1,2)
		end
	)


end
