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

package com.jgoodies.binding.binder;

import com.jgoodies.binding.beans.BeanAdapter;


/**
 * Creates Bindables that can be bound to components.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @param <B>   the type of the bean
 *
 * @since 2.3
 */
public class BeanBinderImpl<B> // extends ObservableBean>
    extends ObjectBinderImpl
    implements BeanBinder {


    private final B target;
    private BeanAdapter beanAdapter;


    // Instance Creation ******************************************************

    /**
     * Constructs a BeanBinder for the given bean.
     *
     * @param target   used to bind bean properties via {@link #bindProperty(String)}
     */
    public BeanBinderImpl(B target) {
        this.target = target;
    }


    // BeanBinder Implementation **********************************************

    @Override
	public ValueModelBindingBuilder bindProperty(String propertyName){
        return new ValueModelBindingBuilderImpl(
                getBeanAdapter().getValueModel(propertyName),
                propertyName);
    }


    // Helper Code ************************************************************

    /**
     * @return this binder's bean
     */
    protected B getTarget() {
        return target;
    }


    /**
     * Lazily creates and returns a {@link BeanAdapter} on this binder's
     * bean that observes the bean properties.
     *
     * @return the BeanAdapter
     */
    protected BeanAdapter getBeanAdapter() {
        if (beanAdapter == null) {
            beanAdapter = new BeanAdapter<B>(target, true);
        }
        return beanAdapter;
    }



}

