package com.javagoo.sajdi.print;

import sun.jvm.hotspot.memory.SystemDictionary;
import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;

/**
 * from https://github.com/songuo/sa-jdi
 */
public class PrintHeapStrings extends Tool {

    @Override
    public void run() {
        ObjectHeap heap = VM.getVM().getObjectHeap();
        InstanceKlass strKlass = SystemDictionary.getStringKlass();
        heap.iterateObjectsOfKlass(new StringHeapVisitor(), strKlass);
        System.out.println();
    }

    private static class StringHeapVisitor implements HeapVisitor {

        @Override
        public void prologue(long l) {

        }

        @Override
        public boolean doObj(Oop oop) {
            System.out.println(OopUtilities.stringOopToString(oop));
            return false;
        }

        @Override
        public void epilogue() {

        }
    }

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 1) {
            System.err.println("Usage: java PrintHeapStrings <PID of the JVM whose heap strings you want to print>");
            System.exit(1);
        }
        PrintHeapStrings pst = new PrintHeapStrings();
        pst.execute(args);
    }
}

//  sudo /Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/bin/java -cp /Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/tools.jar:. com.javagoo.sajdi.print.PrintHeapStrings 5781

