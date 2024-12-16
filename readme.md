# Textboard bot

Is a simple bot for [Textboard](https://textboard.fr).

Its goal is to be able to print ascii art at blazing speeds.

# Usage

Compile the code yourself using Java 17 (or later), or download it through release

then, in a terminal, type `java -jar path/to/textboard-bot.jar [path/to/ascii.txt], [X origin point] [Y origin point] [your token]`

for exemple : `java -jar textboard-bot.jar text.txt 0 0 token`

# Obtain your token

on textboard.fr, once you are logged in, open your dev tools (F12 on firefox).

go to storage (or equivalent on chromium based browsers), select cookies, and copy refresh_token's value

# DO NOT SHARE THIS TOKEN WITH ANYONE AS THEY CAN USE IT TO IMPERSONATE YOU !!!!!

here's an image showing it : 

![Storage -> cookies](https://i.ibb.co/sPBxsHr/image.png "Exemple of cookie")