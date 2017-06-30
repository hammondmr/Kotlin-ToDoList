package com.example.moriahhammond.todolist

import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.util.Log
import org.jetbrains.anko.alert
import org.jetbrains.anko.editText
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout
import android.content.ContentValues
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.View



class MainActivity : AppCompatActivity() {

    //MARK: ivars
    val TAG = "MainActivity"
    private var mHelper = TaskDbHelper(this)
    private lateinit var mTaskListView: ListView
    private var mAdapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mHelper = TaskDbHelper(this)
        mTaskListView = findViewById(R.id.list_todo) as ListView
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
        updateUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_add_task -> {
                alert("Testing alerts") {
                    title("Add new task")
                    message("What do you need to do")
                    val task = ""
                    customView{
                        verticalLayout{
                            val taskEditText = editText {
                                hint = "Enter task"
                            }
                            positiveButton("Add") {
                                toast("Task added")
                                Log.d(TAG, "Task to add: " + task)

                                val task = taskEditText.text.toString()
                                val db = mHelper?.getWritableDatabase()
                                val values = ContentValues()

                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task)
                                db?.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE)
                                db?.close()
                                updateUI()
                            }
                            negativeButton("Cancel") {
                                toast("No task added")
                            }
                        }
                    }
                }.show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        var taskList = ArrayList<String>()
        val db = mHelper!!.getReadableDatabase()
        val stringArray = arrayOf(TaskContract.TaskEntry.ID, TaskContract.TaskEntry.COL_TASK_TITLE)
        val cursor = db.query(TaskContract.TaskEntry.TABLE, stringArray, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
            taskList.add(cursor.getString(idx))
            //Log.d(TAG, "Task: " + cursor.getString(idx))
        }
        if(mAdapter == null){
            mAdapter = ArrayAdapter<String>(this, R.layout.item_todo, R.id.task_title, taskList)
            mTaskListView?.setAdapter(mAdapter)
        } else {
            mAdapter!!.clear()
            mAdapter!!.addAll(taskList)
            mAdapter!!.notifyDataSetChanged()
        }
        cursor.close()
        db.close()
    }

    fun deleteTask(view: View) {
        val parent = view.getParent() as View
        val taskTextView = findViewById(R.id.task_title) as TextView
        val task = taskTextView.text.toString()
        val db = mHelper.writableDatabase
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                arrayOf(task))
        db.close()
        updateUI()
    }
}
