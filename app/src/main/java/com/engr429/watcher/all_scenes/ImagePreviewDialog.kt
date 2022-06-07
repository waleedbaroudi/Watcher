package com.engr429.watcher.all_scenes

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.engr429.watcher.R
import com.engr429.watcher.databinding.DialogImagePreviewBinding

// Using in the ActivityExt
class ImagePreviewDialog() : DialogFragment() {

    private lateinit var binding: DialogImagePreviewBinding
    private var image: Drawable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogImagePreviewBinding.inflate(inflater)
        binding.btnDismiss.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    fun show(manager: FragmentManager, drawable: Drawable) {
        if (isVisible) return
        super.show(manager, this::class.java.canonicalName)
        image = drawable
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setWindowAnimations(R.style.FullScreenDialogAnim)
        binding.image.setImageDrawable(image)
    }
}