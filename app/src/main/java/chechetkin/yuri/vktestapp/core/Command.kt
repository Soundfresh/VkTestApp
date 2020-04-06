package chechetkin.yuri.vktestapp.core

interface Command<REQUEST, RESPONSE> {

    fun request(request: REQUEST): RESPONSE
}