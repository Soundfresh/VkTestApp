package chechetkin.yuri.vktestapp.core

interface Mapper<FROM, TO> {
    fun map(from: FROM): TO
}