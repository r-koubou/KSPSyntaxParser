# Changelog

## Version 0.2.1

### BUG Fixed Syntax Parser program

* Not work when newline is CRLF
* select statement

## Version 0.2.0

1. Modified some snippet.
2. **NEW!: Syntax validation ( ALPHA VERSION! ). Semantic analysis not yet.**

![](https://github.com/r-koubou/vscode-syntax-for-ksp/raw/master/images/readme/syntaxparser.gif)

### NOTE

* Default is **"disabled"**.

    You can change setting at Preferences -> Settings (Part of "KSP NI KONTAKT Script" ).

* You need to install **Java 1.6 (or higher).**

    **Recomended** 1.8 or higher to work it.

## Version 0.1.0

* NEW Feature

    - Find symbols and find references in script (context menu / Shift+F12)

* Improved

    - Syntax highlighting

## Version 0.0.5

* NEW Feature

    - Go to definition

        Ctrl(Command)+Shift+O or F2 or Ctrl(Command)+Mouse Click
        ![](https://github.com/r-koubou/vscode-syntax-for-ksp/raw/master/images/readme/goto1.png)

        - Variable

            - If variable type is ui_####, you can select following

                1. Callback definition
                2. Variable definition

                ![](https://github.com/r-koubou/vscode-syntax-for-ksp/raw/master/images/readme/goto2.png)

        - User function

* Improved

    - Syntax highlighting

## Version 0.0.4

* Improved

    - Syntax highlighting, hover text, autocomplete

        - Some commands (incl non documented)


## Version 0.0.3

* KONTAKT 5.6.5 ready. Following command, variable added

    - New UI control
        - ui_xy
        - $CONTROL_PAR_CURSOR_PICTURE
        - $CONTROL_PAR_MOUSE_MODE
        - $CONTROL_PAR_ACTIVE_INDEX
        - $CONTROL_PAR_MOUSE_BEHAVIOUR_X
        - $CONTROL_PAR_MOUSE_BEHAVIOUR_Y

	- New UI Commands
        - set_control_par_arr()
        - set_control_par_str_arr()

* Improved

    - KSP commands signature behavior  (hover)
    - Syntax highlighting (KSP commands)

## Version 0.0.2

* Autocomplete supported
* Fixed file extension ( Added ".txt")
* Tweek some files.

## Version 0.0.1

### 1st release. ( Based on KONTAKT 5.6 )

Following features supported

* Syntax hilighting
* Snippets
	* Callbacks
	* Events
	* Commands
	* Built-in Variables