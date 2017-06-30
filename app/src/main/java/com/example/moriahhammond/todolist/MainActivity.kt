package com.example.moriahhammond.todolist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.util.Log
import android.widget.EditText
import org.jetbrains.anko.alert
import org.jetbrains.anko.editText
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {

    //MARK: ivars
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
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
                                val task = taskEditText.text
                                toast("Task added")
                                Log.d(TAG, "Task to add: " + task)
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
}
