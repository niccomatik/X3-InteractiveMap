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

import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;


/**
 * A binder that can build action bindings from Actions,
 * data bindings from ValueModels, and list bindings from
 * ListModel/ListSelectionModel or a SelectionInList.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 *
 * @since 2.3
 */
public class ObjectBinderImpl implements ObjectBinder {


    @Override
	public ActionBindingBuilder bind(Action action) {
        return new ActionBindingBuilderImpl(action);
    }


    @Override
    public ComboBoxBindingBuilder bind(ComboBoxModel comboBoxModel) {
    	return new ComboBoxBindingBuilderImpl(comboBoxModel);
    }


    @Override
    public ListBindingBuilder bind(Object[] data, ListSelectionModel selectionModel) {
    	return new ListBindingBuilderImpl(data, selectionModel);
    }


//    @Override
//    public ListBindingBuilder bind(List<?> data, ListSelectionModel selectionModel) {
//    	return new ListBindingBuilderImpl(data, selectionModel);
//    }
    
    
    @Override
	public ListBindingBuilder bind(ListModel dataModel, ListSelectionModel selectionModel) {
        return new ListBindingBuilderImpl(dataModel, selectionModel);
    }


    @Override
	public SelectionInListBindingBuilder bind(SelectionInList<?> selectionInList) {
        return new SelectionInListBindingBuilderImpl(selectionInList);
    }


    @Override
	public ValueModelBindingBuilder bind(ValueModel valueModel) {
        return new ValueModelBindingBuilderImpl(valueModel);
    }


}

