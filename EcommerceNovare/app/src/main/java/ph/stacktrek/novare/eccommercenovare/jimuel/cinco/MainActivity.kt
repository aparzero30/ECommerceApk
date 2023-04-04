package ph.stacktrek.novare.eccommercenovare.jimuel.cinco

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.adapters.ProductAdapter
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.adapters.SwipeCallback
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.databinding.ActivityMainBinding
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.databinding.DialogueAddProductBinding
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.DatabaseHandler
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.Product
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.ProductDAO
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model.ProductDAOSQLLiteImplementation
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter:ProductAdapter
    private lateinit var productDAO: ProductDAO
    private lateinit var itemTouchHelper: ItemTouchHelper










    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = intent.extras
        Log.d("MAIN ACTIVITY","USERNAME BUNDLE: ${bundle!!.getString("bundle_username")}")

        val extra = intent.getStringExtra("username")
        Log.d("MAIN ACTIVITY","USERNAME EXTRA : $extra")






        loadProducts()

        binding.fabAddProductButton.setOnClickListener{
            showAddProductDialogue().show()
        }

    }

    fun loadProducts(){
        productDAO = ProductDAOSQLLiteImplementation(applicationContext)
        productAdapter = ProductAdapter(applicationContext, productDAO.getProducts() as ArrayList<Product>)
        with(binding.productsList){
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = productAdapter
        }
        var swipeCallback = SwipeCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, applicationContext)
        swipeCallback.productAdapter = productAdapter
        itemTouchHelper = ItemTouchHelper(swipeCallback).apply {
            attachToRecyclerView(binding.productsList)
        }
    }




    fun showAddProductDialogue(): Dialog {
        return this!!.let {
            val builder = AlertDialog.Builder(it)
            var dialogueAddProductBinding: DialogueAddProductBinding =
                DialogueAddProductBinding.inflate(it.layoutInflater)
            with(builder) {
                setPositiveButton(null, null) // Set null values for default behavior

                setNegativeButton(null, null) // Set null values for default behavior

                setView(dialogueAddProductBinding.root)

                val dialog = create()
                dialog.setOnShowListener {
                    // Get references to the "ADD" and "CANCEL" buttons
                    val addButton = dialog.findViewById<AppCompatButton>(R.id.add_button)
                    val cancelButton = dialog.findViewById<AppCompatButton>(R.id.cancel_button)

                    // Set the positive button to the "ADD" button
                    addButton.setOnClickListener {
                        val name = dialogueAddProductBinding.productName.text.toString().trim()
                        if (name.isNotEmpty()) {



                            val product = Product(name)
                            val image = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.main_default)
                            val file = File(applicationContext.filesDir, "zelda_default.jpg")
                            val fileOutputStream2 = FileOutputStream(file)
                            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2)
                            fileOutputStream2.flush()
                            fileOutputStream2.close()
                            product.imagePath = file.absolutePath
                            val productDAO = ProductDAOSQLLiteImplementation(applicationContext)
                            productDAO.addProduct(product)
                            productAdapter.addProduct(product)
                            dialog.dismiss()


                        } else {
                            Toast.makeText(context, "Product name cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Set the negative button to the "CANCEL" button
                    cancelButton.setOnClickListener {
                        dialog.dismiss()
                    }
                }

                dialog
            }
        }
    }





    override fun onBackPressed() {
        super.onBackPressed()
        val goToMain = Intent(applicationContext,
            LoginActivity::class.java)

        startActivity(goToMain)
        finish()

    }
}