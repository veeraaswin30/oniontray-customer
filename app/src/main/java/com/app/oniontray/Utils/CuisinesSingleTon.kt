package com.app.oniontray.Utils

import android.app.Application
import java.util.*
import kotlin.collections.ArrayList

class CuisinesSingleton() {


    private var mCuisinelist: ArrayList<String> = arrayListOf()
    private var mfilterList: ArrayList<String> = arrayListOf()



    companion object {
        var mInstance: CuisinesSingleton? = null

        fun getInstance(): CuisinesSingleton {
            if (mInstance == null)
                mInstance = CuisinesSingleton()

            return mInstance!!
        }


         var orderBy: String? = ""
            get() = field
            set(value) {
                field = value
            }
         var SortBy: String? = ""
            get() = field
            set(value) {
                field = value
            }
         var paymentBy : String = ""
            get() = field
            set(value) {
                field = value
            }

    }


    // retrieve array from anywhere
    fun getArray(): ArrayList<String>? {
        return this.mCuisinelist
    }

    fun getFilterArray(): ArrayList<String>? {
        return this.mfilterList
    }

    //Add element to array
    fun addToArray(value: String) {
        mCuisinelist!!.add(value)
    }

    fun addToFilterArray(value: String) {
        mfilterList!!.add(value)
    }

    //Add element to array
    fun removeToArr(value: String) {
        mCuisinelist!!.remove(value)
    }

    fun removeToFilterArr(value: String) {
        mfilterList!!.remove(value)
    }

    //Remove all the array
    fun mRemoveAll() {
        mCuisinelist.clear()
    }

    fun mRemoveAllFilter() {
        mfilterList.clear()
    }




}