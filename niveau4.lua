function level4()

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
			launchMenuContinent(3,1)
		end)	
	-- 0 pour non, 1 pour oui
	-- Question, réponse, explication
	-- Il faudrait que ces questions soient lues depuis un fichier
	local questions = {}
function lines_from()
    lines = {}
    for line in io.lines("questions.txt") do
		lines[#lines + 1] = line
		if #lines == 5 then
			questions[#questions+1] = lines
			lines = {}
        end
    end
	return lines
end

	local upLO = Bitmap.new(Texture.new("little_button_up.png"))
	local downLO = Bitmap.new(Texture.new("little_button_down.png"))
	local upLN = Bitmap.new(Texture.new("little_button_up.png"))
	local downLN = Bitmap.new(Texture.new("little_button_down.png"))
	local upOK = Bitmap.new(Texture.new("little_button_up.png"))
	local downOK = Bitmap.new(Texture.new("little_button_down.png"))
	local txtO = TextField.new(nil, "OUI")
	local txtN = TextField.new(nil, "NON")
	local txtOK = TextField.new(nil, "OK")
	
	local buttonO = Button.new(upLO, downLO, txtO)
	local buttonN = Button.new(upLN, downLN, txtN)
	local buttonOK = Button.new(upOK, downOK, txtOK)
	txtOK:setText("hihihi")
	
	buttonO:setPosition(40, 250)
	buttonN:setPosition(200, 250)
	buttonOK:setPosition(200, 250)
	numQ = 1
	
	function printQuestion()
		q = questions[numQ]
		quest = TextField.new(nil, q[1])
		quest:setPosition(10, 150)
		fond:addChild(quest)
		fond:addChild(buttonO)
		fond:addChild(buttonN)
		txtN:setText(q[4])
		txtO:setText(q[5])
	end
	
	function check(answer) 
		if tonumber(q[2]) == answer then
			txtA = "VRAI : " .. q[3]
			ans = TextField.new(nil, txtA)
		else
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
			stage:removeChild(fond)
		else
			printQuestion()
		end
	end
	
	lines_from()
	printQuestion()
	buttonO:addEventListener("click", check, 1)
	buttonN:addEventListener("click", check, 0)
	buttonOK:addEventListener("click", next)	
end
