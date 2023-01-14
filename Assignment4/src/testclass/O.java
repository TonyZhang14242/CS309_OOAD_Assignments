package testclass;

import dependency_injection.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class O {

    @Value(value = "no")
    private boolean emptyBoolean;

    @Value(value = "RA2=HBK08")
    private int emptyInt;

    @Value(value = "")
    private String emptyString;

    @Value(value = "[QQQKKKJ10-KKKAAA34]", delimiter = "-")
    private boolean[] emptyBooleanArray;

    @Value(value = "[66677788899933-111222333444555]", delimiter = "-")
    private int[] emptyIntArray;

    @Value(value = "[]")
    private String[] emptyStringArray;

    @Value(value = "[66677788899933-111222333444555]", delimiter = "-")
    private List<Integer> emptyIntegerList;

    @Value(value = "{RA2=HBK08, Lantian28}")
    private Set<Boolean> emptyBooleanSet;

    @Value(value = "{3333336666669999:treu,11111122222233333:fales}")
    private Map<Integer, Boolean> emptyMap;

    public boolean isEmptyBoolean() {
        return emptyBoolean;
    }

    public int getEmptyInt() {
        return emptyInt;
    }

    public String getEmptyString() {
        return emptyString;
    }

    public boolean[] getEmptyBooleanArray() {
        return emptyBooleanArray;
    }

    public int[] getEmptyIntArray() {
        return emptyIntArray;
    }

    public String[] getEmptyStringArray() {
        return emptyStringArray;
    }

    public List<Integer> getEmptyIntegerList() {
        return emptyIntegerList;
    }

    public Set<Boolean> getEmptyBooleanSet() {
        return emptyBooleanSet;
    }

    public Map<Integer, Boolean> getEmptyMap() {
        return emptyMap;
    }
}
