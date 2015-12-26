package com.sun.tools.javap;

import com.google.gson.Gson;
import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Class_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Double_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Fieldref_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Float_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Integer_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_InterfaceMethodref_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_InvokeDynamic_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Long_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_MethodHandle_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_MethodType_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Methodref_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_NameAndType_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_String_info;
import com.sun.tools.classfile.ConstantPool.CONSTANT_Utf8_info;
import com.sun.tools.classfile.ConstantPool.CPInfo;
import com.sun.tools.classfile.ConstantPoolException;

public class ConstantPoolPrinter {

    public static void printConstant(CPInfo cpInfo, ConstantPool constant_pool) throws ConstantPoolException {
        if (cpInfo instanceof CONSTANT_Fieldref_info) {
            CONSTANT_Fieldref_info info = (CONSTANT_Fieldref_info) cpInfo;
            printConstant(constant_pool.get(info.name_and_type_index), constant_pool);
            
        } else if (cpInfo instanceof CONSTANT_NameAndType_info) {
            CONSTANT_NameAndType_info info = (CONSTANT_NameAndType_info) cpInfo;
            System.out.print("{\"name\":");
            printConstant(constant_pool.get(info.name_index), constant_pool);
            
            System.out.print(",\"constantType\":");
            printConstant(constant_pool.get(info.type_index), constant_pool);
            System.out.print("}");
            
        } else if (cpInfo instanceof CONSTANT_String_info) {
            CONSTANT_String_info info = (CONSTANT_String_info) cpInfo;
            System.out.print("{\"stringValue\":" + new Gson().toJson(info.getString()) + "}");
            
        } else if (cpInfo instanceof CONSTANT_Utf8_info) {
            CONSTANT_Utf8_info info = (CONSTANT_Utf8_info) cpInfo;
            System.out.print("{\"utf8Value\":\"" + info.value + "\"}");
            
        } else if (cpInfo instanceof CONSTANT_Methodref_info) {
            CONSTANT_Methodref_info info = (CONSTANT_Methodref_info) cpInfo;
            printConstant(constant_pool.get(info.name_and_type_index), constant_pool);
            
        } else if (cpInfo instanceof CONSTANT_Integer_info) {
            CONSTANT_Integer_info info = (CONSTANT_Integer_info) cpInfo;
            System.out.print("{\"intValue\":");
            System.out.print(info.value);
            System.out.print("}");

        } else if (cpInfo instanceof CONSTANT_Float_info) {
            CONSTANT_Float_info info = (CONSTANT_Float_info) cpInfo;
            System.out.print("{\"floatValue\":");
            System.out.print(info.value);
            System.out.print("}");

        } else if (cpInfo instanceof CONSTANT_Long_info) {
            CONSTANT_Long_info info = (CONSTANT_Long_info) cpInfo;
            System.out.print("{\"longValue\":");
            System.out.print(info.value);
            System.out.print("}");

        } else if (cpInfo instanceof CONSTANT_Double_info) {
            CONSTANT_Double_info info = (CONSTANT_Double_info) cpInfo;
            System.out.print("{\"doubleValue\":");
            System.out.print(info.value);
            System.out.print("}");

        } else if (cpInfo instanceof CONSTANT_Class_info) {
            CONSTANT_Class_info info = (CONSTANT_Class_info) cpInfo;
            printConstant(constant_pool.get(info.name_index), constant_pool);

        } else if (cpInfo instanceof CONSTANT_InterfaceMethodref_info) {
            CONSTANT_InterfaceMethodref_info info = (CONSTANT_InterfaceMethodref_info) cpInfo;
            System.out.print("{\"methodName\":");
            printConstant(constant_pool.get(info.name_and_type_index), constant_pool);
            System.out.print(",\"className\":");
            printConstant(constant_pool.get(info.class_index), constant_pool);
            System.out.print("}");
            
        } else if (cpInfo instanceof CONSTANT_MethodHandle_info) {
            CONSTANT_MethodHandle_info info = (CONSTANT_MethodHandle_info) cpInfo;
            printConstant(constant_pool.get(info.reference_index), constant_pool);

        } else if (cpInfo instanceof CONSTANT_MethodType_info) {
            CONSTANT_MethodType_info info = (CONSTANT_MethodType_info) cpInfo;
            printConstant(constant_pool.get(info.descriptor_index), constant_pool);

        } else if (cpInfo instanceof CONSTANT_InvokeDynamic_info) {
            CONSTANT_InvokeDynamic_info info = (CONSTANT_InvokeDynamic_info) cpInfo;
            printConstant(constant_pool.get(info.name_and_type_index), constant_pool);
            // more..
        }
        
    }
    
    
}
