package com.github.hansolaf.records;

import com.github.krukow.clj_lang.APersistentMap;
import com.github.krukow.clj_lang.IPersistentMap;
import com.github.krukow.clj_lang.PersistentHashMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class Rec<T> implements
        Map<Key<?>, Object>,
        Function<Key<?>, Object>,
        Iterable<Map.Entry<Key<?>, Object>> {

    private final APersistentMap<Key<?>, Object> map;

    public Rec() {
        this(PersistentHashMap.emptyMap());
    }

    public Rec(APersistentMap<Key<?>, Object> map) {
        this.map = map;
    }

    public Rec(Map<Key<?>, Object> map) {
        this(PersistentHashMap.create(map));
    }

    public Rec(Object... keysAndVals) {
        this(PersistentHashMap.create(keysAndVals));
    }

    public static <X> Rec<X> create(Object... keysAndVals) {
        return new Rec<>(keysAndVals);
    }

    /* Record stuff */

    /**
     * Returns a new Rec of the same type with 'key' associated to 'value'
     */
    public <A> Rec<T> with(Key<A> key, A value) {
        return new Rec<>((APersistentMap<Key<?>, Object>) map.assoc(key, value));
    }

    /**
     * Returns a new Rec without mappings for the specified 'keys'
     */
    public Rec<T> without(Key<?>... keys) {
        IPersistentMap<Key<?>, Object> res = map;
        for (Key<?> key : keys) {
            if (containsKey(key))
                res = res.without(key);
        }
        return new Rec<>((APersistentMap<Key<?>, Object>) res);
    }

    /**
     * Returns a new Rec with only the mappings specified by 'keys'
     */
    public Rec<T> selectKeys(Key<?>... keys) {
        IPersistentMap<Key<?>, Object> res = PersistentHashMap.emptyMap();
        for (Key<?> key : keys) {
            if (containsKey(key))
                res = res.assoc(key, get(key));
        }
        return new Rec<>((APersistentMap<Key<?>, Object>) res);
    }

    /**
     * Returns the value associated with the given 'key'
     */
    @SuppressWarnings("unchecked")
    public <A> A get(Key<A> key) {
        return (A) map.get(key);
    }

    /**
     * Returns a stream of the map entries
     */
    public Stream<Entry<Key<?>, Object>> stream() {
        return entrySet().stream();
    }

    @Override
    public Iterator<Entry<Key<?>, Object>> iterator() {
        return entrySet().iterator();
    }

    @Override
    public Object apply(Key<?> key) {
        return get(key);
    }

    /* Map stuff */

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(Key<?> key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Key<?>, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Key<?>> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Key<?>, Object>> entrySet() {
        return map.entrySet();
    }

    /* Object stuff */

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return map.equals(obj);
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
