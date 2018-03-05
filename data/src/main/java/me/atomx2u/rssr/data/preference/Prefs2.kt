package me.atomx2u.rssr.data.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Prefs2(context: Context) {
    private val prefs = context.getSharedPreferences("name", MODE_PRIVATE)

    var count by Delegate.Int("count")

    companion object {
        const val name = "playground"

        private sealed class Delegate<T> : ReadWriteProperty<Prefs2, T> {

            /** Int */
            class Int(private val key: kotlin.String) : Delegate<kotlin.Int>() {
                override fun getValue(thisRef: Prefs2, property: KProperty<*>): kotlin.Int {
                    return thisRef.prefs.getInt(key, 0)
                }

                override fun setValue(thisRef: Prefs2, property: KProperty<*>, value: kotlin.Int) {
                    thisRef.prefs.edit { putInt(key, value) }
                }
            }

            /** Float */
            class Float(private val key: kotlin.String) : Delegate<kotlin.Float>() {
                override fun getValue(thisRef: Prefs2, property: KProperty<*>): kotlin.Float {
                    return thisRef.prefs.getFloat(key, 0.0f)
                }

                override fun setValue(thisRef: Prefs2, property: KProperty<*>, value: kotlin.Float) {
                    thisRef.prefs.edit { putFloat(key, value) }
                }

            }

            /** String */
            class String(private val key: kotlin.String) : Delegate<kotlin.String>() {
                override fun getValue(thisRef: Prefs2, property: KProperty<*>): kotlin.String {
                    return thisRef.prefs.getString(key, "")
                }

                override fun setValue(thisRef: Prefs2, property: KProperty<*>, value: kotlin.String) {
                    thisRef.prefs.edit { putString(key, value) }
                }

            }

            /** Util */
            fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> Unit) {
                edit().apply { block() }.apply()
            }
        }
    }
}