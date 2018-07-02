function level2()

	-- Background

	local background = Bitmap.new(Texture.new("FondNiveauBlanc.png"))
	stage:addChild(background)
	
	local title = TextField.new(nil, "Niveau 2")
	title:setPosition(30, 30)
	background:addChild(title)
	
	-- Symptoms
	
	local symp = {
		{"VIH", "2 à 4 semaines après la contamination (pas systématiques) : fièvre, diarrhée, éruption cutanée (plaques de petits boutons), douleurs musculaires, apparitions de ganglions, fatigue."},
		{"Hépatites B et C", "2 à 8 semaines après la contamination (pas systématiques) : fièvre, jaunisse, fatigue."},
		{"Papillomavirus", "1 à 8 semaines après la contamination : petites verrues indolores sur les organes génitaux."},
		{"Gonorrhée", "2 à 7 jours après la contamination : brulures quand on urine, écoulement jaunâtre/verdâtre par le vagin, la verge ou l’anus, douleur au vagin ou à l’extrémité du gland, fièvre."},
		{"Chlamydia", "Majoritairement asymptomatique, mais ceux-ci peuvent apparaitre de quelques jours à quelques mois après la contamination : perte vaginale anormale, écoulement clair par le vagin, l’anus ou le pénis, rougeur des muqueuses, brulure lorsqu’on urine."},
		{"Herpès génital", "2 à 20 jours (mais parfois après quelques années) après la contamination : cloches sur les organes génitaux ou l’anus, douleur quand on urine."}
	}
	
	-- Explication text
	
	local exp = TextField.new(nil, "Quelle IST cause ces symptômes ?")
	exp:setPosition(10, 60)
	background:addChild(exp)
	
	-- Print Question
	
	local question
	local next
	local done = {}
	math.randomseed(os.time())
	math.random(); math.random(); math.random()
	-- https://stackoverflow.com/questions/20154991/generating-uniform-random-numbers-in-lua
	
	next = function()
		local remaining = {}
		for i = 1, #symp do
			if done[i] == nil then
				table.insert(remaining, i)
			end
		end
		if #remaining ~= 0 then
			local i = math.random(#remaining)
			question(remaining[i])
		else
			local correctVals = 0
			for k, v in pairs(done) do
				if v then
					correctVals = correctVals + 1
				end
			end
			background:removeChild(exp)
			local res1 = TextField.new(nil, "Plus de questions !")
			res1:setPosition(10, 60)
			background:addChild(res1)
			local res2 = TextField.new(nil, "Vous avez répondu correctement à " .. correctVals .. " questions sur " .. #symp .. ".")
			res2:setPosition(0, 10)
			res1:addChild(res2)
		end
	end

	question = function(numQ)
		-- Show this question
		local q = TextWrap.new(symp[numQ][2], 300, "justify")
		q:setPosition(10, 90)
		background:addChild(q)
		-- Check Answer
		local function check(answer)
			background:removeChild(q)
			local answerText = ""
			if answer == symp[numQ][1] then
				answerText = "Bien joué, vous avez trouvé la bonne réponse !"
				done[numQ] = true
			else
				answerText = "Dommage, ce n'était pas la bonne réponse..."
				done[numQ] = false
			end
			local a = TextField.new(nil, answerText)
			a:setPosition(10, 100)
			background:addChild(a)
			local up = Bitmap.new(Texture.new("button_up.png"))
			local down = Bitmap.new(Texture.new("button_down.png"))
			local txt = TextField.new(nil, "Question suivante.")
			local btn = Button.new(up, down, txt)
			btn:setPosition(30, 30)
			btn:addEventListener("click", 
				function()
					background:removeChild(a)
					next()
				end
			)
			a:addChild(btn)
		end
		-- Show buttons
		local vert = 60
		for i = 1, #symp do
			local up = Bitmap.new(Texture.new("long_button_up.png"))
			local down = Bitmap.new(Texture.new("long_button_down.png"))
			local txt = TextField.new(nil, symp[i][1])
			local btn = Button.new(up, down, txt)
			btn:setPosition(30, vert)
			btn:addEventListener("click", check, symp[i][1])
			q:addChild(btn)
			vert = vert + 40
		end
	end
	
	next()
	
	-- Quit Button
	
	local up = Bitmap.new(Texture.new("button_up.png"))
	local down = Bitmap.new(Texture.new("button_down.png"))
	local txt = TextField.new(nil, "Quitter le niveau")
	
	local quit = Button.new(up, down, txt)
	quit:setPosition(70, 400)
	quit:addEventListener("click", function() stage:removeChild(background) end)
	
	background:addChild(quit)
	
end