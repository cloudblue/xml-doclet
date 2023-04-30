/*
 * Copyright 2023 CloudBlue.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.automation.xmldoclet;

import com.automation.xmldoclet.xjc.Class;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author sparry
 * @param <C>
 */
public class ClassList<C extends Class> extends ArrayList<C> {
    protected HashMap<String, Class> map;

    @Override
    public boolean contains(Object o) {
        return map.containsKey(((Class)o).getQualified());
    }

    @Override
    public boolean add(C c) {
        if (!map.containsKey(c.getQualified())) {
            map.put(c.getQualified(), c);
            return super.add(c);
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (map.remove(((Class)o).getQualified(), o)) {
            return super.remove(o);
        } else {
            return false;
        }
    }
    
    @Override
    public void replaceAll(UnaryOperator<C> operator) {
        map.clear();
        super.replaceAll(operator);
        this.stream().forEach(c -> map.put(c.getQualified(), c));
    }

    @Override
    public boolean removeIf(Predicate<? super C> filter) {
        this.stream().filter(filter).forEach(c -> map.remove(c.getQualified()));
        return super.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> coll) {
        if (super.retainAll(coll) ) {
            map.clear();
            this.stream().forEach(c -> map.put(c.getQualified(), c));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> coll) {
        if (super.removeAll(coll) ) {
            coll.stream().forEach(o -> map.remove(((Class)o).getQualified()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        this.subList(fromIndex, toIndex).stream().forEach(c -> map.remove(c.getQualified()));
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public boolean addAll(int index, Collection<? extends C> coll) {
        int i = 0;
        boolean retVal = false;
        for(C c: coll) {
            if (!map.containsKey(c.getQualified())) {
                map.put(c.getQualified(), c);
                retVal = retVal || true;
                add(i++, c);
            }
        }
        return retVal;
    }

    @Override
    public boolean addAll(Collection<? extends C> coll) {
        return coll.stream().mapToLong(o -> add((C)o) ? 1 : 0).sum() > 0;
    }

    @Override
    public void clear() {
        map.clear();
        super.clear();
    }

    @Override
    public C remove(int index) {
        if (index < super.size() && index >=0) {
            String key = super.get(index).getQualified();
            map.remove(key);
            return super.remove(index);
        } else {
            return null;
        }
    }

    @Override
    public void add(int index, C c) {
        if (!map.containsKey(c.getQualified())) {
            map.put(c.getQualified(), c);
            super.add(index, c);
        }
    }

    @Override
    public C set(int index, C c) {
        if (index < super.size() && index >=0 && c != null) {
            String key = super.get(index).getQualified();
            if (! c.getQualified().equals(key)) {
                if(map.containsKey(c.getQualified())) {
                    return null;
                } else {
                    map.remove(key);
                    map.put(c.getQualified(), c);
                    return super.set(index, c);
                }
            } else {
                return super.set(index, c);
            }
        } else {
            return null;
        }
    }

    public ClassList(int initialCapacity) {
        super(initialCapacity);
        map = new HashMap<>(initialCapacity);
    }

    public ClassList() {
        super();
        map = new HashMap<>();
    }

    public ClassList(Collection<? extends C> coll) {
        super(coll);
        coll.stream().forEach(c -> map.put(c.getQualified(), c));
    }
    
}
