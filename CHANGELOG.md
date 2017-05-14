# Change Log
This file tracks all the update notes of this project.
*plugin.yml will not be tracked.*
## Update 0.1.0
The structure of the plugin is finished.
- Added Main.java
- Added Command UI
  - Help
    - Customized Help Section is not done yet.
  - Reload
- Added AntiCurse.java
  - Replace words with characters.
- Added plugin.yml and config.yml
## Update 0.1.1
Fixed several bugs and changed config.yml settings.
- Changed config.yml
  - Added choices to enable or disable certain functions.
- Changed AntiCurse.java
  - Support replacing bad words that are not seperated with spaces.
  - Older versions doesn't support "a<badword>b" replacement, only support "a <badword> b". Now supported.
  - Fixed bug that occured when two badwords are sticked together.
## Update 0.2.0
AntiCaseSpam is added. Starts to depend on ActionBarAPI.
- Added AntiCaseSpam.java
- Added ActionBarAPI dependency
  - Added dependency.
  - Can choose to display messages on chat or action bar.
- Changed config.yml
## Update 0.3.0
Message Shortener is added. Fixed several bugs and compabilities between each functions.
- Added MessageShortener.java
- Added options for message shortener into config.yml
- Added sub-command messageshortener.
  - Added sub-sub-command add. Usage: /chatassets messageshortener add <key>.
  - Message can be typed not as an argument behind the key.
## Update 0.3.1
Fixed several bugs and compabilities between each functions.
## Update 0.3.2
Command for removing shorten messages is added.
- Changed Commander.java
  - Added sub-sub-command remove. Usage: /chatassets messageshortener remove <key>.
## Update 0.3.3
Fixed several bugs and compabilities between each functions.
## Update 1.0
Added a lot of things, especially chat auto modifier, msg and reply.
- Changed Main.java
  - Added support for color codes
- Changed Command UI
  - msg. Usage: /msg <player> <message>
  - reply. Usage: /reply <message>
    - msg command must be executed successfully before using this command before sender or receiver quitting.
- Added ChatAutoModifier.java
  - Adds spaces between each sentences.
  - Adds a full stop at the end of the message if not ended with '.', '?' or '!'.
- Added QuitHandler.java
  - Handles part of the reply command.
- Changed config.yml
## Update 1.1.0
Added customization for message when a player joins or leaves the server, and motd.
- Changed QuitHandler.java
  - Added customization for message when a player leaves the server.
- Added JoinHandler.java
  - Added customization for message when a player joins the server, and motd.
- Added PlaceHolders
  - <playername>: Player real name
  - <displayname>: Displayed player name
  - <onlineplayercount>: Total online players count
  - These placeholders can only be used in this plugin.
  - This plugin will support PlaceHolderAPI, which means it is not supported currently.
- Changed config.yml
  - Added options
    - Join Message
	- Quit Message
	- Motd