package ru.academits.blinov.hashtable;

import java.util.*;

public class HashTableHome<E> implements Collection<E> {
    private ArrayList<E>[] hashTable;
    private int modCount = 0;
    private int entries = 0;

    public HashTableHome() {
        //noinspection unchecked
        hashTable = new ArrayList[]{null};
    }

    public HashTableHome(int size) {
        //noinspection unchecked
        hashTable = new ArrayList[size];
    }

    @Override
    public int size() {
        return entries;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(Object value) {
        int index = Math.abs(value.hashCode() % hashTable.length);
        if (hashTable[index] == null) {
            hashTable[index] = new ArrayList<>();
        }
        if (hashTable[index].add((E) value)) {
            modCount++;
            entries++;
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean isAdded = false;
        for (E e : collection) {
            if (e == null) {
                continue;
            }
            if (add(e)) {
                entries++;
                modCount++;
                isAdded = true;
            }
        }
        return isAdded;
    }

    @Override
    public boolean contains(Object object) {
        if (isEmpty()) {
            return false;
        }
        if (object == null) {
            for (ArrayList<E> e : hashTable) {
                if (e == null) {
                    return true;
                }
            }
            return false;
        }
        int index = Math.abs(object.hashCode() % hashTable.length);
        if (hashTable[index] == null) {
            return false;
        }
        for (E e : hashTable[index]) {
            if (e.equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty() || isEmpty()) {
            return false;
        }
        for (Object e : collection) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        /*for (int i = 0; i < array.length; i++) {
            array[i] =
        }*/
        List<ArrayList<E>> tempHashTable = new ArrayList<>(Arrays.asList(hashTable));
        tempHashTable.removeAll(Collections.singleton(null));
        return tempHashTable.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size()) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(toArray(), size(), array.getClass());
        }
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(toArray(), 0, array, 0, array.length);
        if (array.length > size()) {
            array[size()] = null;
            return array;
        }
        return array;
    }

    @Override
    public boolean remove(Object object) {
        if (isEmpty()) {
            return false;
        }
        if (object == null) {
            List<ArrayList<E>> currentHashTable = new ArrayList<>(Arrays.asList(hashTable));
            currentHashTable.removeAll(Collections.singleton(null));
            HashTableHome<E> tempHashTableHome = new HashTableHome<>(currentHashTable.size());
            for (ArrayList<E> e : currentHashTable) {
                if (e != null) {
                    tempHashTableHome.addAll(e);
                }
            }
            hashTable = tempHashTableHome.hashTable;
            modCount++;
            entries--;
            return true;

        }
        int index = Math.abs(object.hashCode() % hashTable.length);
        if (hashTable[index] == null) {
            return false;
        }
        if (hashTable[index].remove(object)) {
            modCount++;
            entries--;
            if (hashTable[index].isEmpty()) {
                hashTable[index] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection.isEmpty() || isEmpty()) {
            return false;
        }
        boolean isDeleted = false;
        for (Object e : collection) {
            if (remove(e)) {
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }
        int count = 0;
        for (ArrayList<E> e : hashTable) {
            if (e == null) {
                count++;
                continue;
            }
            e.clear();
            if (e.isEmpty()) {
                hashTable[count] = null;
            }
            count++;
        }
        modCount++;
        entries = 0;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection.isEmpty() || isEmpty()) {
            return false;
        }
        boolean isRetained = false;
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) {
                continue;
            }
            if (hashTable[i].retainAll(collection)) {
                isRetained = true;
                modCount++;
                entries--;
            }
            if (hashTable[i].isEmpty()) {
                hashTable[i] = null;
            }
        }
        return isRetained;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (hashTable.length == 0) {
            return stringBuilder.append("[ ]").toString();
        }
        stringBuilder.append("[");
        for (ArrayList<E> e : hashTable) {
            stringBuilder.append(e).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]");

        return stringBuilder.toString();
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentIndex = 0;
        private int currentInnerIndex = -1;
        int currentModCount = modCount;

        @Override
        public boolean hasNext() {
            return (currentIndex + 1 < hashTable.length);
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (modCount != currentModCount) {
                throw new ConcurrentModificationException();
            }
            for (int i = currentIndex; i < size() - 1; i++) {
                if (hashTable[i] == null) {
                    currentIndex++;
                    continue;
                }
                if (!hasNext()) {
                    break;
                }
                if (currentInnerIndex + 1 < hashTable[currentIndex].size() - 1) {
                    currentInnerIndex++;
                    return hashTable[currentIndex].get(currentInnerIndex);
                }
                //currentInnerIndex = 0;
                //return hashTable[i].get(currentInnerIndex);
            }

            if (hashTable[currentIndex] == null) {
                currentIndex++;
                return null;
            }
            if (currentInnerIndex + 1 < hashTable[currentIndex].size()) {
                currentInnerIndex++;
                return hashTable[currentIndex].get(currentInnerIndex);
            }
            currentInnerIndex = -1;
            if (currentIndex + 1 < hashTable.length) {
                currentIndex++;
                if (hashTable[currentIndex] == null) {
                    currentIndex++;
                    return null;
                }
            }
            currentInnerIndex++;
            return hashTable[currentIndex].get(currentInnerIndex);
        }
    }

    public Iterator<E> iterator() {
        return new HashTableIterator();
    }
}