package chechetkin.yuri.vktestapp.core.mvp

interface BasePresenter<V : BaseView> {
    fun attach(view: V)
    fun detach()
    fun isAttached(): Boolean
}