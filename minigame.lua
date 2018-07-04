-- Square Dodge by Jason Oakley (C) 2013 Blue Bilby

-- Define our variables and tables as local to save memory
local score
local hiScore
local enemyShape = {}
local capoteShape = {}
local player
local capoteLife = false
local isGameRunning
local scoreText
local hiScoreText
local MINNUMBEROFENEMIES = 7
local MAXNUMBEROFENEMIES = 101
local numberOfEnemies = MINNUMBEROFENEMIES
local enemyCountdown
local MAXCOUNTDOWN = 50
local gameoverImg, startbuttonImg
local noCollision
local index 

-- Cache math.random to make access faster
local rnd = math.random

-- Functions begin here

-- Load high score if it exists
local function loadScore()
	local file = io.open("|D|settings.txt","r")
	if not file then
		hiScore = 123
	else
		hiScore=tonumber(file:read("*line"))
		file:close()
	end
end

-- Save high score
local function saveScore()
	local file=io.open("|D|settings.txt","w+")
	file:write(hiScore .. "\n")
	file:close()
end

-- Initialise scores and text
local function initScores()
	score = 0
	scoreText = TextField.new(nil, "Score: " .. score)
	scoreText:setPosition(10,10)
	stage:addChild(scoreText)
	hiScoreText = TextField.new(nil, "Hi Score: " .. hiScore)
	hiScoreText:setPosition(200,10)
	stage:addChild(hiScoreText)
end

-- Update all scores
local function scoresUpdate()
	score = score + 1
	scoreText:setText("Score: " .. score)
	if score > 1000 then
		succes = true
	end
	if score > hiScore then
		hiScore = score
		hiScoreText:setText("Hi Score: " .. hiScore)	
	end
end

-- Play tune
local function playTune()
	local gametune = Sound.new("audio/DST-909Dreams.mp3")
	gametune:play(100,true)
end

-- Play sound effects
local function playEffect()
	local explodefx = Sound.new("audio/gameover.wav")
	explodefx:play()
end

-- Player image touched function
local function imagetouch(playerImage, event)
	if not isGameRunning then
		return
	end
	-- Is the player touching the player object?
	if playerImage:hitTestPoint(event.touch.x, event.touch.y) then
		player.x = event.touch.x-(player.width/2)
		player.y = event.touch.y-(player.height/2)
		-- Make sure they don't drag it offscreen
		if player.x > 286 then
			player.x = 286
		end
		if player.x < 0 then
			player.x = 0
		end	
		if player.y < 0 then
			player.y = 0
		end
		if player.y > 446 then
			player.y = 446
		end
		player:setPosition(player.x, player.y)
	end
end

-- Set up the player object
local function initPlayer()
	-- Create the player object
	player = Bitmap.new(Texture.new("images/player.png"))
	player.x, player.y = 160,240
	player.width, player.height = 32, 32
	player:setPosition(player.x, player.y)
	stage:addChild(player)
	-- Make it touchable
	player:addEventListener(Event.TOUCHES_MOVE, imagetouch, player)
end

-- Set up the enemies
local function initEnemies()
	
	-- Create enemy objects
		local i
		for i = 1,MAXNUMBEROFENEMIES do
		enemyShape[i] = Shape.new()
		enemyShape[i]:setLineStyle(1, 0x000066, 0.25)
		enemyShape[i]:setFillStyle(Shape.SOLID, 0xaaaaff)
		enemyShape[i]:beginPath()
		enemyShape[i]:moveTo(1,1)
		enemyShape[i]:lineTo(19,1)
		enemyShape[i]:lineTo(19,19)
		enemyShape[i]:lineTo(1,19)
		enemyShape[i]:lineTo(1,1)
		enemyShape[i]:endPath()
		enemyShape[i].width, enemyShape[i].height = 20, 20

		-- Set up enemy positions
		if i < MINNUMBEROFENEMIES then
			-- Add randomly to the screen
			local xRand, yRand = rnd(270)+20,rnd(400)+20
			-- Make sure enemies do not appear on top of player
			if xRand > 100 and xRand < 200 and yRand > 200 and yRand < 300 then
				xRand = xRand + 100
				yRand = yRand + 100
			end
			enemyShape[i].x, enemyShape[i].y = xRand, yRand
		else
			-- Hide extra enemies offscreen until needed
			enemyShape[i].x, enemyShape[i].y = -100,-100
		end
		enemyShape[i]:setPosition(enemyShape[i].x,enemyShape[i].y)
		stage:addChild(enemyShape[i])
		
		-- Pick a direction for the enemies to move
		local xdir, ydir = rnd(2)-1, rnd(2)-1
		if xdir == 0 then
			xdir = -1
		end
		if ydir == 0 then
			ydir = -1
		end
		enemyShape[i].xdir, enemyShape[i].ydir = xdir, ydir
	end
	enemyCountdown=MAXCOUNTDOWN
