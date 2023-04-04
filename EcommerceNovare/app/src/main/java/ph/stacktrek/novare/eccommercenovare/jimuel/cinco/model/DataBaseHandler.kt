package ph.stacktrek.novare.eccommercenovare.jimuel.cinco.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ph.stacktrek.novare.eccommercenovare.jimuel.cinco.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASENAME,null, DATABASEVERSION) {
    private val mContext = context
    companion object {
        private val DATABASEVERSION = 1
        private val DATABASENAME = "ProductsDatabase"

        const val TABLE_PRODUCT = "product_table"
        const val TABLE_PRODUCT_ID = "id"
        const val TABLE_PRODUCT_NAME = "name"
        const val TABLE_PRODUCT_PRICE = "price"
        const val TABLE_PRODUCT_BRAND = "brand"
        const val TABLE_PRODUCT_MEASUREMENT = "measurement"
        const val TABLE_PRODUCT_DESCRIPTION = "description"
        const val TABLE_PRODUCT_MEASUREMENT_UNIT = "measurement_unit"
        const val TABLE_PRODUCT_QUANTITY = "quantity"
        const val TABLE_PRODUCT_IMAGE = "image"
        const val TABLE_PRODUCT_IMAGE_PATH = "img_path"

    }


        override fun onCreate(db: SQLiteDatabase?) {
            val CREATE_PRODUCTS_TABLE =
                "CREATE TABLE $TABLE_PRODUCT " +
                        "($TABLE_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$TABLE_PRODUCT_NAME TEXT, " +
                        "$TABLE_PRODUCT_PRICE REAL, " +
                        "$TABLE_PRODUCT_BRAND TEXT, " +
                        "$TABLE_PRODUCT_MEASUREMENT REAL, " +
                        "$TABLE_PRODUCT_DESCRIPTION TEXT, " +
                        "$TABLE_PRODUCT_MEASUREMENT_UNIT TEXT, " +
                        "$TABLE_PRODUCT_QUANTITY REAL, " +
                        "$TABLE_PRODUCT_IMAGE BLOB, " +
                        "$TABLE_PRODUCT_IMAGE_PATH TEXT)"




        db?.execSQL(CREATE_PRODUCTS_TABLE)



            val image = BitmapFactory.decodeResource(mContext.resources, R.drawable.zelda)
            val file = File(mContext.filesDir, "main_image.jpg")
            val fileOutputStream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            val values = ContentValues()
            values.put(TABLE_PRODUCT_NAME, "ZELDA BOTW")
            values.put(TABLE_PRODUCT_IMAGE_PATH, file.absolutePath) // add new column for file path
            val id = db?.insert(TABLE_PRODUCT, null, values)


            val image2 = BitmapFactory.decodeResource(mContext.resources, R.drawable.zelda1)
            val file2 = File(mContext.filesDir, "zelda1.jpg")
            val fileOutputStream2 = FileOutputStream(file2)
            image2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2)
            fileOutputStream2.flush()
            fileOutputStream2.close()

            val values2 = ContentValues()
            values2.put(TABLE_PRODUCT_NAME, "LINKS AWKNG")
            values2.put(TABLE_PRODUCT_IMAGE_PATH, file2.absolutePath)
            val id2 = db?.insert(TABLE_PRODUCT, null, values2)

            val image3 = BitmapFactory.decodeResource(mContext.resources, R.drawable.zelda2)
            val file3 = File(mContext.filesDir, "zelda2.jpg")
            val fileOutputStream3 = FileOutputStream(file3)
            image3.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream3)
            fileOutputStream3.flush()
            fileOutputStream3.close()

            val values3 = ContentValues()
            values3.put(TABLE_PRODUCT_NAME, "ZELDA SKYWRD")
            values3.put(TABLE_PRODUCT_IMAGE_PATH, file3.absolutePath)
            val id3 = db?.insert(TABLE_PRODUCT, null, values3)




        }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
        onCreate(db)
    }


}