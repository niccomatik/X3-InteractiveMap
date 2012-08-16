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

import javax.swing.JComboBox;



/**
 * Describes a binding builder that holds a ComboBoxModel
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
public interface ComboBoxBindingBuilder {


    /**
     * Binds this builder's ComboBoxModel to the given combo box.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bind(countryComboBoxModel).<b>to</b>(countryCombo);</tt>
     *
     * @param comboBox   the combo box where the combo box model is set
     *    as both data and selection model.
     *
     * @throws NullPointerException if {@code comboBox} is {@code null}
     */
    void to(JComboBox comboBox);


    /**
     * Binds this builder's ComboBoxModel to the given combo box
     * where {@code null} values are mapped to a special null element
     * that is displayed using the given {@code nullElementText}.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bind(countryComboBoxModel).<b>to</b>(countryCombo, "---");</tt>
     *
     * @param comboBox   the combo box where the combo box model is set
     *    as both data and selection model.
     * @param nullElementText   the text that represents the {@code null} value
     *    in the combo box
     *
     * @throws NullPointerException if {@code comboBox} is {@code null}
     */
    void to(JComboBox comboBox, String nullElementText);


}

