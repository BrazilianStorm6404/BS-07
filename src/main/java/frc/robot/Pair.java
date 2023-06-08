package frc.robot;

public class Pair <A, B> {
    
    A a;
    B b;

    public Pair(A a, B b){
        
        this.a = a;
        this.b = b; 

    }

    public A firstValue(){
        return a;
    }

    
    public B secundValue(){
        return b;
    }
}
