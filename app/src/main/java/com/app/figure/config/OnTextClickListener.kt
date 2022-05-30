package com.app.figure.config

interface OnTextClickListener {
    fun onTextClick(id : String)
}

interface OnClickGetItemCart{
    fun getItem(i : Int)
    fun getMoney(i : Int)
}