package lv.romstr.mobile.rtu_android

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var photoUri: Uri
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        takePictureButton.setOnClickListener { takePicture() }

        takePictureButtonCameraX.setOnClickListener { takePictureCameraX() }
    }

    private fun takePictureCameraX() {
        val intent = Intent(this, CameraActivity::class.java)

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_CAMERA_X)
    }

    private fun takePicture() {

        val photoFile = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }

        photoFile?.let {

            photoUri = FileProvider.getUriForFile(
                this,
                "lv.fjodors.rtu_android_14_playground_practice.fileprovider",
                it
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            intent.resolveActivity(packageManager)?.let {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        val storageDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        } else {
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        }

        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).also {
            currentPhotoPath = it.absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            imageView.setImageURI(photoUri)

            setPicture()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                addToGalleryMediaStore()
            } else {
                addToGallery()
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE_CAMERA_X && resultCode == Activity.RESULT_OK) {
            val uri = data!!.getParcelableExtra<Uri>("uri")

            imageView.setImageURI(uri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun addToGalleryMediaStore() {
        val file = File(currentPhotoPath)

        val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, photoUri))

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val tableUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        val imageUri = contentResolver.insert(tableUri, values)!!

        contentResolver.openOutputStream(imageUri).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }

        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)

        contentResolver.update(imageUri, values, null, null)
    }

    private fun addToGallery() {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)

        val file = File(currentPhotoPath)
        val uri = Uri.fromFile(file)

        intent.data = uri
        sendBroadcast(intent)
    }

    private fun setPicture() {
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath)

            val scaleFactor =
                Math.max(1, Math.min(outHeight / imageView.height, outWidth / imageView.width))

            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        imageView.setImageBitmap(bitmap)
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_CAPTURE_CAMERA_X = 2
    }
}