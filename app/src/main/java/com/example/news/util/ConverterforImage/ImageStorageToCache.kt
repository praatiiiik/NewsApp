package com.example.news.util.ConverterforImage

import android.graphics.Bitmap
import android.util.LruCache

class ImageStorageToCache private constructor() {

    private object HOLDER {
        val INSTANCE = ImageStorageToCache()
    }

    companion object {
        val instance: ImageStorageToCache by lazy { HOLDER.INSTANCE }
    }

    val lru: LruCache<Any, Any>

    init {

        lru = LruCache(1024)

    }

    fun saveBitmapToCahche( bitmap: Bitmap,key: String) {

        try {
            ImageStorageToCache.instance.lru.put(key, bitmap)
        } catch (e: Exception) {
        }

    }

    fun retrieveBitmapFromCache(key: String): Bitmap? {

        try {
            return ImageStorageToCache.instance.lru.get(key) as Bitmap?
        } catch (e: Exception) {
        }

        return null
    }

    fun RemoveBitmapToCahche(key: String, bitmap: Bitmap) {

        try {
            ImageStorageToCache.instance.lru.remove(key)
        } catch (e: Exception) {
        }

    }
}