package me.atomx2u.rssr.mvp

/**
 * View Action 的抽象
 * */
interface MvpView

/**
 * 业务逻辑和设备行为的抽象
 * */
interface MvpPresenter {
    fun create()
    fun resume()
    fun pause()
    fun destroy()
    fun back()
}

interface AsDialog {
    fun dismiss()
}

/**
 * P, V 联结的抽象
 * */
interface MvpNexus {
    fun onBack()
}