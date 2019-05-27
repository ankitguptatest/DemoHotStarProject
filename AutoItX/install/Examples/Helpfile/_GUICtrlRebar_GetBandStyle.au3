#include <GUIConstantsEx.au3>
#include <GuiReBar.au3>
#include <GuiToolbar.au3>
#include <WindowsConstants.au3>

Global $g_idMemo

Example()

Func Example()
	Local $hGui, $idBtnExit, $hReBar, $hToolbar, $idInput
	Local Enum $e_idNew = 1000, $e_idOpen, $e_idSave, $e_idHelp

	$hGui = GUICreate("Rebar", 400, 396, -1, -1, BitOR($WS_MINIMIZEBOX, $WS_CAPTION, $WS_POPUP, $WS_SYSMENU, $WS_MAXIMIZEBOX))

	; create the rebar control
	$hReBar = _GUICtrlRebar_Create($hGui, BitOR($CCS_TOP, $WS_BORDER, $RBS_VARHEIGHT, $RBS_AUTOSIZE, $RBS_BANDBORDERS))

	$g_idMemo = GUICtrlCreateEdit("", 2, 100, 396, 250, $WS_VSCROLL)
	GUICtrlSetFont($g_idMemo, 10, 400, 0, "Courier New")

	; create a toolbar to put in the rebar
	$hToolbar = _GUICtrlToolbar_Create($hGui, BitOR($TBSTYLE_FLAT, $CCS_NORESIZE, $CCS_NOPARENTALIGN))

	; Add standard system bitmaps
	Switch _GUICtrlToolbar_GetBitmapFlags($hToolbar)
		Case 0
			_GUICtrlToolbar_AddBitmap($hToolbar, 1, -1, $IDB_STD_SMALL_COLOR)
		Case 2
			_GUICtrlToolbar_AddBitmap($hToolbar, 1, -1, $IDB_STD_LARGE_COLOR)
	EndSwitch

	; Add buttons
	_GUICtrlToolbar_AddButton($hToolbar, $e_idNew, $STD_FILENEW)
	_GUICtrlToolbar_AddButton($hToolbar, $e_idOpen, $STD_FILEOPEN)
	_GUICtrlToolbar_AddButton($hToolbar, $e_idSave, $STD_FILESAVE)
	_GUICtrlToolbar_AddButtonSep($hToolbar)
	_GUICtrlToolbar_AddButton($hToolbar, $e_idHelp, $STD_HELP)

	; create a input box to put in the rebar
	$idInput = GUICtrlCreateInput("Input control", 0, 0, 120, 20)

	;add band containing the control
	_GUICtrlRebar_AddBand($hReBar, GUICtrlGetHandle($idInput), 120, 200, "Name:")

	; add band containing the control to the beginning of rebar
	_GUICtrlRebar_AddToolBarBand($hReBar, $hToolbar, "", 0)

	_GUICtrlRebar_SetBandBackColor($hReBar, 1, Int(0x00008B))
	_GUICtrlRebar_SetBandForeColor($hReBar, 1, Int(0xFFFFFF))

	_GUICtrlRebar_SetBandStyle($hReBar, 1, BitOR($RBBS_CHILDEDGE, $RBBS_GRIPPERALWAYS, $RBBS_NOVERT))

	For $x = 0 To _GUICtrlRebar_GetBandCount($hReBar) - 1
		MemoWrite("==== Styles Used =====")
		MemoWrite("Band Index " & $x & @TAB & "fStyle: " & _GUICtrlRebar_GetBandStyle($hReBar, $x))
		MemoWrite("============================================")
		MemoWrite("$RBBS_BREAK.........: " & _GUICtrlRebar_GetBandStyleBreak($hReBar, $x))
		MemoWrite("$RBBS_CHILDEDGE.....: " & _GUICtrlRebar_GetBandStyleChildEdge($hReBar, $x))
		MemoWrite("$RBBS_FIXEDBMP......: " & _GUICtrlRebar_GetBandStyleFixedBMP($hReBar, $x))
		MemoWrite("$RBBS_FIXEDSIZE.....: " & _GUICtrlRebar_GetBandStyleFixedSize($hReBar, $x))
		MemoWrite("$RBBS_GRIPPERALWAYS.: " & _GUICtrlRebar_GetBandStyleGripperAlways($hReBar, $x))
		MemoWrite("$RBBS_HIDDEN........: " & _GUICtrlRebar_GetBandStyleHidden($hReBar, $x))
		MemoWrite("$RBBS_HIDETITLE.....: " & _GUICtrlRebar_GetBandStyleHideTitle($hReBar, $x))
		MemoWrite("$RBBS_NOGRIPPER.....: " & _GUICtrlRebar_GetBandStyleNoGripper($hReBar, $x))
		MemoWrite("$RBBS_NOVERT........: " & _GUICtrlRebar_GetBandStyleNoVert($hReBar, $x))
		MemoWrite("$RBBS_TOPALIGN......: " & _GUICtrlRebar_GetBandStyleTopAlign($hReBar, $x))
		MemoWrite("$RBBS_USECHEVRON....: " & _GUICtrlRebar_GetBandStyleUseChevron($hReBar, $x))
		MemoWrite("$RBBS_VARIABLEHEIGHT: " & _GUICtrlRebar_GetBandStyleVariableHeight($hReBar, $x))
		MemoWrite("============================================")
	Next

	$idBtnExit = GUICtrlCreateButton("Exit", 150, 360, 100, 25)
	GUICtrlSetState($idBtnExit, $GUI_DEFBUTTON)
	GUICtrlSetState($idBtnExit, $GUI_FOCUS)

	GUISetState(@SW_SHOW)

	While 1
		Switch GUIGetMsg()
			Case $GUI_EVENT_CLOSE, $idBtnExit
				Exit
		EndSwitch
	WEnd
EndFunc   ;==>Example

; Write message to memo
Func MemoWrite($sMessage = "")
	GUICtrlSetData($g_idMemo, $sMessage & @CRLF, 1)
EndFunc   ;==>MemoWrite
