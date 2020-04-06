package chechetkin.yuri.vktestapp.core.mvp

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var view: V? = null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun isAttached(): Boolean {
        return view != null
    }
}