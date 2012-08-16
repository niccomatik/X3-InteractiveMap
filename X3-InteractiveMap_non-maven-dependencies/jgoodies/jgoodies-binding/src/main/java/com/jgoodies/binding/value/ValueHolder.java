/*
 * Copyright (c) 2002-2012 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.binding.value;

/**
 * A simple {@link com.jgoodies.binding.value.ValueModel} implementation
 * that holds a generic value.
 * If the value changes, a {@code PropertyChangeEvent} is fired
 * that can be observed using a {@code PropertyChangeListener}.<p>
 *
 * Differences in the old and new value can be checked either using
 * {@code ==} or {@code #equals}. The unbound property
 * <em>identityCheckEnabled</em> determines which mechanism is used
 * to check for changes in {@code #setValue(Object)}.
 * This check can be overridden for individual changes by the boolean
 * parameter in {@code #setValue(Object, boolean)}.<p>
 *
 * <strong>Constraints:</strong> The value is of type {@code Object}.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.10 $
 *
 * @see     ValueModel
 * @see     java.beans.PropertyChangeEvent
 * @see     java.beans.PropertyChangeListener
 * @see     com.jgoodies.binding.beans.ExtendedPropertyChangeSupport
 */
public final class ValueHolder extends AbstractValueModel {

    /**
     * Holds a value of type {@code Object} that is to be observed.
     */
    private Object value;

    /**
     * Describes whether a value change event shall be fired if the
     * old and new value are different. If {@code true} the old
     * and new value are compared with {@code ==}. If {@code false}
     * the values are compared with {@code #equals}.
     *
     * @see #setValue(Object, boolean)
     * @see com.jgoodies.binding.beans.Model#firePropertyChange(String, Object, Object, boolean)
     * @see com.jgoodies.binding.beans.ExtendedPropertyChangeSupport
     */
    private boolean checkIdentity;


    // Instance Creation ****************************************************

    /**
     * Constructs a {@code ValueHolder} with {@code null}
     * as initial value.
     */
    public ValueHolder() {
        this(null);
    }

    /**
     * Constructs a {@code ValueHolder} with the given initial value.
     * By default the old and new value are compared using {@code #equals}
     * when firing value change events.
     *
     * @param initialValue   the initial value
     */
    public ValueHolder(Object initialValue) {
        this(initialValue, false);
    }

    /**
     * Constructs a {@code ValueHolder} with the given initial value.
     *
     * @param initialValue     the initial value
     * @param checkIdentity    true to compare the old and new value using
     *     {@code ==}, false to use {@code #equals}
     */
    public ValueHolder(Object initialValue, boolean checkIdentity) {
        value = initialValue;
        this.checkIdentity = checkIdentity;
    }

    /**
     * Constructs a {@code ValueHolder} with the specified initial
     * boolean value that is converted to a {@code Boolean} object.
     *
     * @param initialValue   the initial boolean value
     */
    public ValueHolder(boolean initialValue) {
        this(Boolean.valueOf(initialValue));
    }

    /**
     * Constructs a {@code ValueHolder} with the specified initial
     * double value that is converted to a {@code Double} object.
     *
     * @param initialValue   the initial double value
     */
    public ValueHolder(double initialValue) {
        this(Double.valueOf(initialValue));
    }

    /**
     * Constructs a {@code ValueHolder} with the specified initial
     * float value that is converted to a {@code Float} object.
     *
     * @param initialValue   the initial float value
     */
    public ValueHolder(float initialValue) {
        this(Float.valueOf(initialValue));
    }

    /**
     * Constructs a {@code ValueHolder} with the specified initial
     * int value that is converted to an {@code Integer} object.
     *
     * @param initialValue   the initial int value
     */
    public ValueHolder(int initialValue) {
        this(Integer.valueOf(initialValue));
    }

    /**
     * Constructs a {@code ValueHolder} with the specified initial
     * long value that is converted to a {@code Long} object.
     *
     * @param initialValue   the initial long value
     */
    public ValueHolder(long initialValue) {
        this(Long.valueOf(initialValue));
    }


    // ValueModel Implementation ********************************************

    /**
     * Returns the observed value.
     *
     * @return the observed value
     */
    @Override
	public Object getValue() {
        return value;
    }

    /**
     * Sets a new value. Fires a value change event if the old and new
     * value differ. The difference is tested with {@code ==} if
     * {@code isIdentityCheckEnabled} answers {@code true}.
     * The values are compared with {@code #equals} if the
     * identity check is disabled.
     *
     * @param newValue  the new value
     */
    @Override
	public void setValue(Object newValue) {
        setValue(newValue, isIdentityCheckEnabled());
    }


    // Optional Support for Firing Events on Identity Change ***************

    /**
     * Answers whether this ValueHolder fires value change events if
     * and only if the old and new value are not the same.
     *
     * @return {@code true} if the old and new value are compared
     *     using {@code ==}, {@code false} if the values
     *     are compared using {@code #equals}
     */
    public boolean isIdentityCheckEnabled() {
        return checkIdentity;
    }

    /**
     * Sets the comparison that is used to check differences between
     * the old and new value when firing value change events.
     * This is the default setting that is used when changing the value via
     * {@code #setValue(Object)}. You can override this default setting
     * by changing a value via {@code #setValue(Object, boolean)}.
     *
     * @param checkIdentity    true to compare the old and new value using
     *     {@code ==}, false to use {@code #equals}
     */
    public void setIdentityCheckEnabled(boolean checkIdentity) {
        this.checkIdentity = checkIdentity;
    }


    /**
     * Sets a new value. Fires a value change event if the old and new
     * value differ. The difference is tested with {@code ==} if
     * {@code checkIdentity} is {@code true}. The values are
     * compared with {@code #equals} if the {@code checkIdentiy}
     * parameter is set to {@code false}.<p>
     *
     * Unlike general bean property setters, this method does not fire
     * an event if the old and new value are {@code null}.
     *
     * @param newValue         the new value
     * @param checkIdentity    true to compare the old and new value using
     *     {@code ==}, false to use {@code #equals}
     */
    public void setValue(Object newValue, boolean checkIdentity) {
        Object oldValue = getValue();
        if (oldValue == newValue)
            return;
        value = newValue;
        fireValueChange(oldValue, newValue, checkIdentity);
    }



}
