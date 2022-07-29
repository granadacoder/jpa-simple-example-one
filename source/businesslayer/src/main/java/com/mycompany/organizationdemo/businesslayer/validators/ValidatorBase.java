package com.mycompany.organizationdemo.businesslayer.validators;

import java.util.Collection;

public abstract class ValidatorBase<T> {
    public abstract void validateSingle(T item);

    public abstract void validateCollection(Collection<T> items);
}
