function level1()

	wallLevel1 = Bitmap.new(Texture.new('FondNiveau.png'))
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
	
	local bu = Bitmap.new(Texture.new('button_up.png'))
	local bd = Bitmap.new(Texture.new('button_down.png'))
	local bt = TextField.new(nil, 'QUESTION BIATCH')
	
	local b = Button.new(bu, bd, bt)
	
	
	
	wallLevel1:addChild(back)
end
