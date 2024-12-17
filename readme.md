# Textboard bot (fr)

Est un simple bot pour [Textboard](https://textboard.fr).

Son but est d'être capable de dessiner des ascii arts super vite (avec plusieurs comptes)

## Utilisation

Compilez le code vous même avec java 17 (ou 21), ou bien telechargez le depuis l'onglet release.

Dans un fichier `tokens.txt`, mettez vos refresh_token dedans (un token par ligne, jusqu'a 10 par ip) comme ça : 

```
token1
token2
etc...
tokenN
```

ps : le fichier tokens.txt doit être dans le même dossier que le bot lui même.

dans un terminal, tapez `java -jar textboard_bot-1.0-SNAPSHOT-all.jar [chemin/vers/votre/ascii.txt] [Point d'origine X] [Point d'origine Y]`

exemple : `java -jar textboard_bot-1.0-SNAPSHOT-all.jar text.txt 0 0`

## Obtenir votre token

Sur textboard.fr, une fois connecté, allez dans les outils de dev (f12 sur firefox).

Dans l'onglet "stockage", choisissez "cookies", et copiez le contenu de "refresh_token" (voir screenshot)

![Storage -> cookies](https://i.ibb.co/sPBxsHr/image.png "Exemple of cookie")

# Textboard bot (en)

Is a simple bot for [Textboard](https://textboard.fr).

Its goal is to be able to print ascii art at blazing speeds.

## Usage

Compile the code yourself using Java 17 (or later), or download it through release

Put as many refresh_tokens you need (up to 10 per ip) in a file named `tokens.txt` (case sensitive !) in the same directory as the jar file as such :

```
token1
token2
etc...
tokenN
```

then, in a terminal, type `java -jar textboard_bot-1.0-SNAPSHOT-all.jar [path/to/ascii.txt], [X origin point] [Y origin point]`

for exemple : `java -jar  textboard_bot-1.0-SNAPSHOT-all.jar text.txt 0 0`

## Obtain your token

on textboard.fr, once you are logged in, open your dev tools (F12 on firefox).

go to storage (or equivalent on chromium based browsers), select cookies, and copy refresh_token's value

# DO NOT SHARE THIS TOKEN WITH ANYONE AS THEY CAN USE IT TO IMPERSONATE YOU !!!!!

### PLEASE NOTE THAT YOU ALSO NEED TO HAVE MORE THAN ONE ACCOUNT

here's an image showing it : 

![Storage -> cookies](https://i.ibb.co/sPBxsHr/image.png "Exemple of cookie")
