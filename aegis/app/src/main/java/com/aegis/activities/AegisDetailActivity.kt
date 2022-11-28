package com.aegis.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aegis.R
import com.aegis.models.AegisPlaceModel
import kotlinx.android.synthetic.main.activity_aegis_detail.*

class AegisDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aegis_detail)

        var AegisDetailModel: AegisPlaceModel? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            AegisDetailModel =
                    intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as AegisPlaceModel
        }

        if (AegisDetailModel != null) {

            setSupportActionBar(toolbar_happy_place_detail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = AegisDetailModel.title

            toolbar_happy_place_detail.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(AegisDetailModel.image))
            tv_description.text = AegisDetailModel.description
            tv_location.text = AegisDetailModel.location
        }

        btn_view_on_map.setOnClickListener {
            val intent = Intent(this@AegisDetailActivity, MapActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, AegisDetailModel)
            startActivity(intent)
        }
    }
}