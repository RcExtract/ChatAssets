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
AntiCaseSpam is added. Starts to depend on ActionBarAPI
- Added AntiCaseSpam.java
- Added ActionBarAPI dependency
  - Added dependency
  - Can choose to display messages on chat or action bar
- Changed config.yml