-----ToDoList-----
Version 1.0.1.0
Created by: Frank Derry Wanye, 2016.

This is a to-do-list program, intended to be used as a reminder
for upcoming tasks.

Installation instructions:
(i) If you have not already done so, download the ToDoList.exe
file from http://github.com/ffrankies/ToDoList/
(ii) Save the file in a permanent folder (do not move this folder, or the file after running the program unless you want to perform some extra steps)
(iii) Run ToDoList.exe

The program automatically creates a batch file in your startup location, so that it runs automatically whenever you log in to your computer.

(iv) If you need a desktop shortcut for the program, simple create one by right clicking on the .exe file, scrolling to "Send to..." and selecting "Desktop (create shortcut)."

If you would like to move the ToDoList.exe file for whatever reason, or you've renamed one of the folders in it's path, then to make the program run again on startup, you have to open your startup location (C:/Users/(Your username)/AppData/Roaming/Microsoft/Windows/Start Menu/Startup/), locate the ToDoList.bat file, and either manually edit the path inside it or delete it and restart the application. Note that AppData is a hidden folder, so you will have to enable viewing of hidden folders (Control Panel -> Folder Options) in order to do this.

Known Problems:
(i) Currently there is no manual - I will be working on that shortly
(ii) The application uses quite a lot of CPU power (20% on my laptop), I plan to fix that to the best of my ability in the next release.
(iii) The shell that opens up whenever the program starts from the batch file is annoying
(iv) No installer, so shortcuts have to be created manually

Plans for future releases:
- Improved UI
- An on-screen manual
- A settings menu with color schemes and font sizes
- An installer, or at least an executable that creates shortcuts

Contact information:
If you have any suggestions, complaints or questions, you can send me an e-mail at wanyef@mail.gvsu.edu

Credit goes to MadProgrammer from StackOverflow for the base code used in the CustomBooleanCellEditor class.

Known bugs:
- Repaint problem when task is deleted – fixed