end

-- Set up the enemies
local function initCapote()
	-- Create enemy objects
		local i = 1
		capoteShape[i] = Shape.new()
		capoteShape[i]:setLineStyle(1, 0x000000, 0.25)
		capoteShape[i]:setFillStyle(Shape.SOLID, 0x000000)
		capoteShape[i]:beginPath()
		capoteShape[i]:moveTo(1,1)
		capoteShape[i]:lineTo(19,1)
		capoteShape[i]:lineTo(19,19)
		capoteShape[i]:lineTo(1,19)
		capoteShape[i]:lineTo(1,1)
		capoteShape[i]:endPath()
		capoteShape[i].width, capoteShape[i].height = 20, 20
		-- Add randomly to the screen
		local xRand, yRand = rnd(270)+20,rnd(400)+20
		-- Make sure enemies do not appear on top of player
		if xRand > 100 and xRand < 200 and yRand > 200 and yRand < 300 then
			xRand = xRand + 100
			yRand = yRand + 100
		end
		capoteShape[i].x, capoteShape[i].y = xRand, yRand
		capoteShape[i]:setPosition(capoteShape[i].x,capoteShape[i].y)
		stage:addChild(capoteShape[i])
		
		-- Pick a direction for the enemies to move
		local xdir, ydir = rnd(2)-1, rnd(2)-1
		if xdir == 0 then
			xdir = -1
		end
		if ydir == 0 then
			ydir = -1
		end
		capoteShape[i].xdir, capoteShape[i].ydir = xdir, ydir
end

-- Spawn a new enemy
local function spawnEnemy()
	local xRand, yRand = rnd(250)+20,rnd(400)+20
	-- Make sure enemies do not appear on top of player
	if xRand > player.x-40 and xRand < player.x+40 and yRand > player.y-40 and yRand < player.y+40 then
		xRand = xRand + 100
		yRand = yRand + 100
		if xRand > 270 then xRand = xRand - 200 end
		if yRand > 420 then yRand = yRand - 200 end
	end	
	numberOfEnemies = numberOfEnemies + 1
	enemyShape[numberOfEnemies].x, enemyShape[numberOfEnemies].y = xRand, yRand
	enemyShape[numberOfEnemies]:setPosition(enemyShape[numberOfEnemies].x,enemyShape[numberOfEnemies].y)
	enemyCountdown=MAXCOUNTDOWN
end

-- Update the enemies
local function enemiesUpdate()
	local i
	for i=1,numberOfEnemies do
		enemyShape[i].x=enemyShape[i].x+enemyShape[i].xdir
		enemyShape[i].y=enemyShape[i].y+enemyShape[i].ydir
	
		-- Check if we hit a wall
		if enemyShape[i].x < 1 or enemyShape[i].x > 299 then
			enemyShape[i].xdir = -enemyShape[i].xdir
		end
		if enemyShape[i].y < 1 or enemyShape[i].y > 459 then
			enemyShape[i].ydir = -enemyShape[i].ydir
		end
		enemyShape[i]:setPosition(enemyShape[i].x,enemyShape[i].y)
	end
		capoteShape[1].x=capoteShape[1].x+capoteShape[1].xdir
		capoteShape[1].y=capoteShape[1].y+capoteShape[1].ydir
	
		-- Check if we hit a wall
		if capoteShape[1].x < 1 or capoteShape[1].x > 299 then
			capoteShape[1].xdir = -capoteShape[1].xdir
		end
		if capoteShape[1].y < 1 or capoteShape[1].y > 459 then
			capoteShape[1].ydir = -capoteShape[1].ydir
		end
		capoteShape[1]:setPosition(capoteShape[1].x,capoteShape[1].y)
		
		-- Simple way of doing a timer before spawning more enemies
		enemyCountdown = enemyCountdown - 1
		if enemyCountdown == 0 then
			if numberOfEnemies < MAXNUMBEROFENEMIES then
				spawnEnemy()
			end
		end	
end

