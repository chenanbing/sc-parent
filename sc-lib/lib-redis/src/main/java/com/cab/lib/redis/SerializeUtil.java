package com.cab.lib.redis;

import java.io.*;

/**
 */
public class SerializeUtil {

    public static byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bai!=null){
                try {
                    bai.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(obi!= null){
                try {
                    obi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //反序列化
    public static Object unserialize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(byt);
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bis!= null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oii!= null){
                try {
                    oii.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
    }
}
