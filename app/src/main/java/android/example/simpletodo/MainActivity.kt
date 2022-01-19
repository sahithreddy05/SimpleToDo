package android.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

     var listOfTasks = mutableListOf<String>()
    lateinit  var adapter:TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. remove
                listOfTasks.removeAt(position)
                // 2. notify adapter that our data set  has changed
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }

        LoadItems()
//        lookup recyclerview in layout
       val recyclerView =  findViewById<RecyclerView>(R.id.recyclerView)
         adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
//        attach each other
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask = inputTextField.text.toString()

            listOfTasks.add(userInputtedTask)

            adapter.notifyItemInserted(listOfTasks.size - 1)
            inputTextField.setText("")
            saveItems()
        }
    }
//    save data that user has inputed
//    save data by writing and reading from a file

//    Get a method to get the file we need
        fun getDataFile(): File {

//    Every line is going to represent a specific task in our list of tasks
            return  File(filesDir,"data.txt")
        }

//    Load the items by reading every line in the data file
        fun LoadItems() {
    try {
        listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
    }catch (ioException:IOException) {
    ioException.printStackTrace()
    }
}
//    save items by writing them into our data file
        fun saveItems() {
    try {
        FileUtils.writeLines(getDataFile(), listOfTasks)
    }catch (ioException:IOException){
        ioException.printStackTrace()
    }
}

}