package org.ajls.lib.advanced;

public class EnumA<T> {
//    EnumA() {
//        T[2];  // t is an enum
//    }
    Class<T> enumClass;
    public EnumA(Class<T> enumClass) {
        this.enumClass = enumClass;
    }
}
