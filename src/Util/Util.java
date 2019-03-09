package Util;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Util
{

    public static int generateID() {
        int max = (int) Math.pow(2, Constant.size);
        return getRandInt(0,max);
    }

    public static int getRandInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static int convertToCircleID(int id){
        System.out.println("ConvertToCircleId:"+id);
        if(id < 0){
            return (int) (id + Math.pow(2, Constant.size) );
        }
        else{
            return (int) (id%Math.pow(2, Constant.size));
        }
    }

    public static byte[] makeMessage(String message) {

        byte[] sendByte=new byte[Constant.messageSize];
        Arrays.fill(sendByte, 0, Constant.messageSize, (byte) 0);
        byte messageByte[] = message.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(sendByte);
        byteBuffer.position(0);
        byteBuffer.put(messageByte);
        sendByte = byteBuffer.array();
        return sendByte;

    }
}
