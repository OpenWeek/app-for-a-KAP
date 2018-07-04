function level1()

	wallLevel1 = Bitmap.new(FondPreservatif)
	stage:addChild(wallLevel1)

	local backUp = Bitmap.new(ButtonUp)
	local backDown = Bitmap.new(ButtonDown)
	local backText = TextField.new(font,'Level 1 - Retour')	
	local back = Button.new(backUp, backDown, backText)
	back:addEventListener("click", 
		function()
			stage:removeChild(wallLevel1)
			launchMenuContinent(1,2)
		end)
	
	wallLevel1:addChild(back)
end
