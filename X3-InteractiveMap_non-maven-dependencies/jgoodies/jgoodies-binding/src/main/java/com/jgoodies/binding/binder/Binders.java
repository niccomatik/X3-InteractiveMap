/*
 * Copyright (c) 2012 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * This software is the proprietary information of JGoodies Karsten Lentzsch.
 * Use is subject to license terms.
 *
 */

package com.jgoodies.binding.binder;

import com.jgoodies.binding.internal.IActionPresentationModel;
import com.jgoodies.binding.internal.IPresentationModel;
import com.jgoodies.common.internal.IActionBean;
import com.jgoodies.common.internal.IActionObject;



/**
 * Creates binders for Objects, Beans, PresentationModels, and
 * ActionObject, ActionBeans, and ActionPresentationModels.
 * Since the latter three classes do not ship with the JGoodies Binding,
 * these are described by interfaces.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.3 $
 * 
 * @since 2.7
 */
public final class Binders {


    private Binders() {
        // Overrides default constructor; prevents instantiation.
    }


    /**
     * Creates and returns a general binder for objects that can bind Actions,
     * ListModel + ListSelectionModel, ValueModels, and SelectionInLists.
     *
     * @return the created binder
     */
    public static ObjectBinder binder() {
        return new ObjectBinderImpl();
    }


    /**
     * Creates and returns a binder that adds the capability to bind
     * Actions that are looked up by an Action name to the general binder
     * returned by {@link #binder()}.
     *
     * @param object    provides Actions for Action names
     * @return the created binder
     */
    public static ActionObjectBinder binderFor(IActionObject object) {
        return new ActionObjectBinderImpl<IActionObject>(object);
    }


    /**
     * Creates and returns a binder for beans that can bind bean
     * properties as well as the object bindings: Actions,
     * ListModel + ListSelectionModel, ValueModels, and SelectionInLists.<p>
     * 
     * The parameter type is Object, not ObservableBean or ObservableBean2,
     * because any Object may be a bean. If {@code bean} is not a bean,
     * runtime exceptions will be thrown during the execution.
     *
     * @param bean   the bean used to bind bean properties
     * @return the created binder
     */
    public static BeanBinder binderFor(Object bean) {
        return new BeanBinderImpl(bean);
    }


    /**
     * Creates and returns a binder that adds the capability to bind
     * Actions that are looked up by an Action name to the binder
     * returned by {@link #binderFor(Object)}.
     *
     * @param bean   the bean used to bind bean properties and to look up Actions
     * @return the created binder
     */
    public static ActionBeanBinder binderFor(IActionBean bean) {
        return new ActionBeanBinderImpl(bean);
    }


    /**
     * Creates and returns a binder for presentation models that can bind
     * bean properties, (synthetic) properties of the presentation model,
     *  as well as the object bindings: Actions, ListModel + ListSelectionModel,
     *  ValueModels, and SelectionInLists.
     *
     * @param model    the presentation model that holds a bean (used to bind
     *    bean properties) and that may provide its own synthetic properties
     * @return the created binder
     */
    public static PresentationModelBinder binderFor(IPresentationModel<?> model) {
        return new PresentationModelBinderImpl(model);
    }


    /**
     * Creates and returns a binder that adds the capability to bind
     * Actions that are looked up by an Action name to the binder
     * returned by {@link #binderFor(IPresentationModel)}.
     *
     * @param model    the presentation model that holds a bean (used to bind
     *    bean properties), and that may provide its own synthetic properties,
     *    and that provides Actions for Action names
     * @return the created binder
     */
    public static ActionPresentationModelBinder binderFor(IActionPresentationModel<?> model) {
        return new ActionPresentationModelBinderImpl(model);
    }


}

