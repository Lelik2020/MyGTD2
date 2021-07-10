package ru.kau.mygtd2.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum TypeOfTask {
    EPIC(1),
    STORY(2),
    TASK(3),
    BUG(4),
    QUESTION(5);

    public final int Value;
    TypeOfTask(int value)
    {
        Value = value;
    }

    private static final Map<Integer, TypeOfTask> _map = new HashMap<Integer, TypeOfTask>();
    private static final Map<TypeOfTask, Integer> _map2 = new HashMap<TypeOfTask, Integer>();
    static
    {
        for (TypeOfTask typeOfTask : TypeOfTask.values()) {
            _map.put(typeOfTask.Value, typeOfTask);
            _map2.put(typeOfTask, typeOfTask.Value);
        }
    }

    public static TypeOfTask from(int value)
    {
        return _map.get(value);
    }

    public static int to(TypeOfTask typeOfTask){
        return _map2.get(typeOfTask);
    }

}
