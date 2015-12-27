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
import com.sun.tools.classfile.Descriptor;
import com.sun.tools.classfile.Descriptor.InvalidDescriptor;

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
    
    public static String parseDescriptor(String desc) {
        int p = 0;
        StringBuilder sb = new StringBuilder();
        int dims = 0;
        int count = 0;
        int end = desc.length();

        while (p < end) {
            String type;
            char ch;
            switch (ch = desc.charAt(p++)) {
                case '(':
                    sb.append('(');
                    continue;

                case ')':
                    sb.append(')');
                    continue;

                case '[':
                    dims++;
                    continue;

                case 'B':
                    type = "byte";
                    break;

                case 'C':
                    type = "char";
                    break;

                case 'D':
                    type = "double";
                    break;

                case 'F':
                    type = "float";
                    break;

                case 'I':
                    type = "int";
                    break;

                case 'J':
                    type = "long";
                    break;

                case 'L':
                    int sep = desc.indexOf(';', p);
                    if (sep == -1)
                        throw new RuntimeException("Invalid descriptor");
                    type = desc.substring(p, sep).replace('/', '.');
                    p = sep + 1;
                    break;

                case 'S':
                    type = "short";
                    break;

                case 'Z':
                    type = "boolean";
                    break;

                case 'V':
                    type = "void";
                    break;

                default:
                    throw new RuntimeException("Invalid descriptor");
            }

            if (sb.length() > 1 && sb.charAt(0) == '(')
                sb.append(", ");
            sb.append(type);
            for ( ; dims > 0; dims-- )
                sb.append("[]");

            count++;
        }

        return sb.toString();
    }
    
    public static void printMethodDescriptor(Descriptor descriptor, ConstantPool constant_pool) throws InvalidDescriptor, ConstantPoolException {
        System.out.print("{\"returnType\":\"");
        System.out.print( descriptor.getReturnType(constant_pool) );
        System.out.print("\", \"parameterTypes\":[");
        
        String rawTypes = descriptor.getParameterTypes(constant_pool);
        String ptypes = rawTypes.substring(1, rawTypes.length()-1);
        String splitted[] = ptypes.split(",");
        if (splitted != null && splitted.length > 0) {
            for (int i = 0; i < splitted.length; i++) {
                if (splitted[i].trim().equals("")) continue;
                System.out.print("\"" + splitted[i].trim() + "\"");
                if (i < splitted.length-1) System.out.print(",");
            }
        }
        
        System.out.print("]}");
    }
}
