package com.example.canvas.presentation.screens

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.canvas.R
import com.example.canvas.databinding.ActivityImageSearchBinding
import com.example.canvas.presentation.KEY_IMAGE_SEARCH
import com.example.canvas.presentation.URL_IMAGE
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class ImageSearchActivity : AppCompatActivity() {

    lateinit var binding: ActivityImageSearchBinding
    private var enable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etWordForSearch.visibility = View.GONE

        binding.btnGetImage.setOnClickListener {
            getOnRandomImage()
        }

        binding.etWordForSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener getOnRandomImage()
            }
            return@setOnEditorActionListener false
        }

        binding.checkBox.setOnClickListener {
            enableTextView()
        }

        binding.btnSetBackgroundImage.setOnClickListener {
            // TODO save image in storage and intent directory for MainActivity
        }

    }

    fun getOnRandomImage(): Boolean {

        val keyword = binding.etWordForSearch.text.toString()

        if (binding.checkBox.isChecked && keyword.isBlank()) {
            binding.etWordForSearch.error = R.string.not_input_text.toString()
            return true
        }

        val inputWord = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        binding.progressBar.visibility = View.VISIBLE

        Glide.with(this@ImageSearchActivity)
            .load(
                URL_IMAGE +
                        if (binding.checkBox.isChecked) "?$inputWord" else ""
            )
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.ivImage)
        return false

    }

    fun enableTextView() = with(binding) {
        if (checkBox.isChecked) {
            etWordForSearch.visibility = View.VISIBLE
        } else {
            etWordForSearch.setText("")
            etWordForSearch.visibility = View.GONE
        }
    }

    private fun launchResult() {
        val intent = Intent()
        intent.putExtra(KEY_IMAGE_SEARCH, "ok") //TODO write directory image storage
        setResult(RESULT_OK, intent)
        finish()
    }

}