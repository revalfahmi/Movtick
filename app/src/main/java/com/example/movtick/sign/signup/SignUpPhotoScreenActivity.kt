package com.example.movtick.sign.signup

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movtick.home.HomeActivity
import com.example.movtick.R
import com.example.movtick.utils.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.UUID

class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener {

    private lateinit var tvWelcome: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var btnAddPhoto: ImageView
    private lateinit var btnSave: Button
    private lateinit var btnUploadLater: Button

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo_screen)

        tvWelcome = findViewById(R.id.tv_welcome)
        ivProfile = findViewById(R.id.iv_profile)
        ivBack = findViewById(R.id.iv_back)
        btnAddPhoto = findViewById(R.id.iv_add_photo)
        btnSave = findViewById(R.id.btn_save)
        btnUploadLater = findViewById(R.id.btn_upload_later)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        tvWelcome.text = "Welcome,\n"+intent.getStringExtra("nama")

        btnAddPhoto.setOnClickListener {
            if (statusAdd){
               statusAdd = false
                btnSave.visibility = View.VISIBLE
                btnAddPhoto.setImageResource(R.drawable.ic_btn_upload)
                ivProfile.setImageResource(R.drawable.user_pic)
            } else {
                Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
            }
        }

        btnUploadLater.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btnSave.setOnClickListener {
            if (filePath != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoScreenActivity, "Upload Photo Successfully", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValue("url", it.toString())
                        }

                        finishAffinity()
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Upload Photo Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload "+progress.toInt()+ " %")
                    }
            } else {

            }
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "You can't added photo profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Are you in a hurry? Just click the upload button later", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)

            btnSave.visibility = View.VISIBLE
            btnAddPhoto.setImageResource(R.drawable.ic_btn_delete)
        }
    }
}