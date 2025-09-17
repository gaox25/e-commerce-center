package com.gaoxi.springcloud;

import java.util.function.Function;

public class T1 {
    public static void main(String[] args) {
        //这是lambda表达式的第一种形式
//        Dog dog = hi("小花猫", (String str) -> {
//            Cat cat = new Cat();
//            cat.setName(str);
//            return cat;
//        });
        //这是lambda表达式的第二种形式，如果形参只有一个，那么类型和小括号就可以去掉
//        Dog dog = hi("小花猫", str -> {
//            Cat cat = new Cat();
//            cat.setName(str);
//            return cat;
//        });
        //进一步简写
//        Dog dog = hi("小花猫", str -> {
//            return new Cat(str);
//        });
        //再进一步简写
        Dog dog = hi("小白猫", str -> new Cat(str));
        System.out.println("dog = " + dog);
    }

    public static Dog hi(String str, Function<String, Cat> fn) {
        Cat cat = fn.apply(str);
        Dog dog = new Dog();
        dog.setName(cat.getName() + "~变成了小狗名" );
        return dog;
    }

}

class Dog {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}