
--launch the initial menu
function launchMenu()

	--print("launchMenu")

	wallMenu = Bitmap.new(Texture.new('preservatifs.jpg'))
	stage:addChild(wallMenu)
	
	local txt1 = TextField.new(nil, "Continent 1")
	local txt2 = TextField.new(nil, "Continent 2")
	local txt3 = TextField.new(nil, "Continent 3")
	
	local up1 = Bitmap.new(Texture.new("button_up.png"))
	local down1 = Bitmap.new(Texture.new("button_down.png"))
	local button1 = Button.new(up1, down1, txt1)
	button1:addEventListener("click",
		function()
			stage:removeChild(wallMenu)
			launchMenuContinent(1,2)
		end)
	button1:setPosition(40, 30)
	wallMenu:addChild(button1)

	if(lock2 == 0)then -- continent 2 and 3 is locked
		local lockedButton2 = Bitmap.new(Texture.new("gray_button.png"))
		lockedButton2:setPosition(40, 120)
		txt2:setPosition(70, 40)
		wallMenu:addChild(lockedButton2)
		lockedButton2:addChild(txt2)
		local lockedButton3 = Bitmap.new(Texture.new("gray_button.png"))
		lockedButton3:setPosition(40, 210)
		txt3:setPosition(70, 40)
		wallMenu:addChild(lockedButton3)
		lockedButton3:addChild(txt3)
	else --continent 2 is accessible
		local up2 = Bitmap.new(Texture.new("button_up.png"))
		local down2 = Bitmap.new(Texture.new("button_down.png"))
		local button2 = Button.new(up2, down2, txt2)
		button2:addEventListener("click", 
		function()
			stage:removeChild(wallMenu)
			launchMenuContinent(2,1)
		end)
			button2:setPosition(40, 120)
			wallMenu:addChild(button2)
		if(lock3 == 0)then --continent 3 is locked
			local lockedButton3 = Bitmap.new(Texture.new("gray_button.png"))
			lockedButton3:setPosition(40, 210)
			txt3:setPosition(70, 40)
			wallMenu:addChild(lockedButton3)
			lockedButton3:addChild(txt3)
		else --continent 3 is accessible
			local up3 = Bitmap.new(Texture.new("button_up.png"))
			local down3 = Bitmap.new(Texture.new("button_down.png"))
			local button3 = Button.new(up3, down3, txt3)
			button3:addEventListener("click", 
			function()
				stage:removeChild(wallMenu)
				launchMenuContinent(3,1)
			end)
			button3:setPosition(40, 210)
			wallMenu:addChild(button3)
		end
	end	
end


--Take care of the menus of the different continents
--Cnbr is the continent number
-- Bnbr is the number of button to set up, "go back" button not included
function launchMenuContinent(Cnbr, Bnbr)

	--print("launchMenuContinent")

	-- Creation of wallPaper
	wallContinent = Bitmap.new(Texture.new('preservatifs.jpg'))
	stage:addChild(wallContinent)

	-- Creation of button 1
	local buttonUp1 = Bitmap.new(Texture.new("button_up.png"))
	local buttonDown1 = Bitmap.new(Texture.new("button_down.png"))	
	local buttonText1
	local button1
	if(Cnbr == 1) then -- Continent 1
		buttonText1 = TextField.new(nil,'Reconnaitre les IST')
		button1 = Button.new(buttonUp1, buttonDown1, buttonText1)
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level1()
		end)
	elseif(Cnbr == 2) then -- Continent 2
		buttonText1 = TextField.new(nil,'Pratiques à risque')
		button1 = Button.new(buttonUp1, buttonDown1, buttonText1)
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level3()
		end)
	elseif(Cnbr == 3) then -- Continent 3
		buttonText1 = TextField.new(nil,'Dépistage')
		button1 = Button.new(buttonUp1, buttonDown1, buttonText1)
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level4()
		end)
	end
	button1:setPosition(40, 120)
	wallContinent:addChild(button1)
	
	-- Creation of second button if needed
	if(Bnbr>=2)then
		local buttonText2
		local button2
		if(Cnbr == 1) then -- In continent 1, the second button leads to the second game
			buttonText2 = TextField.new(nil,'Symptomes des IST')
			if(lock1 == 1)then
				local lockedButton2 = Bitmap.new(Texture.new("gray_button.png"))
				lockedButton2:setPosition(40, 210)
				buttonText2:setPosition(70, 40)
				wallContinent:addChild(lockedButton2)
				lockedButton2:addChild(buttonText2)
			else
				local buttonUp2 = Bitmap.new(Texture.new("button_up.png"))
				local buttonDown2 = Bitmap.new(Texture.new("button_down.png"))
				button2 = Button.new(buttonUp2, buttonDown2, buttonText2)
				button2:addEventListener("click",
				function()
					stage:removeChild(wallContinent)
					level2()
				end)
				button2:setPosition(40, 210)
				wallContinent:addChild(button2)
			end
			
		else
			-- This 'other' button is not used yet --
			buttonText2 = TextField.new(nil,'Autre')
			local buttonUp2 = Bitmap.new(Texture.new("button_up.png"))
			local buttonDown2 = Bitmap.new(Texture.new("button_down.png"))
			button2 = Button.new(buttonUp2, buttonDown2, buttonText2)
			-- !! Do something (for example: statistics, previous scores...) !! --
			button2:setPosition(40, 210)
			wallContinent:addChild(button2)
		end
	end
	
	--Creation of "go back" button
	local backUp = Bitmap.new(Texture.new("button_up.png"))
	local backDown = Bitmap.new(Texture.new("button_down.png"))
	local backText = TextField.new(nil,'Retour')
	local back = Button.new(backUp, backDown, backText)
	back:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			launchMenu()
		end)
	back:setPosition(70,430)
	wallContinent:addChild(back)
end