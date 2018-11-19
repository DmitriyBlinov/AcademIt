package ru.academits.blinov.checklist;

import ru.academits.blinov.list.SinglyLinkedList;

public class CheckList {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> numbers = new SinglyLinkedList<>();

        numbers.add(1);
        numbers.add(3);
        numbers.add(5);
        numbers.add(8);
        System.out.println("Изначальный список: " + numbers.toString());
        System.out.println("Размер списка: " + numbers.getSize());
        System.out.println("Первый элемент: " + numbers.getHead());
        System.out.println("Элемент по индексу 2: " + numbers.getItem(2));

        System.out.println("Измененный элемент: " + numbers.setItem(99, 2) + ", " + numbers.toString());
        System.out.println("Удаленный элемент: " + numbers.removeItem(0));

        numbers.addAt(7, 1);
        System.out.println("Вставить по индексу 0: " + numbers.toString());

        System.out.println("Удалить по data: " + numbers.removeByData(8) + ", " + numbers.toString());
        System.out.println("Удалить head: " + numbers.removeHead() + ", " + numbers.toString());

        System.out.println("Копия списка: " + numbers.copy().toString());

        numbers.reverseList();
        System.out.println("Разворот списка: " + numbers.toString());
    }
}
