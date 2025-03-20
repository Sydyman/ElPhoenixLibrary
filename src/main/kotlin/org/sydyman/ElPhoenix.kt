package org.sydyman

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Universal Phoenix class that "revives" an object after it is garbage collected.
 * Supports WeakReference and SoftReference.
 * Can revive into different types like List, Map, or Set.
 *
 * @param initialValue The initial value to be stored.
 * @param resurrectionTypeMap A map of functions that create different resurrected objects.
 * @param referenceType Determines whether to use WeakReference or SoftReference.
 * @param resurrectionMode Determines into which type the object should be revived.
 */
open class ElPhoenix<T : Any>(
    initialValue: T,
    private val resurrectionTypeMap: Map<ResurrectionMode, () -> T>,
    private val referenceType: ReferenceType = ReferenceType.WEAK, // Default to WeakReference
    private var resurrectionMode: ResurrectionMode = ResurrectionMode.SINGLE // Default resurrection mode
) {
    // Enum for reference types
    enum class ReferenceType {
        WEAK, SOFT
    }

    // Enum for resurrection types
    enum class ResurrectionMode {
        SINGLE, LIST, SET, MAP
    }

    // Generic reference holder
    private var reference = createReference(initialValue)

    /**
     * Creates a reference based on the selected type.
     */
    private fun createReference(value: T) = when (referenceType) {
        ReferenceType.WEAK -> WeakReference(value)
        ReferenceType.SOFT -> SoftReference(value)
    }

    /**
     * Retrieves the current value.
     * If the object has been garbage collected, it "revives" into a new instance.
     */
    fun get(): T {
        return reference.get() ?: run {
            try {
                val newValue = resurrectionTypeMap[resurrectionMode]?.invoke()
                    ?: throw IllegalArgumentException("No resurrection function defined for mode: $resurrectionMode")
                reference = createReference(newValue)
                newValue
            } catch (e: Exception) {
                throw RuntimeException("Failed to revive object: ${e.message}", e)
            }
        }
    }

    /**
     * Changes the resurrection mode.
     */
    fun setResurrectionMode(mode: ResurrectionMode) {
        this.resurrectionMode = mode
    }

    /**
     * Forces the object to be cleared (simulating garbage collection).
     */
    fun clear() {
        reference.clear()
    }
}