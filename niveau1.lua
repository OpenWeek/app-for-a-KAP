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
	
	local bu1 = Bitmap.new(LongButtonUp)
	local bd1 = Bitmap.new(LongButtonDown)
	local b1 = Button.new(bu, bd, bt)
	
	local bu2 = Bitmap.new(LongButtonUp)
	local bd2 = Bitmap.new(LongButtonDown)
	local b2 = Button.new(bu, bd, bt)
	
	local bu3 = Bitmap.new(LongButtonUp)
	local bd3 = Bitmap.new(LongButtonDown)
	local b3 = Button.new(bu, bd, bt)
	
	function readFile(file, ist, other)
		local istB = false
		local otherB = false
		for line in io.lines(file) do
			if line == "<ist>" then
				istB = true
			elseif line == "</ist>" then
				istB = false
			else
				if istB then
					if line == "<other>" then
						otherB = true
					elseif line == "</other>" then
						otherB = false
					else
						table.insert(other, line)
					end
				else
					table.insert(ist, line)
				end
			end
		end
		
	end
	
	
	wallLevel1:addChild(back)
end
