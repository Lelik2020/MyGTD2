package ru.kau.mygtd2.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum PrStatus {

    ACTIVE(1),
    CANCELED(2),
    ARCHIVE(3),
    POSTPONED(4);

    public final int Value;

    PrStatus(int value)
    {
        Value = value;
    }

    private static final Map<Integer, PrStatus> _map = new HashMap<Integer, PrStatus>();
    private static final Map<PrStatus, Integer> _map2 = new HashMap<PrStatus, Integer>();
    static
    {
        for (PrStatus prStatus : PrStatus.values()) {
            _map.put(prStatus.Value, prStatus);
            _map2.put(prStatus, prStatus.Value);
        }
    }

    public static PrStatus from(int value)
    {
        return _map.get(value);
    }

    public static int to(PrStatus prStatus){
        return _map2.get(prStatus);
    }


}
