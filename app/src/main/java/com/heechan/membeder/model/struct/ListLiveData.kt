package com.heechan.membeder.model.struct

import androidx.lifecycle.LiveData

class ListLiveData<T : Any>(items: List<T>) : LiveData<List<T>>(items) {
    private val _value: MutableList<T> get() = super.getValue()?.toMutableList() ?: mutableListOf()
    var value : List<T>
        get() = _value
        set(value) {
            _value.clear()
            _value.addAll(value)
            super.setValue(_value)
        }

    fun add(item: T) {
        _value.add(item)
        super.setValue(_value)
    }

    fun remove(item: T) {
        _value.remove(item)
        super.setValue(_value)
    }

    fun clear() {
        _value.clear()
        super.setValue(_value)
    }

    val size: Int
        get() = super.getValue()?.size ?: 0

    init {
        _value.addAll(items)
        super.setValue(items)
    }

    constructor() : this(listOf<T>())
}