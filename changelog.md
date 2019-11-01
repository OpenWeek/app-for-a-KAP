# Kapotopia - Changelog

## Alpha-0.3 - code: 10 - 2019/11/01
- General
  - Gradle version has been downgraded due to incompatibility problems with some computers of the devs. We now use v3.3.2 instead of v3.4.2. Thus gradle scripts also have been rewritten to met old standards.
  - Added a few helper classes for devs, like a LabelBuilder or a ImageHelper
  - New packages has been created to group together classes with the same functionnality
- Game1
  General Refractor of the code
  - Aesthetic
    - New background and new STI's and fake STI's
    - New background for the introduction of the game, without any text yet
    - Font has been changed to the COMMS font and changed from black to white, for clarity
    - A sound is now played when the player touch a STI
    - A small "missed" text appears when a true STI is missed by the player
    - Enemi labels has been (somewhat) aligned with the picture
  - Gameplay
    - Added a pause Icon to manually pause the game
    - Difficulty
      - More STI's and fake STI's have been added. The number of each stay equivalent so the difficulty stay balanced.
      - Added a difficulty choosing screen. The player can now choose which difficulty he want to play the game. The screen allowing the player to choose the difficulty has been standardized and therefore can be reused in other parts of the game
      - Overall difficulty has been increased
    - New label that shows how much STI's has been catched
    - Improved the learning part (#22) by adding a short description of the STI's not catched (shown on the bilan screen)
  - Bug fixing
    - Fixed a bug in #20 referenced issue, causing the game to not scale to certain screen configurations
    - Fixed the failed sound not playing (that plays when the player miss a STI) when the screen is shown for the first time. It was caused by the sound not properly loaded into memory to instantly play it when the screen appears.
    - Fixed a not-yet-existing-bug that could have been caused by the width and height of Virus not correctly set. We don't draw them the size they are, they are scalled (by two factors). The resulting width and height however wasn't saved, which could have lead to bugs in the future.
    - Fixed a collision problem with viruses
- Game2
  - Behind the scenes mockup mechanics has been reworked to use CinematicScreens
- Game3
  - The design has been totally reworked with the new textures.
  - New types of pipes has been added, giving more gameplay possibilities
- Game4 (W4)
  A basic demo is available
- World3
  Until now it was crashing (because it served as test base in the past and was never updated). I just added a WIP (Work In Progress) text and a back button. It doesnt crash anymore when are coming from main menu now
- Graphisms
  - Textures
    - STI's texture widths and heights has been standardized (they had different size before)
    - More STI's and fake STI's textures !
    - A new button has been made for G1, made of leaves
    - A pause Icon has also been added
    - A basketball has been added for G2
    - Pipes for G3 has been added, with a normal version and a electrified version, as well as lock
    - A temporary as well as a Work In Progress app icons has been added
  - Backgrounds
    - Game 1 now have a jungle background. It is separated in three files. One has the back and trees picture, another one has the leaves on the first plan, and the last file has both.
    - Game 1 introduction now has pictures with the characters involved, plus space to insert dialogs
    - Game 2 now has a basketball hoop
    - Game 3 has a new background
  - Font
    A new font (with a free license) has been added, which is a clearer font than the classic one. It comes with different types, sansserif, bold, italic, ...


## Alpha-0.2.6 - code: 9 - 2019/09/13
- Game1
  - Small timebreak before starting game1 again after the game has been resumed
  - Change fakeist name from *flemme* to *Mononucléose*
  - Scalling bug fix #20
  - Simplified virus labels
- General
  - Added a swipe gesture helping class
  - Added an helping class to easily makes cinematics (and mockups)
  - Refractor of mockupG1 using the new framework
- Documentation
  - Added **CONTRIBUTING.md**
  - Added changelog file *this one*


## Alpha-0.2.5 - code: 8 - 2019/09/05
- Proposition fix for Issue #21 *Live problem*
  - Fixed Mireille sometimes not losing a life after she just has lost a life.
  - Mireille doesnt randomly instantly die anymore
  - The same ennemy doesnt always come next after a non-ist disease has touched the player.

  Both problems were caused by collision bounds which weren't properly upgraded in certain occasions
- Proposition fix for Issue #22 *Improving the learning part*
  - Monsieur Sida name has been changed monsieur VIH to better reflect the real virus, as sida is only a stage of the disease.
  - When players misses Ists, they are displayed in a list after the end of the game, to improve the learning part of the game.
- Documentation
  - Documentation files has been moved to ```/docs``` folder
  - **README.md** has been updated since it wasn't up to date since one year ago.
  - Authors has been properly set since the dev and assets teams has been changed since July 2018

## Alpha-0.2.4 - code: 7 - 2019/07/28
- Upgraded AssetsManager : now manage stages and music (=/= sound)
- Reset MockupG1 pictures when the screen is hidden (game change of main screen)
- Upgraded Screen Management : more sophisticated system to change screen (function signature dont change) and allow to reset a screen (usefull for back button on ressourcefull screens)
- Minor change in labels positions, more user-friendly
- added a version label on MainMenu

## Alpha-0.2.3 - code: 6 - 2019/07/27
- Improved naviguation between screens
  - Screen changing is now efficiently handled, correcting memory leaks and bad designs faults.
  - Back Button is mostly working now (unless screens didn't implement it's behavior), it doesn't crash the game anymore.
  - Coming in and out of Mainmenu corrected.
- Assets
  - Renamed and shrinked all sound files (and changed their references in-code)
- Build options
  - Upgraded gradle version from 3.4.1 -> 3.4.2
  - Updated build.gradle with newest commands, basically switched from compile -> implementation
- Small memoryleak fix in StandardInputAdapter

## Alpha-0.2.2 - code: 5 - 2019/06/13
- Game1 - Beta ready
  - Lifes and scores change correctly.
    - If the player touch a fake ist, one life is removed and the score is lowered by -15. The players has it's position resetted.
    - If the player touch an ist, the score is raised by +10
  - Increased difficulty. Virus now gains acceleration with time passing
  - Increased maintenability by using final variables instead of Hardcoded strings
  - All viruses textures are loaded in memory, thus lowering loading time during the game, and thus reducing lag.
- AssetsManager, small improvement and maintenability
- Fixing bug in the function giving a TextButtonStyle which wasn't changing the style size.

## Alpha-0.2.1 - code: 4 - 2019/06/12
- Game1 enhancement
  - Improved Balancing
  - Added a fourth tunnel
  - Hardcoded string paths aren't used anymore for assets ressources. They are now contained in a XML ressource called ```sprite.xml```.
- New Screen World1 to access easily Game1 and Game2
- Assets improvement
  - A single component called the AssetsManager is used to load sounds and texture once and keep them in memory, thus reducing memory loading times and leaks
  - Various sounds have been added thorough the whole app. Exemple, menu buttons sounds when pressed and victory sounds.

## Alpha-0.2 - code: 3 - 2019/05/18
- Game1 playable demo
  - A life counter give the number of tries. At 0, the player loose
  - Collision manager between Mireille and the viruses
- Assets added
  - Two musics composed
  - Ists and fake ists draws

## Alpha-0.1 - code: 2 - 2019/05/15
- Add of a Main menu
- Game1
  - Mockup for introduction
  - Basic Game
- Mockup for Game2
- Game 3 - Version 1 complete
- Game 4 - Basic Version
- Main assets about background, mockups and players added
