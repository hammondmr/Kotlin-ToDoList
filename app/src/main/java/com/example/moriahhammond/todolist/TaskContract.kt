package com.example.moriahhammond.todolist

/**
 * Created by moriah.hammond on 6/30/17.
 */
import android.provider.BaseColumns

object TaskContract {

    @JvmStatic val DB_NAME = "com.example.moriahhammond.todolist.db"
    @JvmStatic val DB_VERSION = 1

    object TaskEntry : BaseColumns {
        @JvmStatic val TABLE = "tasks"
        @JvmStatic val COL_TASK_TITLE = "title"
        @JvmStatic val ID = "id"
    }

}