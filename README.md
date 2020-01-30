[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](code_of_conduct.md)
# Kapotopia

This repository contains all the code and assets concerning the upcoming opensource mobile game "*Kapotopia*", which goal is to educate on the subject of prevention of Sexual Transmissible Infections (STI's) using interactive games.

The opensource game "*Kapotopia*" consists of several minigames and a searchable diseases library about the topic of prevention of Sexual Transmissible Infections. The minigames aims to inform the player around various topics such as identifying STI's, safe practices, STI's symptoms and mutual consents among other subjects. It is developped with *Java* using the opensource library [LibGDX](https://github.com/libgdx/libgdx), a low-level library made for multiplatform games. Our goal is to publish the app on both Android and IOS platforms even if only the Android branch is actively developped for now.

### Features

- Minigame about identifying correct STI's
- Minigame about linking the symptoms to the right STI's      (in progress)
- Minigame about safe practices                               (in progress)
- Summary Quiz for the precedent minigames                    (planned)
- Minigame about mutual consent                               (planned)
- STI's library containing diseases informations              (planned)

More info of the roadmap can be found on the dedicated [wiki page](https://github.com/OpenWeek/app-for-a-KAP/wiki/roadmap).

### Requirements

- Android 9 (Pie) - SDK
- An IDE you are comfortable with (we recommend AndroidStudio/IntelliJ for simplicity)
- Enough disk space, the project takes around ~ 2 Go with all the Assets

### Contributing

We are currently an active team of 6 persons working on the project, but outside contributions are also *very* appreciated. If you are interested into helping us, or have any questions, don't hesitate to shoot us a message in the [issues section](https://github.com/OpenWeek/app-for-a-KAP/issues/new), we will gladly answer your questions ! More details on how to contribute to the project can also be found in the [```CONTRIBUTING.md```](docs/CONTRIBUTING.md) file.

The app has not yet reached an alpha state, we'd say only roughly 25% of the game is made for it's first version. We (mainteners) are students and are new to the world of Android apps, LibGDX and opensource projects in general. Please bear with us if we make noob mistakes, we are still learning :pray:

[A small wiki](https://github.com/OpenWeek/app-for-a-KAP/wiki) exist on the github project page, you can help us expand it !
We currently dont have a place to host the API Javadoc of the app, but if you use AndroidStudio/IntelliJ it is very simple to generate it yourself ! Go on ```Tools->Generate Javadoc```. However we'll ask you to not push the resulting doc on the repository, simply to not polluate the commits and generating non necessary merge conflicts since it will be prone to frequent changes.

### Translation

While the project documentation and code is written in English, the app language is actually written in French. The reason is that the primary goal is to deploy the app for the students of UCLouvain, who are primarly French speakers, in a first time. English and Dutch translations may follow in the future. If you are interested into translating the app, please open up a new issue ! We aren't professional translaters, so we'll take any help offered.

## Structure

The folder ```kapotopia``` contains the new app coded in *Java*.

The project is divided into three sections :
* core
* android
* ios

The **core** folder contains the main game logic, the **android** and **ios** folders contains the assets and files to make it work on both OS. Currently, only the android part is working, the ios porting will be done after the main game is finished.

Here is a more detailled folders tree from the project root :

- kapotopia/
  - core
    - src
      - gdx/kapotopia$
        - Animations
        - AssetsManaging
        - DataStructures
        - DialogsScreen
        - Game1
        - Game2
        - Game3
        - Game4
        - Helpers
          - Builders
          - ...
        - Screens
        - ... (various .java files)
    - build.gradle
  - android
    - assets
      - defaultSkin
      - EcranMenu
      - fonts
      - game3
      - icons
      - ImagesGadgets
      - IST
      - MireilleImages
      - skins
        - comic
      - sound
        - bruitage
      - strings
      - Symbole du jeu
      - World1
        - Game1
        - Game2
    - res
      - drawable
      - mipmap-anydpi-v26
      - mipmap-hdpi
      - mipmap-mdpi
      - mipmap-xhdpi
      - mipmap-xxhdpi
      - mipmap-xxxhdpi
      - values
    - build.gradle
    - AndroidManifest.xml
  - build.gradle
- docs
- raw-assets
  - actiontext-G1
  - IST
  - MireilleImages

## Licenses

We will differentiate here the code part and the assets part.

#### Code
The code is licensed under the MIT license. You can find the ```LICENSE``` files in the root folder.

#### Assets

- **Images** : The images contained in the assets folder, ```kapotopia/android/assets```, are protected by [*CC BY-SA 4.0*](https://creativecommons.org/licenses/by-sa/4.0/legalcode.txt) unless stated otherwise, and thus were made specifically for our project.
- **Sounds/Music**, The musics contained in the ```kapotopia/android/assets/sound``` folder are also protected by [*CC BY-SA 4.0*](https://creativecommons.org/licenses/by-sa/4.0/legalcode.txt). However, for the sounds contained in ```kapotopia/android/assets/sound/bruitage```, each of the musical files are licensed in a different mannier. Their license can be found in the ```LICENSE.md``` file contained in that folder. You can also find the links to the websites of the sounds where you can find more information about it, like their author. Most of them are in public domain but some have choosen other licensing, so be careful in using them if you want to do so and respect their licenses.
- **Skins**, The Skins we are using are stored in the ```kapotopia/android/assets/skins``` folder, the differents licenses for each skin are also gathered together in a global ```LICENSE.md``` file in the root of that folder.
- **Icons**, The third-party icons used are stored in the ```kapotopia/android/assets/icons``` folder, and the licenses for each files are also stored in a ```LICENSE.md``` file.

## Some history

A first version of this application was developped and released during the event organized at the INGI departement at UCLouvain, the *2018 Openweek edition*. This first version was developped by the original team with the **gideros** framework using *Lua* language.

This first app was rebooted afterwards and only the base was kept. This second version was discontinued near February 2019 and no further update will be made. The whole gideros project is however still available in the ```R.I.P.``` folder on root in precedent commits.

The third version developped now is led by a new team and built using *Java* with the **LibGDX** framework.

## Thanks
The app is mainly developped by a student team from UCLouvain or *Université Catholique de Louvain*, at Louvain-la-neuve, Belgium.
##### To the original developping team for their hard work
* Tom Rousseaux
* François Duchêne
* Magali Legast
* Jean-François Sambon
* Sébastien

and the others.

##### The new developping team working hard on their free time :-)
* François Duchêne
* Magali Legast
* Lucas Ody
* Jean-François Sambon
* Colin Evrard

###### Our graphist and musician
* Guillaume Reginster
* Simon Desorbay

#### Commissionners and sponsors
The student groupe [*kap-hot*](https://www.facebook.com/KapHot16/), engaged in prevention for safe sexual practices, which originally commissionned this application, as well as the student government of the university UCLouvain ([*Assemblée Générale des Etudiants*](https://www.aglouvain.be/site2/) in French) for their precious support.

We'd also like to thank the non-profit organisation [*Univers-santé*](https://www.univers-sante.be/) for all the information they provided.
