package com.github.hansolaf.records;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.github.hansolaf.records.RecordTest.Thing.name;
import static com.github.hansolaf.records.RecordTest.Thing.price;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RecordTest {

    interface Thing {
        Key<String> name = new Key<>("name");
        Key<Double> price = Key.create("price");
    }

    private Rec<Thing> a1 = new Rec<Thing>(name, "Some", price, 24.4d);
    private Rec<Thing> a2 = Rec.create(name, "Some", price, 24.4d);
    private Rec<Thing> b = new Rec<Thing>(name, "Other", price, 21.0d);

    @Test
    public void supportEquals() {
        assertEquals(a1, a2);
        assertNotEquals(a1, b);
    }

    @Test
    public void supportToString() {
        assertEquals(a1.toString(), a2.toString());
        assertNotEquals(a1.toString(), b.toString());
    }

    @Test
    public void supportHashCode() {
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), b.hashCode());
    }

    @Test
    public void areImmutable() {
        Rec<Thing> copy = a1.with(name, "Some other name");
        assertNotEquals(copy, a1);
    }

    @Test
    public void supportsMultipleBuilders() {
        Map<Key<?>, Object> map = new HashMap<>();
        map.put(name, "S");
        map.put(price, 1d);
        Rec<Thing> s1 = new Rec<>(map);
        Rec<Thing> s2 = new Rec<Thing>(name, "S", price, 1d);
        Rec<Thing> s3 = new Rec<Thing>().with(name, "S").with(price, 1d);

        assertEquals(1, new HashSet<>(Arrays.asList(s1, s2, s3)).size());
    }

    @Test
    public void canSelectSubset() {
        Rec<Thing> t1 = Rec.create(price, 14d);
        Rec<Thing> t2 = Rec.create(name, "a", price, 14d);
        assertEquals(t1, t2.selectKeys(price));
    }

}
