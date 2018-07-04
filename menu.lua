
--launch the initial menu
function launchMenu()

	wallMenu = Bitmap.new(FondPreservatif)
	stage:addChild(wallMenu)
	
	local posBut1H = 50
	local posBut2H = posBut1H + 90
	local posBut3H = posBut2H + 90
	
	local mainTtl = TextField.new(bigTitleFont, "Kapotopia")
	mainTtl:setPosition(120, 30)
	wallMenu:addChild(mainTtl)
	
	local button1 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font, "Continent 1"))
	button1:addEventListener("click",
		function()
			stage:removeChild(wallMenu)
			launchMenuContinent(1,2)
		end)
	button1:setPosition(MARGE, posBut1H)
	wallMenu:addChild(button1)
	
	local txt2 = TextField.new(font, "Continent 2")
	local txt3 = TextField.new(font, "Continent 3")

	if (lock2 == 0) then -- continent 2 and 3 is locked
		local lockedButton2 = Bitmap.new(GrayButton)
		lockedButton2:setPosition(MARGE, posBut2H)
		txt2:setPosition(TXTBUTTON_W, TXTBUTTON_H)
		wallMenu:addChild(lockedButton2)
		lockedButton2:addChild(txt2)
		-----
		local lockedButton3 = Bitmap.new(GrayButton)
		lockedButton3:setPosition(MARGE, posBut3H)
		txt3:setPosition(TXTBUTTON_W, TXTBUTTON_H)
		wallMenu:addChild(lockedButton3)
		lockedButton3:addChild(txt3)
	else -- continent 2 is accessible
		local button2 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), txt2)
		button2:addEventListener("click", 
			function()
				stage:removeChild(wallMenu)
				launchMenuContinent(2,1)
			end)
		button2:setPosition(MARGE, posBut2H)
		wallMenu:addChild(button2)
		
		if (lock3 == 0) then -- continent 3 is locked
			local lockedButton3 = Bitmap.new(GrayButton)
			lockedButton3:setPosition(MARGE, posBut3H)
			txt3:setPosition(TXTBUTTON_W, TXTBUTTON_H)
			wallMenu:addChild(lockedButton3)
			lockedButton3:addChild(txt3)
		else --continent 3 is accessible
			local button3 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), txt3)
			button3:addEventListener("click", 
			function()
				stage:removeChild(wallMenu)
				launchMenuContinent(3,1)
			end)
			button3:setPosition(MARGE, posBut3H)
			wallMenu:addChild(button3)
		end
	end	
end


-- Take care of the menus of the different continents
-- Cnbr is the continent number
-- Bnbr is the number of button to set up, "go back" button not included
function launchMenuContinent(Cnbr, Bnbr)

	--print("launchMenuContinent")

	-- Creation of wallPaper
	wallContinent = Bitmap.new(FondPreservatif)
	stage:addChild(wallContinent)

	-- Creation of button 1	
	local button1
	if(Cnbr == 1) then -- Continent 1
		button1 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font,'Reconnaitre les IST'))
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level1()
		end)
	elseif(Cnbr == 2) then -- Continent 2
		button1 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font,'Pratiques à risque'))
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level3()
		end)
	elseif(Cnbr == 3) then -- Continent 3
		button1 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font,'Dépistage'))
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level4()
		end)
	end
	button1:setPosition(MARGE, 120)
	wallContinent:addChild(button1)
	
	-- Creation of second button if needed
	if(Bnbr>=2)then
		local buttonText2
		local button2
		if(Cnbr == 1) then -- In continent 1, the second button leads to the second game
			buttonText2 = TextField.new(font,'Symptomes des IST')
			if(lock1 == 1)then
				local lockedButton2 = Bitmap.new(GrayButton)
				lockedButton2:setPosition(MARGE, 210)
				buttonText2:setPosition(TXTBUTTON_W, TXTBUTTON_H)
				wallContinent:addChild(lockedButton2)
				lockedButton2:addChild(buttonText2)
			else
				button2 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), buttonText2)
				button2:addEventListener("click",
				function()
					stage:removeChild(wallContinent)
					level2()
				end)
				button2:setPosition(MARGE, 210)
				wallContinent:addChild(button2)
				
			end
		else
			-- This 'other' button is not used yet --
			local buttonText2 = TextField.new(font,'Autre')
			button2 = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), buttonText2)
			-- !! Do something (for example: statistics, previous scores...) !! --
			button2:setPosition(MARGE, 210)
			wallContinent:addChild(button2)
		end
	end
	
	createButtonMiniG(Cnbr)
	buttonMiniG:setPosition(MARGE, 300)
	wallContinent:addChild(buttonMiniG)
	
				
	--Creation of "go back" button
	local back = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonDown), TextField.new(font,'Retour'))
	back:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			launchMenu()
		end)
	back:setPosition(70,430)
	wallContinent:addChild(back)
end

-- Crée un bouton pour les minijeu avec une difficulté donné par level:int
function createButtonMiniG(level)
	buttonMiniG = Button.new(Bitmap.new(ButtonUp), Bitmap.new(ButtonUp), TextField.new(font, "Minijeu"))
	buttonMiniG:addEventListener("click",
		function()
			stage:removeChild(wallContinent)
			startMiniGame(level)
		end)
end