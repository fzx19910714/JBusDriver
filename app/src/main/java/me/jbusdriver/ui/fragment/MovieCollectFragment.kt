package me.jbusdriver.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.afollestad.materialdialogs.MaterialDialog
import me.jbusdriver.common.toast
import me.jbusdriver.mvp.LinkListContract
import me.jbusdriver.mvp.presenter.MovieCollectPresenterImpl
import me.jbusdriver.ui.data.CollectManager

/**
 * Created by Administrator on 2017/7/17 0017.
 */
class MovieCollectFragment : MovieListFragment(), LinkListContract.LinkListView {

    override fun createPresenter() = MovieCollectPresenterImpl()

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setOnItemLongClickListener { adapter, _, position ->
            this@MovieCollectFragment.adapter.getData().getOrNull(position)?.let {
                MaterialDialog.Builder(viewContext)
                        .title(it.title)
                        .items(listOf("取消收藏"))
                        .itemsCallback { _, _, _, text ->
                            if (CollectManager.removeCollect(it)) {
                                viewContext.toast("取消收藏成功")
                                adapter.data.removeAt(position)
                                adapter.notifyItemRemoved(position)
                            } else {
                                viewContext.toast("已经取消了")
                            }
                        }
                        .show()
            }
            true
        }
    }

    companion object {
        fun newInstance() = MovieCollectFragment()
    }
}