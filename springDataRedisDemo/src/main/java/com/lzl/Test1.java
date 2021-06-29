package com.lzl;

/**
 * @Description
 * @Author LZL
 * @Date 2021.06.10-12:54
 */
public class Test1 {
    public static void main(String[] args) {
        WaitNotify waitNotify=new WaitNotify(1,5);
        new Thread(()->{
            try {
                waitNotify.print("a",1,2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                waitNotify.print("b",2,3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                waitNotify.print("c",3,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        float a= (float) (0.9-0.1);
        float b= (float) (1-0.2);
        System.out.println(a==b);
        System.out.println(3*0.1==0.3);
    }
}
class WaitNotify {
    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String str, int current, int next) throws InterruptedException {
        for(int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while(flag != current) {
                    this.wait();
                }
                System.out.println(str);
                flag = next;
                this.notifyAll();
            }
        }

    }

}

