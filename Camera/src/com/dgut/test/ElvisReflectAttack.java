package com.dgut.test;
import java.lang.reflect.Constructor;  
import java.lang.reflect.InvocationTargetException;  

public class ElvisReflectAttack  
{  
 
    public static void main(String[] args) 
    	throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException  
    {  
        Class<?> classType = Elvis.class;  
 
        Constructor<?> c = classType.getDeclaredConstructor(null);  
        c.setAccessible(true);  
        Elvis e1 = (Elvis)c.newInstance();  
        Elvis e2 = Elvis.getInstance();  
        System.out.println(e1==e2);  
    }  
 
}
