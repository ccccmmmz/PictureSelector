package kotlinImpl.recycler_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.luck.pictureselector.R
import com.luck.pictureselector.databinding.ActivitySimpBinding
import com.luck.pictureselector.databinding.ActivityTestRecyclerBinding

class TestRecyclerActivity : AppCompatActivity() {

    val mViewBinding by lazy { ActivityTestRecyclerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mViewBinding.root)
        initRecycler()
    }

     private fun initRecycler(){
         val adapter = CommonAdapter<Any>().apply {
             mViewBinding.rvTest.layoutManager = LinearLayoutManager(this@TestRecyclerActivity)
             mViewBinding.rvTest.adapter = this

         }
         adapter.regisModelRegistry(Person::class.java, R.layout.item_person)
         adapter.regisModelRegistry(Person::class.java, R.layout.item_person)

         adapter.addData(Person("人", 10))
         adapter.addData(Student("学成", 20))
     }
}

data class Person(val name: String = "", val age: Int = 0)
data class Student(val name: String = "", val age: Int = 0)