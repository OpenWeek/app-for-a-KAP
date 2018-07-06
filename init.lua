------ init.lua ------

--- Textures PreLoading

-- General textures
ButtonDown = Texture.new("images/button_down.png")
ButtonUp = Texture.new("images/button_up.png")
GrayButton = Texture.new("images/gray_button.png")
LittleButtonDown = Texture.new("images/little_button_down.png")
LittleButtonUp = Texture.new("images/little_button_up.png")
LongButtonDown = Texture.new("images/long_button_down.png")
LongButtonGray = Texture.new("images/long_button_gray.png")
LongButtonUp = Texture.new("images/long_button_up.png")
QuitButtonDown = Texture.new("images/quit_button_down.png")
QuitButtonUp = Texture.new("images/quit_button_up.png")
LeftArrow = Texture.new("images/left_arrow.png")
RightArrow = Texture.new("images/right_arrow.png")
FondNiveau = Texture.new("images/FondNiveauBlanc.png")
FondPreservatif = Texture.new('images/preservatifs.jpg')
-- Minigames textures
GameOver = Texture.new("images/gameover.png")
PlayerTxtu = Texture.new("images/player.png")
PlayerTxtu2 = Texture.new("images/penis.png")
PlayerTxtu3 = Texture.new("images/vulva.png")
Squaredodge = Texture.new("images/squaredodge.png")
--Mireille textures
Mireille1 = Texture.new("images/mireille_1.png")
Mireille2 = Texture.new("images/mireille_2.png")
Mireille3 = Texture.new("images/mireille_3.png")
Mireille4 = Texture.new("images/mireille_4.png")
Mireille5 = Texture.new("images/mireille_5.png")
Mireille6 = Texture.new("images/mireille_6.png")
Mireille7 = Texture.new("images/mireille_7.png")
Mireille8 = Texture.new("images/mireille_8.png")
Mireille9 = Texture.new("images/mireille_9.png")
TextBubble = Texture.new("images/TextBubble.png")

--- Fonts definitions

smallFont = TTFont.new("fonts/Roboto-Condensed.ttf", 10)
font = TTFont.new("fonts/Roboto-Condensed.ttf", 12)
titleFont = TTFont.new("fonts/Roboto-Condensed.ttf", 14)
bigTitleFont = TTFont.new("fonts/Roboto-Condensed.ttf", 28)

--- Constants used for element positions

MARGE =  40
TXTBUTTON_W = 90
TXTBUTTON_H = 35

--- global variables that lock and unlock continents

lock1 = 0 --lock = 0 lauch the initial animation
lock2 = 0 -- Continent 2 (and 3) is locked
lock3 = 0
lock4 = 0