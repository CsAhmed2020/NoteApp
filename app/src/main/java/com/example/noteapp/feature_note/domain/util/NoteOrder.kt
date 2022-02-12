package com.example.noteapp.feature_note.domain.util

/** step 8
 * define order by what and det the way (ASC or DES)
 */
sealed class NoteOrder(val orderType: OrderType){
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)

    //for states when i need to keep note order same (title & date & color), but only change the order type (ASE & DESC)
    //used in step 17
    fun copy (orderType: OrderType) : NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
