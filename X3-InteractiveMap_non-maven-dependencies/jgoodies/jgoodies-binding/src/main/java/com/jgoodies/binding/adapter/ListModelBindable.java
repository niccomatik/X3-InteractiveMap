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

package com.jgoodies.binding.adapter;

import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.jgoodies.binding.list.SelectionInList;

/**
 * Describes object access to a ListModel. Primarily used to describe
 * TableModel implementations that have an underlying ListModel for
 * the data rows.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.1 $
 *
 * @see Bindings#bind(JTable, SelectionInList)
 * @see Bindings#bind(JTable, ListModel, ListSelectionModel)
 *
 * @since 2.2
 */
public interface ListModelBindable {


    /**
     * @return the underlying ListModel
     */
    ListModel getListModel();


    /**
     * Sets the given ListModel as new underlying ListModel.
     * Implementations will typically need to remove ListDataListeners
     * from the previously set ListModel and add them to the given ListModel.
     *
     * @param newListModel   the ListModel to be set
     */
    void setListModel(ListModel newListModel);

}
