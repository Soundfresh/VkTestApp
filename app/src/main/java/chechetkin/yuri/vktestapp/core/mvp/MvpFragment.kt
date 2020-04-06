package chechetkin.yuri.vktestapp.core.mvp

import androidx.fragment.app.Fragment

abstract class MvpFragment<PRESENTER : BasePresenter<out BaseView>>: Fragment() {

    abstract val presenter: PRESENTER
}