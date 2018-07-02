function launchMenu()

	local label1 = TextField.new(nil, "Niveau non fini")
	local label2 = TextField.new(nil, "Niveau non fini")
	local label3 = TextField.new(nil, "Niveau non fini")
	local label4 = TextField.new(nil, "Niveau non fini")
	
	local txt1 = TextField.new(nil, "Niveau 1")
	local txt2 = TextField.new(nil, "Niveau 2")
	local txt3 = TextField.new(nil, "Niveau 3")
	local txt4 = TextField.new(nil, "Niveau 4")
	
	label1:setPosition(122,105)
	stage:addChild(label1)
	label2:setPosition(122,195)
	stage:addChild(label2)
	label3:setPosition(122,285)
	stage:addChild(label3)
	label4:setPosition(122,375)
	stage:addChild(label4)
	
	local up1 = Bitmap.new(Texture.new("button_up.png"))
	local down1 = Bitmap.new(Texture.new("button_down.png"))	
	local up2 = Bitmap.new(Texture.new("button_up.png"))
	local down2 = Bitmap.new(Texture.new("button_down.png"))
	local up3 = Bitmap.new(Texture.new("button_up.png"))
	local down3 = Bitmap.new(Texture.new("button_down.png"))
	local up4 = Bitmap.new(Texture.new("button_up.png"))
	local down4 = Bitmap.new(Texture.new("button_down.png"))
	
	local button1 = Button.new(up1, down1, txt1)
	local button2 = Button.new(up2, down2, txt2)
	local button3 = Button.new(up3, down3, txt3)
	local button4 = Button.new(up4, down4, txt4)
	
	button1:addEventListener("click", 
		level1)
	button2:addEventListener("click", 
		level2)	
	button3:addEventListener("click", 
		level3)
	button4:addEventListener("click", 
		level4)
	
	button1:setPosition(40, 30)
	button2:setPosition(40, 120)
	button3:setPosition(40, 210)
	button4:setPosition(40, 300)
	
	stage:addChild(button1)
	stage:addChild(button2)
	stage:addChild(button3)
	stage:addChild(button4)

end
