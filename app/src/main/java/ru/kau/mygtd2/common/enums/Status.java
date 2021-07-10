package ru.kau.mygtd2.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    NEXTACTION(-2), // Следующее действие
    SOMETIME(-1),   // Когда-нибудь
    NOTHING(1),     // Статус не определен
    NEW(2),         // Новая
    INPROGRESS(3),  // В процессе (в работе)
    PAUSE(4),       // Отменена
    INHOLD(5),      // Отложена
    COMPLETED(6);   // Завершена

    public final int Value;

    Status(int value)
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
