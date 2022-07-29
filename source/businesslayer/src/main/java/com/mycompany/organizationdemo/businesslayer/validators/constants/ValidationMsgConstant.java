package com.mycompany.organizationdemo.businesslayer.validators.constants;

public class ValidationMsgConstant {

    public static final String COLLECTION_IS_NULL_COLLECTION_NAME = "Collection is null.  (CollectionName=\"%1$s\")";

    public static final String IS_NULL_ITEM = "Object is null.  (ObjectName=\"%1$s\")";

    public static final String OBJECT_PROPERTY_IS_NULL = "Property is null.  (ObjectName=\"%1$s\", PropertyName=\"%2$s\")";

    public static final String OBJECT_OBJECT_PROPERTY_IS_NULL =
            "Property is null. (ObjectName=\"%1$s\"-\"%2$s\", PropertyName=\"%3$s\")";

    public static final String PROPERTY_VALUE_NULL_OR_EMPTY = "Property value is null or empty. (PropertyName=\"%1$s\")";

    public static final String PROPERTY_VALUE_LESS_THAN_ZERO = "Property value is less than zero. (PropertyName=\"%1$s\")";

    public static final String OBJECT_PROPERTY_VALUE_NULL_OR_EMPTY =
            "Property value is null or empty. (ObjectName=\"%1$s\", PropertyName=\"%2$s\")";

    public static final String LENGTH_TOO_MANY =
            "Property value contains too many characters.  (PropertyName=\"%1$s\", InputValue=\"%2$s\", InputLength=\"%3$s\", MaxLength=\"%4$s\")";

    public static final String ENUM_VALUE_CANNOT_BE_VALUE =
            "Enum cannot be current value.  (EnumName=\"%1$s\", InputValue=\"%2$s\")";

    public static final String FILE_DOES_NOT_EXIST_ERROR_MESSAGE = "File does not exist. (FileName=\"%1$s\")";

    public static final String PROPERTY_VALUE_MUST_BE_GREATER_THAN_ZERO =
            "Value must be greater than zero.  (ObjectName=\"%1$s\", PropertyName=\"%2$s\", CurrentValue=\"%3$s\")";

    public static final String OBJECT_OBJECT_PROPERTY_IS_LESS_THAN_ZERO =
            "Property value must be greater than or equal to zero. (ObjectName=\"%1$s\"-\"%2$s\", PropertyName=\"%3$s\", Value=\"%4$s\")";

    public static final String OBJECT_OBJECT_PROPERTY_IS_LESS_THAN_EQUAL_ZERO =
            "Property value must be greater than zero. (ObjectName=\"%1$s\"-\"%2$s\", PropertyName=\"%3$s\", Value=\"%4$s\")";

    public static final String OBJECT_OBJECT_PROPERTY_MUST_BE_LESS_THAN_ZERO =
            "Property value must be less than zero. (ObjectName=\"%1$s\"-\"%2$s\", PropertyName=\"%3$s\", Value=\"%4$s\")";

    public static final String OBJECT_OBJECT_PROPERTY_VALUE_NULL_OR_EMPTY =
            "Property value is null or empty. (ObjectName=\"%1$s\"-\"%2$s\", PropertyName=\"%3$s\")";

    public static final String PROPERTY_VALUE_IS_NOT_VALID_DOMAIN_NAME =
            "Value is not a valid domain name. (ObjectName=\"%1$s\", PropertyName=\"%2$s\", Value=\"%3$s\")";

    public static final String PROPERTY_VALUE_CONTAINS_INVALID_CHARACTERS =
            "Value contains invalid characters. (ObjectName=\"%1$s\", PropertyName=\"%2$s\", Value=\"%3$s\")";

    public static final String PROPERTY_VALUES_CANNOT_BE_EQUAL =
            "Values cannot be equal. (ObjectName1=\"%1$s\", PropertyName1=\"%2$s\", Value1=\"%3$s\", ObjectName2=\"%4$s\", PropertyName2=\"%5$s\", Value2=\"%6$s\")";

    public static final String SCALAR_PROPERTY_MUST_BE_EXACT_VALUE =
            "Scalar Property must be exact value. (ObjectName=\"%1$s\", SurrogateKey=\"%2$s\", PropertyName=\"%3$s\", RequiredValue=\"%4$s\", CurrentValue=\"%5$s\")";

    public static final String PARENT_CHILD_SCALAR_MISMATCH =
            "Parent Child mismatch. (ParentObjectName=\"%1$s\", SurrogateKey=\"%2$s\", ParentPropertyName=\"%3$s\", ParentValue=\"%4$s\", ChildObjectName=\"%5$s\", ChildPropertyName=\"%6$s\", \"ChildValue=\"%7$s\")";
}
