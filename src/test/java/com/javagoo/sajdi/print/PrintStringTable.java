package com.javagoo.sajdi.print;

import sun.jvm.hotspot.memory.StringTable;
import sun.jvm.hotspot.memory.SystemDictionary;
import sun.jvm.hotspot.oops.Instance;
import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.oops.OopField;
import sun.jvm.hotspot.oops.TypeArray;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;

/**
 * from https://github.com/songuo/sa-jdi
 */
public class PrintStringTable extends Tool {

    @Override
    public void run() {
        StringTable table = VM.getVM().getStringTable();
        table.stringsDo(new StringPrinter());
    }

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 1) {
            System.err.println("Usage: java PrintStringTable <PID of the JVM whose string table you want to print>");
            System.exit(1);
        }
        PrintStringTable pst = new PrintStringTable();
        pst.execute(args);
    }

    class StringPrinter implements StringTable.StringVisitor {
        private final OopField stringValueField;
        public StringPrinter() {
            InstanceKlass strKlass = SystemDictionary.getStringKlass();
            stringValueField = (OopField)strKlass.findField("value", "[C");
        }

        @Override
        public void visit(Instance instance) {
            TypeArray charArray = (TypeArray)stringValueField.getValue(instance);
            StringBuilder sb = new StringBuilder();
            for (long i=0; i<charArray.getLength(); i++) {
                sb.append(charArray.getCharAt(i));
            }

            System.out.println("Address: " + instance.getHandle() + " Content: " + sb.toString());
        }
    }
}

//  sudo /Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/bin/java -cp /Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/tools.jar:. com.javagoo.sajdi.print.PrintStringTable 5781
