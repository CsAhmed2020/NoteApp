package com.example.noteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** step 11
 * define main app class and dagger hilt annotation to use di
 * define it in manifest
 */

@HiltAndroidApp
class NoteApp : Application() {
}