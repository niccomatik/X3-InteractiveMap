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

import javax.swing.JList;
import javax.swing.JTable;


/**
 * Describes a binding builder that holds a ListModel and ListSelectionModel
 * that can be bound to tables and lists.<p>
 *
 * <strong>Examples:</strong>
 * <pre>
 * binder.bind(albumListModel,   albumListSelectionModel)  .to(albumTable);
 * binder.bind(contactListModel, contactListSelectionModel).to(contactList);
 * </pre>
 * @author  Karsten Lentzsch
 * @version $Revision: 1.2 $
 *
 * @since 2.3
 */
public interface ListBindingBuilder {

    /**
     * Sets this builder's ListModel and ListSelectionModel in the given list.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bind(contactSelectionInList).<b>to</b>(contactList);</tt>
     *
     * @param list   the component to set the data and selection model in
     *
     * @throws NullPointerException if {@code table} is {@code null}
     */
    void to(JList list);


    /**
     * Sets this builder's ListModel and ListSelectionModel in the given table.
     * As a precondition, the table's model must implement ListModelBindable,
     * so this builder's ListModel can be set in the TableModel.<p>
     *
     * <strong>Example:</strong><br>
     * <tt>binder.bind(contactSelectionInList).<b>to</b>(contactTable);</tt>
     *
     * @param table   the component to set the data and selection model in
     *
     * @throws NullPointerException if {@code table} is {@code null}
     * @throws IllegalArgumentException if {@code table}'s TableModel does not
     *    implement the ListModelBindable interface
     */
    void to(JTable table);


}

