package dev.joseluisgs.personas.services.cache

interface Cache<K, V> {
    fun get(key: K): V?
    fun put(key: K, value: V)
    fun remove(key: K)
    fun clear()
    fun size(): Int
}