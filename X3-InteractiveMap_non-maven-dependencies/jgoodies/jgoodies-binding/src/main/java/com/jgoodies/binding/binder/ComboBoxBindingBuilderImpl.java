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

import static com.jgoodies.common.base.Preconditions.checkNotNull;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.jgoodies.binding.adapter.Bindings;



/**
 * A binding builder that holds a ComboBoxModel
 * that can be bound to combo boxes.<p>
 *
 * <strong>Examples:</strong>
 * <pre>
 * binder.bind(countryComboBoxModel).to(countryCombo);
 * binder.bind(countryComboBoxModel).to(countryFilterCombo, "none");
 * </pre>
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @since 2.7
 */
public class ComboBoxBindingBuilderImpl implements ComboBoxBindingBuilder {


    private final ComboBoxModel comboBoxModel;


    // Instance Creation ******************************************************

    /**
     * Creates a ComboBoxBindingBuilderImpl for the given combo box model.
     *
     * @param comboBoxModel    provides both the data and the selection
     *
     * @throws NullPointerException if {@code comboBoxModel} is {@code null}
     */
    public ComboBoxBindingBuilderImpl(ComboBoxModel comboBoxModel) {
        this.comboBoxModel =
            checkNotNull(comboBoxModel, "The ComboBoxModel must no be null.");
    }


    // SelectionInListBindingBuilder Implementation ***************************

    @Override
	public void to(JComboBox comboBox) {
        Bindings.bind(comboBox, comboBoxModel);
    }


    @Override
	public void to(JComboBox comboBox, String nullText) {
        Bindings.bind(comboBox, comboBoxModel, nullText);
    }


    // Subclass Utils *********************************************************

    /**
     * @return this builder's SelectionInList
     */
    protected final ComboBoxModel getComboBoxModel() {
        return comboBoxModel;
    }


}

