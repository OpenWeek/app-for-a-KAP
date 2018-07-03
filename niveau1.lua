function level1()

	wallLevel1 = Bitmap.new(Texture.new('preservatifs.jpg'))
	stage:addChild(wallLevel1)

	local backUp = Bitmap.new(Texture.new("button_up.png"))
	local backDown = Bitmap.new(Texture.new("button_down.png"))
	local backText = TextField.new(nil,'Level 1 - Retour')	
	local back = Button.new(backUp, backDown, backText)
	back:addEventListener("click", 
		function()
			stage:removeChild(wallLevel1)
			launchMenuContinent(1,2)
		end)
	
	wallLevel1:addChild(back)
end
