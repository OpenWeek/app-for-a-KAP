# App-for-a-KAP

This repository contains all the code and assets concerning the upcoming mobile game "*Kapotopia*", which goal is to educate on the subject of prevention of Sexual Transmissible Infections using interactive games.

The app is mainly developped by a student team from UCLouvain or *Université Catholique de Louvain*, at Louvain-la-neuve, Belgium. It is commissionned by two student organizations, Kap Hot and Assemblée Générale des Etudiant·e·s de Louvain, more details in the **thanks** section. Outside contributions are also *very* appreciated. More details on how to contribute to the project can be found in the ```CONTRIBUTING.md``` file.

While the project documentation and code is written in English, the app language is actually written in French. The reason is that the primary goal is to deploy the app for the students of UCLouvain, who are primarly French speakers, in a first time. English and Dutch translations may follow in the future.

## Some history

A first version of this application was developped and released during the event organized at the INGI departement at UCLouvain, the *2018 Openweek edition*. This first version was developped by the original team with the **gideros** framework using *Lua* language.

This first app was rebooted afterwards and only the base was kept. This second version was discontinued near February 2019 and no further update will be made. The whole gideros project is however still available in the ```R.I.P.``` folder on root.

The third version developped now is led by a new team and built using *Java* with the **LibGDX** framework.

## Structure

The folder ```kapotopia``` contains the new app coded in *Java* while the ```R.I.P.``` folder contains the old app coded in *Lua*.

The project is divided into three sections :
* core
* android
* ios

The **core** folder contains the main game logic, the android and ios folders contains the assets and files to make it work on both OS. Currently, only the android part is working, the ios porting will be done after the main game is finished.

## Licenses

We will differentiate here the code part and the assets part.

#### Code
The code is licensed under the MIT license, both for the old version and the new version. You can find the ```LICENSE``` files in their respective folders.

#### Assets
Concerning the old version, all the images and sounds in folders ```R.I.P./images``` and ```R.I.P./audio``` are protected by *CC BY-SA 4.0* available at address https://creativecommons.org/licenses/by-sa/4.0/legalcode.txt .

Concerning the new version, it is more specific. The images contained in the assets folder, ```kapotopia/android/assets```, are protected by *CC BY-SA 4.0* as well as the music, in ```kapotopia/android/assets/sound```. However, for the sounds contained in ```kapotopia/android/assets/sound/bruitage```, each of the musical files are licensed in a different mannier. Their license can be found in the ```LICENSE.md``` file contained in that folder. In that ```LICENSE.md``` file are also the links to the websites of the sounds where you can find more information about it, like their author. Most of them are in public domain but some have choosen other licensing, so be careful in using them if you want to do so and respect their licenses.

## Thanks
#### To the original developping team for their hard work
* Tom Rousseaux
* François Duchêne
* Magali Legast
* Jean-François Sambon

and the others.

#### The new developping team working hard on their free time :-)
* François Duchêne
* Magali Legast
* Lucas Ody
* Jean-François Sambon

and the others.

###### Our graphist and musician
* Guillaume Reginster
* Simon Desorbay

#### Commissionners and sponsors
The student groupe [*kap-hot*](https://www.facebook.com/KapHot16/), engaged in prevention for safe sexual practices, which originally commissionned this application, as well as the student government of the university UCLouvain ([*Assemblée Générale des Etudiants*](https://www.aglouvain.be/site2/) in French) for their precious support.

We'd also like to thank the non-profit organisation [*Univers-santé*](https://www.univers-sante.be/) for all the information they provided.
