package com.qualcomm.snapdragon.spaces.spacescontroller.util

class Permission{
    private val tag = "Permission message"
    private val userMicrophonePermissionAgreeCode = 1
    private val userCameraPermissionAgree = 1

    fun askMicrophonePermission(context: Context){

        val currentMicrophonePermission = ActivityCompat.checkSelfPermission(context,android.Manifest.permission.RECORD_AUDIO)


        if(currentMicrophonePermission != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.RECORD_AUDIO), userMicrophonePermissionAgreeCode)
    }
    fun askCameraPermission(context: Context){
        val currentCameraPermission = ActivityCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)


        if(currentCameraPermission != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.CAMERA), )
    }

    fun handlePermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            userMicrophonePermissionAgreeCode -> {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                    Log.i(tag,"Agree microphone permission")
                else
                    Log.i(tag,"Not agree microphone permission")
            }
        }
    }
}