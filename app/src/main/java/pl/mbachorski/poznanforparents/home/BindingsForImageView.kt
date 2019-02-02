package pl.mbachorski.poznanforparents.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
  if (url != null) {
    Glide.with(imageView.context)
      .load(url)
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(imageView)
  }
}
