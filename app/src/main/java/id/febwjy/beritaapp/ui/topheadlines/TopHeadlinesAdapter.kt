package id.febwjy.beritaapp.ui.topheadlines

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.febwjy.beritaapp.R
import id.febwjy.beritaapp.data.model.NewsResponse
import id.febwjy.beritaapp.databinding.ItemTopheadilinesBinding
import id.febwjy.beritaapp.utils.DateUtils

/**
 * Created by Febby Wijaya on 15/11/2023.
 */
class TopHeadlinesAdapter (
    private val newsList: MutableList<NewsResponse.Articles>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<TopHeadlinesAdapter.ViewHolder>(){

    class OnClickListener (val clickListener: (news: NewsResponse.Articles) -> Unit) {
        fun onClick(news: NewsResponse.Articles) = clickListener(news)
    }

    fun updateList(mMovieList: List<NewsResponse.Articles>) {
        newsList.clear()
        newsList.addAll(mMovieList)
        notifyDataSetChanged()
    }

    inner class ViewHolder (private val itemBinding: ItemTopheadilinesBinding, private val context: Context)
        : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(news: NewsResponse.Articles) {
            if (news.title != null) {
                itemBinding.txtNewsTitle.text = news.title
                itemBinding.txtNewsDesc.text = news.description
                itemBinding.txtNewsDate.text = DateUtils.getFormattedDate(news.publishedAt.toString())
            }
            if (news.urlToImage != null) {
                var uri: Uri = Uri.parse(news.urlToImage)
                Glide.with(context).load(uri)
                    .into(itemBinding.imgNews)
                itemBinding.imgNews.visibility = View.VISIBLE
            } else {
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.img_placeholder))
                    .into(itemBinding.imgNews)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopHeadlinesAdapter.ViewHolder {
        val view = ItemTopheadilinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val context = parent.context
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: TopHeadlinesAdapter.ViewHolder, position: Int) {
        holder.bind(news = newsList[position])
        holder.itemView.setOnClickListener {
            newsList[position].let { news -> onClickListener.onClick(news) }
        }
    }

    override fun getItemCount() = newsList.size
}