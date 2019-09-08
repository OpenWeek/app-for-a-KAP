# Kapotopia - Changelog
## Alpha-0.2.5 - code: 8
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
  
## Alpha-0.2.4 - code: 7
- Upgraded AssetsManager : now manage stages and music (=/= sound)
- Reset MockupG1 pictures when the screen is hidden (game change of main screen)
- Upgraded Screen Management : more sophisticated system to change screen (function signature dont change) and allow to reset a screen (usefull for back button on ressourcefull screens)
- Minor change in labels positions, more user-friendly
- added a version label on MainMenu

## Alpha-0.2.3 - code: 6
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

## Alpha-0.2.2 - code: 5
- Game1 - Beta ready
  - Lifes and scores change correctly. µ
    - If the player touch a fake ist, one life is removed and the score is lowered by -15. The players has it's position resetted.
    - If the player touch an ist, the score is raised by +10
  - Increased difficulty. Virus now gains acceleration with time passing
  - Increased maintenability by using final variables instead of Hardcoded strings
  - All viruses textures are loaded in memory, thus lowering loading time during the game, and thus reducing lag.
- AssetsManager, small improvement and maintenability
- Fixing bug in the function giving a TextButtonStyle which wasn't changing the style size.

## Alpha-0.2.1 - code: 4
- Game1 enhancement
  - Improved Balancing
  - Added a fourth tunnel
  - Hardcoded string paths aren't used anymore for assets ressources. They are now contained in a XML ressource called ```sprite.xml```.
- New Screen World1 to access easily Game1 and Game2
- Assets improvement
  - A single component called the AssetsManager is used to load sounds and texture once and keep them in memory, thus reducing memory loading times and leaks
  - Various sounds have been added thorough the whole app. Exemple, menu buttons sounds when pressed and victory sounds.

## Alpha-0.2 - code: 3
- Game1 playable demo
  - A life counter give the number of tries. At 0, the player loose
  - Collision manager between Mireille and the viruses
- Assets added
  - Two musics composed
  - Ists and fake ists draws

## Alpha-0.1 - code: 2
- Add of a Main menu
- Game1
  - Mockup for introduction
  - Basic Game
- Mockup for Game2
- Game 3 - Version 1 complete
- Game 4 - Basic Version
- Main assets about background, mockups and players added
