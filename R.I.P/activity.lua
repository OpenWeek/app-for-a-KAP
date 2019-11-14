--[[
	Class to help creation of new activities
]]

Activity = Core.class()

function Activity:init(texture_background)
	self.wall = Bitmap.new(texture_background)
	stage:addChild(self.wall)
end

function Activity:addTitle(txt, positionW, positionH)
	txt:setPosition(positionW, positionH)
	self.wall:addChild(txt)
end

function Activity:addButton(button, positionW, positionH, funListener, optListener)
	button:addEventListener("click", funListener, optListener)
	button:setPosition(positionW, positionH)
	self.wall:addChild(button)
end

function Activity:addMovieClip(movieClip)
	self.wall:addChild(movieClip)
end

function Activity:addChild(item)
	self.wall:addChild(item)
end

function Activity:removeChild(item)
	self.wall:removeChild(item)
end

function Activity:removeWall()
	stage:removeChild(self.wall)
end

function Activity:getWall()
	return self.wall
end