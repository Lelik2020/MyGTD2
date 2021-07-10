package ru.kau.mygtd2.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeOfInfo {

    QUESTION(1),
    IDEA(2),
    INFO(3);

    public final int Value;
    TypeOfInfo(int value)
    {
        Value = value;
    }

    private static final Map<Integer, TypeOfInfo> _map = new HashMap<Integer, TypeOfInfo>();
    private static final Map<TypeOfInfo, Integer> _map2 = new HashMap<TypeOfInfo, Integer>();
    static
    {
        for (TypeOfInfo typeOfInfo : TypeOfInfo.values()) {
            _map.put(typeOfInfo.Value, typeOfInfo);
            _map2.put(typeOfInfo, typeOfInfo.Value);
        }
    }

    public static TypeOfInfo from(int value)
    {
        return _map.get(value);
    }

    public static int to(TypeOfInfo typeOfInfo){
        return _map2.get(typeOfInfo);
    }

}
