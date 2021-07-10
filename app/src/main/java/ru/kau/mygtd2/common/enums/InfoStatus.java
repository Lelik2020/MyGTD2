package ru.kau.mygtd2.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum InfoStatus {

    NEW(0),
    PROCESSED(1),
    ARCHIE(2);

    public final int Value;

    InfoStatus(int value)
    {
        Value = value;
    }

    private static final Map<Integer, Status> _map = new HashMap<Integer, Status>();
    private static final Map<Status, Integer> _map2 = new HashMap<Status, Integer>();
    static
    {
        for (Status status : Status.values()) {
            _map.put(status.Value, status);
            _map2.put(status, status.Value);
        }
    }

    public static Status from(int value)
    {
        return _map.get(value);
    }

    public static int to(Status status){
        return _map2.get(status);
    }
}
