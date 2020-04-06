package chechetkin.yuri.vktestapp.core

interface PopulatableView<T> {
    fun populate(model: T)
}