package by.start.shirostudy.utils;

import org.apache.catalina.util.SessionIdGeneratorBase;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author bystart
 * @date 2020/7/11 20:19
 * 仔细！坚持！
 * ❥(^_-))
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
@Lazy(false)
public class UniqueID implements SessionIdGenerator {


        private static SecureRandom random = new SecureRandom();

        /**
         * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
         */
        public static String getUUID() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }

        /**
         * 使用SecureRandom随机生成Long.
         */
        public static long randomLong() {
            return Math.abs(random.nextLong());
        }

        /**
         * 基于Base62编码的SecureRandom随机生成bytes.
         */
//        public static String randomBase62(int length) {
//            byte[] randomBytes = new byte[length];
//            random.nextBytes(randomBytes);
//            return Encodes.encodeBase62(randomBytes);
//        }

//        /**
//         * Activiti ID 生成
//         */
//        @Override
//        public String getNextId() {
//            return IdGen.uuid();
//        }

        @Override
        public Serializable generateId(Session session) {
            return UniqueID.getUUID();
        }

        public static void main(String[] args) {
            System.out.println(UniqueID.getUUID());
            System.out.println(UniqueID.getUUID().length());
//            System.out.println(new UniqueID().getNextId());
//            for (int i=0; i<1000; i++){
//                System.out.println(UniqueID.randomLong() + "  " + UniqueID.randomBase62(5));
//            }
        }

}
