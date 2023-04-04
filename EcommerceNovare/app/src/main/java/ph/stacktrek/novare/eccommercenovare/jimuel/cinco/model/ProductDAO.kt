package ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.R
import java.io.File
import java.io.FileOutputStream


interface ProductDAO {
    fun addProduct(product: Product)
    fun getProducts(): ArrayList<Product>
    fun updateProduct(product: Product)
    fun deleteProduct(product: String)
}

class ProductDAOStubImplementation: ProductDAO{
    private var productList: ArrayList<Product> = ArrayList()

    init{
        productList.add(Product("Corned Beef"))
        productList.add(Product("Canned Tuna"))
        productList.add(Product("Canned Carrots and Corn"))
        productList.add(Product("Powdered Milk"))
        productList.add(Product("Coffee"))
        productList.add(Product("Canned Beer"))
        productList.add(Product("Canned Soda"))
        productList.add(Product("Creamer"))
        productList.add(Product("Bottled Water"))
        productList.add(Product("Bottled Soda"))
        productList.add(Product("Bottled Tea"))
        productList.add(Product("Bottled Milk Tea"))
    }

    override fun addProduct(product: Product) {
        productList.add(product)
    }

    override fun getProducts(): ArrayList<Product> = productList

    //TODO("Not yet implemented")
    override fun updateProduct(product: Product) {

    }

    //TODO("Not yet implemented")
    override fun deleteProduct(product: String) {

    }

}

class ProductDAOSQLLiteImplementation(var context: Context): ProductDAO{
    override fun addProduct(product: Product) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLE_PRODUCT_NAME, product.name)

        var status = db.insert(DatabaseHandler.TABLE_PRODUCT,
            null,
            contentValues)
        db.close()
    }

    override fun getProducts(): ArrayList<Product> {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var result = ArrayList<Product>()
        var cursor: Cursor? = null



        val columns =  arrayOf(DatabaseHandler.TABLE_PRODUCT_ID,
            DatabaseHandler.TABLE_PRODUCT_NAME, DatabaseHandler.TABLE_PRODUCT_IMAGE_PATH)

        try {
            cursor = db.query(DatabaseHandler.TABLE_PRODUCT,
                columns,
                null,
                null,
                null,
                null,
                null)



        }catch (sqlException: SQLException){
            db.close()
            return result
        }

        var product: Product
        if(cursor.moveToFirst()){
            do{
                product = Product("")
                product.name = cursor.getString(1)
                product.id = cursor.getInt(0).toString()

//                product.imagePath = cursor.getString(2);

                if (!cursor.isNull(2)) {
                    product.imagePath = cursor.getString(2)
                }
                else{
                    val image = BitmapFactory.decodeResource(context.resources, R.drawable.main_default)
                    val file = File(context.filesDir, "default.jpg")
                    val fileOutputStream2 = FileOutputStream(file)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2)
                    fileOutputStream2.flush()
                    fileOutputStream2.close()
                    product.imagePath = file.absolutePath
                }
                result.add(product)
            }while(cursor.moveToNext())

        }
        return result
    }

    override fun updateProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(product: String) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        db.delete(DatabaseHandler.TABLE_PRODUCT,
            "${DatabaseHandler.TABLE_PRODUCT_ID}=?",
            arrayOf(product))

        db.close()
    }


}