-- Simple collision test
function collisionTest(rectA, rectB)
	local collided = false	 
	local x1,y1,w1,h1,x2,y2,w2,h2 = rectA.x, rectA.y, rectA.width, rectA.height, rectB.x, rectB.y, rectB.width, rectB.height
	if (y2 >= y1 and y1 + h1 >= y2) or (y2 + h2 >= y1 and y1 + h1 >= y2 + h2) or (y1 >= y2 and y2 + h2 >= y1) or (y1 + h1 >= y2 and y2 + h2 >= y1 + h1) then
		if x2 >= x1 and x1 + w1 >= x2 then
			collided = true
		end
		if x2 + w2 >= x1 and x1 + w1 >= x2 + w2 then
			collided = true 
		end
		if x1 >= x2 and x2 + w2 >= x1 then
			collided = true  
		end
		if x1 + w1 >= x2 and x2 + w2 >= x1 + w1 then
			collided = true
		end	 
	end
	return collided
end

-- Initialise the game
local function initGame()
	isGameRunning = true
	initEnemies()
	initCapote()
	initPlayer()
	loadScore()
	initScores()
end

-- Start button touch handler
local function startTouch(startbuttonImage, event)
	-- See if the Start Button object was touched
	if startbuttonImage:hitTestPoint(event.touch.x, event.touch.y) then
		startbuttonImage:removeEventListener(Event.TOUCHES_END, startTouch)
		-- Clean up our objects
		stage:removeChild(startbuttonImage)
		startbuttonImage=nil
		initGame()
	end
end

-- Start game. Display START button and logo
local function startGame()
	-- Create a Start Button object and display it
	startbuttonImg = Bitmap.new(Texture.new("images/squaredodge.png"))
	startbuttonImg.x, startbuttonImg.y = 0,200
	startbuttonImg:setPosition(startbuttonImg.x, startbuttonImg.y)
	stage:addChild(startbuttonImg)
	-- Make the Start Button object touchable
	startbuttonImg:addEventListener(Event.TOUCHES_BEGIN, startTouch, startbuttonImg)
end

local function goTouch(gameOverImage, event)
	-- See if the Game Over object was touched
	if gameOverImage:hitTestPoint(event.touch.x, event.touch.y) then
		gameoverImg:removeEventListener(Event.TOUCHES_END, goTouch)
		-- Clean up our objects
		stage:removeChild(gameoverImg)
		gameoverImg=nil
		local i
		for i = 1,MAXNUMBEROFENEMIES do
			if enemySchape[i]~=nil then
				stage:removeChild(enemyShape[i])
				enemyShape[i]=nil
			end
		end
		stage:removeChild(player)
		player=nil
		stage:removeChild(hiScoreText)
		hiScoreText=nil
		stage:removeChild(scoreText)
		scoreText=nil
		-- Restart the game
		launchMenu(sucess)
	end	
end

-- Game over handling
local function gameOver()
	-- Save the current hiScore
	saveScore()
	-- Play explosion
	playEffect()
	-- Remove the listener from the player object
	player:removeEventListener(Event.TOUCHES_MOVE, imagetouch)
	-- Create a Game Over object and display it
	gameoverImg = Bitmap.new(Texture.new("images/gameover.png"))
	gameoverImg.x, gameoverImg.y = 0,200
	gameoverImg:setPosition(gameoverImg.x, gameoverImg.y)
	stage:addChild(gameoverImg)
	-- Make the Game Over object touchable
	gameoverImg:addEventListener(Event.TOUCHES_BEGIN, goTouch, gameoverImg)
end

-- See if any collisions occurred
local function checkCollisions()

	for i=1,numberOfEnemies do
		if collisionTest (player, enemyShape[i]) == true then
			if capoteLife == false and noCollision == false then
				isGameRunning = false
				gameOver()
				return
			end
			capoteLife = false
		end
		if collisionTest (player, enemyShape[i]) == false then
			noCollision = false
		end
	end
		if collisionTest (player, capoteShape[1]) == true then
			score = score + 10
		end
end

-- Update everything
local function updateAll()
	-- Only update if the game is still going
	if not isGameRunning then
		return
	end
	scoresUpdate()	
	enemiesUpdate()
	checkCollisions()
	
end

function setLevel(level)

	MAXCOUNTDOWN = 500 - level*100
end

function startMiniGame()
	playTune()
	startGame()
	stage:addEventListener(Event.ENTER_FRAME, updateAll)
end
startMiniGame()
