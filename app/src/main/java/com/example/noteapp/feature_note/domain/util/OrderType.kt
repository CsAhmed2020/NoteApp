package com.example.noteapp.feature_note.domain.util

/** step 7
 * create ascending and descending order types
 */
sealed class OrderType {

    object Ascending : OrderType()
    object Descending : OrderType()
}
