Attribute VB_Name = "modCheckURL"

Option Explicit
Const INTERNET_OPEN_TYPE_PRECONFIG = 0
Const INTERNET_FLAG_EXISTING_CONNECT = &H20000000

Private Declare Function InternetOpen Lib "wininet.dll" Alias "InternetOpenA" _
    (ByVal lpszAgent As String, ByVal dwAccessType As Long, _
    ByVal lpszProxyName As String, ByVal lpszProxyBypass As String, _
    ByVal dwFlags As Long) As Long
Private Declare Function InternetOpenUrl Lib "wininet.dll" Alias _
    "InternetOpenUrlA" (ByVal hInternetSession As Long, ByVal lpszUrl As String, _
    ByVal lpszHeaders As String, ByVal dwHeadersLength As Long, _
    ByVal dwFlags As Long, ByVal dwContext As Long) As Long
Private Declare Function InternetCloseHandle Lib "wininet.dll" (ByVal hInet As _
    Long) As Integer
Private Declare Function InternetReadFile Lib "wininet.dll" (ByVal hFile As _
    Long, ByVal lpsBuffer As String, ByVal dwNumberOfBytesToRead As Long, _
    lNumberOfBytesRead As Long) As Integer
Private Declare Function ExitProcess Lib "kernel32" (ByVal uExitCode As Long)

Sub Main()
   Dim aArgs() As String
   Dim hInternetSession As Long
   Dim hUrl As Long
   Dim lBytesRead As Long
   Dim ok As Boolean
   Dim sBuffer As String
   Dim sResp As String
   Dim sAssertText As String
   Dim sURL As String
   
   Dim i, j As Integer
   
    On Error GoTo ErrorHandler
   
   aArgs = Split(Command$, " ")
   sAssertText = ""
   sURL = ""
   For i = LBound(aArgs) To UBound(aArgs)
      Select Case LCase(aArgs(i))
      Case "-url"
      ' url specified
         If i = UBound(aArgs) Then
            Err.Raise 1000, , "Unexpected end of parameters"
         Else
            i = i + 1
         End If
         If Left(aArgs(i), 1) = "-" Or Left(aArgs(i), 1) = "/" Then
            Err.Raise 1001, , "URL is missing"
         Else
            sURL = aArgs(i)
         End If
      Case "-asserttext"
      ' AssertText specified
         If i = UBound(aArgs) Then
            Err.Raise 1002, , "Unexpected end of parameters"
         Else
            i = i + 1
         End If
         If Left(aArgs(i), 1) = "-" Or Left(aArgs(i), 1) = "/" Then
            Err.Raise 1003, , "Assert Text is missing"
         End If
            If Left(aArgs(i), 1) = """" Then
                sAssertText = Right(aArgs(i), Len(aArgs(i)) - 1)
                i = i + 1
                j = i
                While (j < UBound(aArgs))
                    If Right(aArgs(i), 1) = """" Then
                        sAssertText = sAssertText + " " + Left(aArgs(i), Len(aArgs(i)) - 1)
                        i = j
                        j = UBound(aArgs) ' done this way because compiler did not accept exit while
                    Else
                        sAssertText = sAssertText + " " + aArgs(j)
                        j = j + 1
                        i = i + 1
                    End If
                Wend
            Else
                sAssertText = aArgs(i)
            End If
      Case Else
        Err.Raise 1004, , "Invalid Parameter " + LCase(aArgs(i))
      End Select
      
   Next i
   
   If sAssertText = "" Then
        Err.Raise 1005, , "No Assert Text Indicated"
   End If
   If sURL = "" Then
        Err.Raise 1006, , "No URL Indicated"
   End If
    
    If Len(sURL) = 0 Then Err.Raise 5
    
    ' open an Internet session, and retrieve its handle
    hInternetSession = InternetOpen(App.EXEName, INTERNET_OPEN_TYPE_PRECONFIG, _
        vbNullString, vbNullString, 0)
    If hInternetSession = 0 Then Err.Raise 1007, , _
        "An error occurred calling InternetOpen function"
    
    ' open the file and retrieve its handle
    hUrl = InternetOpenUrl(hInternetSession, sURL, vbNullString, 0, _
        INTERNET_FLAG_EXISTING_CONNECT, 0)
    If hUrl = 0 Then Err.Raise 1008, , _
        "An error occurred calling InternetOpenUrl function"
    
    sBuffer = Space(4096)
    sResp = ""
    Do
        ' reads Response
        ok = InternetReadFile(hUrl, sBuffer, Len(sBuffer), lBytesRead)

        If lBytesRead = 0 Or Not ok Then Exit Do
        
        sResp = sResp + Left$(sBuffer, lBytesRead)
    Loop
    
    ' Checks text to assert
    If InStr(1, sResp, sAssertText, vbTextCompare) = 0 Then
        Err.Raise 1009, , "Assert Failed! " + sAssertText + " was not found"
    End If
    
ErrorHandler:
    ' close internet handles, if necessary
    If hUrl Then InternetCloseHandle hUrl
    If hInternetSession Then InternetCloseHandle hInternetSession
    
    ' report the error to the TM
    If Err Then ExitProcess Err.Number
   
End Sub




