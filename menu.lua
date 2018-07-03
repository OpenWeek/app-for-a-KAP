--launch le menu initial
function launchMenu()

	print("launchMenu")

	wallMenu = Bitmap.new(Texture.new('preservatifs.jpg'))
	stage:addChild(wallMenu)
	
	local txt1 = TextField.new(nil, "Continent 1")
	local txt2 = TextField.new(nil, "Continent 2")
	local txt3 = TextField.new(nil, "Continent 3")
	
	local up1 = Bitmap.new(Texture.new("button_up.png"))
	local down1 = Bitmap.new(Texture.new("button_down.png"))	
	local up2 = Bitmap.new(Texture.new("button_up.png"))
	local down2 = Bitmap.new(Texture.new("button_down.png"))
	local up3 = Bitmap.new(Texture.new("button_up.png"))
	local down3 = Bitmap.new(Texture.new("button_down.png"))
	
	local button1 = Button.new(up1, down1, txt1)
	local button2 = Button.new(up2, down2, txt2)
	local button3 = Button.new(up3, down3, txt3)
	
	button1:addEventListener("click",
		function()
			stage:removeChild(wallMenu)
			launchMenuContinent(1,2)
		end)
	button2:addEventListener("click", 
		function()
			stage:removeChild(wallMenu)
			launchMenuContinent(2,1)
		end)	
	button3:addEventListener("click", 
		function()
			stage:removeChild(wallMenu)
			launchMenuContinent(3,1)
		end)
	
	button1:setPosition(40, 30)
	button2:setPosition(40, 120)
	button3:setPosition(40, 210)
	
	wallMenu:addChild(button1)
	wallMenu:addChild(button2)
	wallMenu:addChild(button3)
	
end


--Gère les différents menus
--Cnbr est le numéro du continent et Bnbr le nombre de bouton à afficher en plus du bouton retour

function launchMenuContinent(Cnbr, Bnbr)

	print("launchMenuContinent")

	-- Création fond d'écran
	wallContinent = Bitmap.new(Texture.new('preservatifs.jpg'))
	stage:addChild(wallContinent)

	-- Création bouton 1
	local buttonUp1 = Bitmap.new(Texture.new("button_up.png"))
	local buttonDown1 = Bitmap.new(Texture.new("button_down.png"))	
	local buttonText1
	local button1
	if(Cnbr == 1) then
		buttonText1 = TextField.new(nil,'Reconnaitre les IST')
		button1 = Button.new(buttonUp1, buttonDown1, buttonText1)
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level1()
		end)
	elseif(Cnbr == 2) then
		buttonText1 = TextField.new(nil,'Pratiques à risque')
		button1 = Button.new(buttonUp1, buttonDown1, buttonText1)
		button1:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			level3()
		end)
	elseif(Cnbr == 3) then
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

	
		
	if(Bnbr>=2)then
		local buttonUp2 = Bitmap.new(Texture.new("button_up.png"))
		local buttonDown2 = Bitmap.new(Texture.new("button_down.png"))
		local buttonText2
		local button2
		if(Cnbr == 1) then
			buttonText2 = TextField.new(nil,'Symptomes des IST')
			button2 = Button.new(buttonUp2, buttonDown2, buttonText2)
			button2:addEventListener("click",
				function()
					stage:removeChild(wallContinent)
					level2()
			end)
		else
			-- Ce bouton "autre" n'est pas encore utilisé --
			buttonText2 = TextField.new(nil,'Autre')
			local button2 = Button.new(buttonUp2, buttonDown2, buttonText2)
			-- !! Faire action !! --
		end
		button2:setPosition(40, 200)
		wallContinent:addChild(button2)
	end
	
	--Création bouton retour
	local backUp = Bitmap.new(Texture.new("button_up.png"))
	local backDown = Bitmap.new(Texture.new("button_down.png"))
	local backText = TextField.new(nil,'Retour')	
	local back = Button.new(backUp, backDown, backText)
	back:addEventListener("click", 
		function()
			stage:removeChild(wallContinent)
			launchMenu()
		end)
	wallContinent:addChild(back)

end